package br.com.fiap.email.network
import br.com.fiap.email.models.AuthResponse
import br.com.fiap.email.models.LoginRequest
import br.com.fiap.email.models.ReceivedEmail
import br.com.fiap.email.models.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface AuthService {
    @POST("api/Auth/register")
    fun register(@Body request: RegisterRequest): Call<AuthResponse>

    @POST("api/Auth/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>

    @GET("api/emails/{userId}/received-emails")
    fun getReceivedEmails(@Path("userId") userId: String): List<ReceivedEmail>
}