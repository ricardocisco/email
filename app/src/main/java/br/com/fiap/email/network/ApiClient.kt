package br.com.fiap.email.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithToken = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(requestWithToken)
    }
}

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:5132/"
    private var token: String? = null

    fun setToken(newToken: String) {
        token = newToken
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val requestBuilder = request.newBuilder()

            token?.let {
                requestBuilder.header("Authorization", "Bearer $it")
            }

            requestBuilder.header("Content-Type", "application/json")
            chain.proceed(requestBuilder.build())
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authService: AuthService = retrofit.create(AuthService::class.java)
}
