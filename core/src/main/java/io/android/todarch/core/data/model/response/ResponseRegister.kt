package io.android.todarch.core.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 11.11.2018.
 */
data class ResponseRegister(
    @SerializedName("email") val email: String?,
    @SerializedName("errorCode") override val errorCode: String?,
    @SerializedName("errorMessage") override val errorMessage: String?
) : BaseResponse