package io.android.todarch.core.util

import io.android.todarch.core.data.api.Result
import java.io.IOException

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 11.11.2018.
 *
 * Wrap a suspending API [call] in try/catch. In case an exception is thrown, a [Result.Error] is
 * created based on the [errorMessage].
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        call()
    } catch (e: Exception) {
        // An exception was thrown when calling the API so we're converting this to an IOException
        Result.Error(IOException(errorMessage, e))
    }
}