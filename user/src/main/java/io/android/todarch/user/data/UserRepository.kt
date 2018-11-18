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
package io.android.todarch.user.data

import io.android.todarch.core.data.api.Result
import io.android.todarch.core.data.api.TodarchService
import io.android.todarch.core.data.model.User
import io.android.todarch.core.data.model.response.ResponseLogin
import io.android.todarch.core.data.model.response.ResponseRegister
import io.android.todarch.core.util.safeApiCall
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 10.11.2018.
 */
@Singleton
class UserRepository @Inject constructor(private val service: TodarchService) {
    /**
     * registers new [User]
     */
    suspend fun register(email: String, password: String) = safeApiCall(
        call = { requestRegister(email, password) },
        errorMessage = "Register Failed"
    )

    private suspend fun requestRegister(
        email: String,
        password: String
    ): Result<ResponseRegister> {
        val response = service.register(User(email, password)).await()
        if (response.errorCode.isNullOrEmpty()) {
            return Result.Success(response)
        }
        return Result.Error(
            IOException("Error getting comments ${response.errorCode} ${response.errorMessage}")
        )
    }

    /**
     * logs the [User] in
     */
    suspend fun login(email: String, password: String) = safeApiCall(
        call = { requestLogin(email, password) },
        errorMessage = "Register Failed"
    )

    private suspend fun requestLogin(
        email: String,
        password: String
    ): Result<ResponseLogin> {
        val response = service.login(User(email, password)).await()
        if (response.errorCode.isNullOrEmpty()) {
            // TODO save user to local
            return Result.Success(response)
        }
        return Result.Error(
            IOException("Error getting comments ${response.errorCode} ${response.errorMessage}")
        )
    }
}