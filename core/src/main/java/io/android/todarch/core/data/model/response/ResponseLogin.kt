package io.android.todarch.core.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 13.11.2018.
 */
data class ResponseLogin(
    @SerializedName("token") val token: String?,
    @SerializedName("errorCode") override val errorCode: String?,
    @SerializedName("errorMessage") override val errorMessage: String?
) : BaseResponse