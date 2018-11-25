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
 * @since 19.11.2018.
 */
@Singleton
class UserRemoteDataSource @Inject constructor(private val service: TodarchService) {
    /**
     * logs the [User] in
     */
    suspend fun login(email: String, password: String) = safeApiCall(
        call = { requestLogin(email, password) },
        errorMessage = "Register Failed"
    )

    private suspend fun requestLogin(email: String, password: String): Result<ResponseLogin> {
        val response = service.login(User(email, password)).await()
        if (response.errorCode.isNullOrEmpty()) {
            return Result.Success(response)
        }
        return Result.Error(
            IOException("Error getting comments ${response.errorCode} ${response.errorMessage}")
        )
    }

    /**
     * registers new [User]
     */
    suspend fun register(email: String, password: String) = safeApiCall(
        call = { requestRegister(email, password) },
        errorMessage = "Register Failed"
    )

    private suspend fun requestRegister(email: String, password: String): Result<ResponseRegister> {
        val response = service.register(User(email, password)).await()
        if (response.errorCode.isNullOrEmpty()) {
            return Result.Success(response)
        }
        return Result.Error(
            IOException("Error getting comments ${response.errorCode} ${response.errorMessage}")
        )
    }
}