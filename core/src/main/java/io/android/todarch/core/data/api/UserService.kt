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

import io.android.todarch.core.data.model.User
import io.android.todarch.core.data.model.response.ResponseLogin
import io.android.todarch.core.data.model.response.ResponseRegister
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 5.01.2019.
 */
interface UserService {
    @POST("um/non-secured/register")
    @Headers("No-Authentication: true")
    fun register(@Body user: User): Deferred<ResponseRegister>

    @POST("um/non-secured/authenticate")
    @Headers("No-Authentication: true")
    fun login(@Body user: User): Deferred<ResponseLogin>
}