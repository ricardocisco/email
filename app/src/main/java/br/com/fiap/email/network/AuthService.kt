package br.com.fiap.email.network
import br.com.fiap.email.models.AuthResponse
import br.com.fiap.email.models.LoginRequest
import br.com.fiap.email.models.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {
    @POST("api/Auth/register")
    fun register(@Body request: RegisterRequest): Call<AuthResponse>

    @POST("api/Auth/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>
}