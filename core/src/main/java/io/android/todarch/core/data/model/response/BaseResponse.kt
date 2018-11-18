package io.android.todarch.core.data.model.response

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 11.11.2018.
 */
interface BaseResponse {
    val errorCode: String?
    val errorMessage: String?
}