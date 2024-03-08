package com.achmadss.data.common

import androidx.annotation.Keep
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeAPICall(call: suspend () -> Response<T>): APICallResult<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body() ?: throw IllegalStateException("Body is null")
            return APICallResult.Success(body)
        }
        val errorBody = response.errorBody()?.string() ?: throw IllegalStateException("Error body is null")
        val error = Gson().fromJson(errorBody, ErrorBody::class.java)
        return APICallResult.Error(IOException(error.message))
    } catch (e: Exception) {
        return APICallResult.Error(e)
    }
}

@Keep
data class ErrorBody(
    @SerializedName("message") val message: String
)