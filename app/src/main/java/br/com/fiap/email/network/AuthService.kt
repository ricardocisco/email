package br.com.fiap.email.network
import br.com.fiap.email.models.AuthResponse
import br.com.fiap.email.models.Email
import br.com.fiap.email.models.Emails
import br.com.fiap.email.models.LoginRequest
import br.com.fiap.email.models.MoveEmailsRequest
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

    @POST("api/Auth/users/{userId}/sendEmail")
    fun sentEmail(@Path("userId") userId: String, @Body email: Email): Call<Email>

    @GET("api/Auth/users/{userId}/emails")
    fun getUserEmails(@Path("userId") userId: String): Call<Emails>

    @POST("api/User/{userId}/moveToArchived")
    fun getMoveToArchived(@Path("userId") userId: String, @Body request: MoveEmailsRequest): Call<Void>
}