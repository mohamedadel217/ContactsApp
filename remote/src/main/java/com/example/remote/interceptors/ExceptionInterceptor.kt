package com.example.remote.interceptors

import com.example.remote.exception.NetworkException
import okhttp3.Interceptor
import okhttp3.Response

class ExceptionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val code = response.code

        if (code in 500..599 || code in 400 .. 404) {
            val error = getNetworkException(response)
            response.close()
            throw error
        }
        return response
    }

    private fun getNetworkException(response: Response?): NetworkException {
        return try {
            if (response == null) return NetworkException()
            val error = "error happened while getting the data"
            NetworkException(error)

        } catch (e: Throwable) {
            NetworkException()
        }
    }

}