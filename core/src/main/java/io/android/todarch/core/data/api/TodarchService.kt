package io.android.todarch.core.data.api

import io.android.todarch.core.data.model.User
import io.android.todarch.core.data.model.response.ResponseLogin
import io.android.todarch.core.data.model.response.ResponseRegister
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 11.11.2018.
 */
interface TodarchService {
    @POST("um/non-secured/register")
    fun register(@Body user: User): Deferred<ResponseRegister>

    @POST("um/non-secured/authenticate")
    fun login(@Body user: User): Deferred<ResponseLogin>
}