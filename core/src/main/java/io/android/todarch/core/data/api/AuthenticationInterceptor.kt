/*
 * Copyright 2018 Todarch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.android.todarch.core.data.api

import io.android.todarch.core.data.Session
import io.android.todarch.core.data.model.User
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 14.12.2018.
 */
class AuthenticationInterceptor @Inject constructor(
    private val apiService: UserService,
    private val session: Session
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val mainRequest = chain.request()

        if (session.isLoggedIn && mainRequest.header("No-Authentication") == null) {
            if (session.token.isNullOrEmpty()) {
                val newToken = fetchToken()
                val request = getNewRequestWithToken(mainRequest, newToken)
                return chain.proceed(request)
            } else {
                val token: String = session.token!!
                val request = getNewRequestWithToken(mainRequest, token)
                val mainResponse = chain.proceed(request)

                if (mainResponse.code() == 401 || mainResponse.code() == 403) {
                    // token expired, retry the 'mainRequest' which encountered an authentication error
                    // add new token into 'mainRequest' header and request again
                    val newToken = fetchToken()
                    val secondRequest = getNewRequestWithToken(mainRequest, newToken)
                    return chain.proceed(secondRequest)
                }
                return mainResponse
            }
        }
        // no authentication, proceed adding nothing to interceptor
        return chain.proceed(mainRequest)
    }

    private fun getNewRequestWithToken(mainRequest: Request, token: String): Request {
        val builder = mainRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .method(mainRequest.method(), mainRequest.body())
        return builder.build()
    }

    /**
     * request to login API to get fresh token
     * synchronously calling login API
     */
    private fun fetchToken(): String {
        return runBlocking {
            val loginResponse = apiService.login(User(session.email!!, null, session.password!!)).await()
            val token = loginResponse.token

            if (loginResponse.errorCode.isNullOrEmpty() && !token.isNullOrEmpty()) {
                // save the new token
                session.token = token
            }
            return@runBlocking token ?: ""
        }
    }
}