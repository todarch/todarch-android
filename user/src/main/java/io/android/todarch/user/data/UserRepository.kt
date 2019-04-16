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

import io.android.todarch.core.data.CoroutinesContextProvider
import io.android.todarch.core.data.api.Result
import io.android.todarch.core.data.model.User
import io.android.todarch.core.data.model.response.ResponseLogin
import io.android.todarch.core.data.model.response.ResponseRegister
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 10.11.2018.
 */
@Singleton
class UserRepository @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
    contextProvider: CoroutinesContextProvider
) {
    var user: User? = null
        private set

    init {
        GlobalScope.launch(contextProvider.io) {
            user = localDataSource.getUser()
        }
    }

    suspend fun login(username: String, password: String): Result<ResponseLogin> {
        val result = remoteDataSource.login(username, password)

        if (result is Result.Success) {
            result.data.token?.run {
                setLoggedInUser(User(username, this, password))
            }
        }
        return result
    }

    suspend fun register(username: String, password: String): Result<ResponseRegister> =
        remoteDataSource.register(username, password)

    suspend fun logout() {
        localDataSource.logout()
    }

    private suspend fun setLoggedInUser(loggedInUser: User) {
        localDataSource.setUser(loggedInUser)
    }
}