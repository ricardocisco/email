package br.com.fiap.email.network
import br.com.fiap.email.models.AuthResponse
import br.com.fiap.email.models.DeleteEmailsRequest
import br.com.fiap.email.models.Email
import br.com.fiap.email.models.Emails
import br.com.fiap.email.models.LoginRequest
import br.com.fiap.email.models.MoveArchivedRequest
import br.com.fiap.email.models.MoveEmailsRequest
import br.com.fiap.email.models.PasswordReset
import br.com.fiap.email.models.ReceivedEmail
import br.com.fiap.email.models.RegisterRequest
import br.com.fiap.email.models.UpdateEmailSortOrderRequest
import br.com.fiap.email.models.UpdateFontRequest
import br.com.fiap.email.models.UpdateLanguageRequest
import br.com.fiap.email.models.UpdateThemeRequest
import br.com.fiap.email.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


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

    @POST("api/User/{userId}/moveToTrash")
    fun getMoveToTrash(@Path("userId") userId: String, @Body request: MoveEmailsRequest): Call<Void>

    @POST("api/User/{userId}/moveFromArchived")
    fun moveFromArchived(@Path("userId") userId: String, @Body request: MoveArchivedRequest): Call<Void>

    @POST("api/User/{userId}/moveFromTrash")
    fun moveFromTrash(@Path("userId") userId: String, @Body request: MoveArchivedRequest): Call<Void>

    @POST("api/User/deleteEmails")
    fun deleteEmails(@Body deleteEmailsRequest: DeleteEmailsRequest): Call<Unit>

    @POST("api/Auth/reset")
    fun resetPassword(@Body reset: PasswordReset): Call<Void>

    @PUT("api/User/users/{id}/theme")
    suspend fun updateTheme(@Path("id") userId: String, @Body request: UpdateThemeRequest): Response<User>

    @PUT("api/User/users/{id}/fontsize")
    suspend fun updateFontSize(@Path("id") userId: String, @Body request: UpdateFontRequest): Response<User>

    @PUT("api/User/users/{id}/language")
    suspend fun updateLanguage(@Path("id") userId: String, @Body request: UpdateLanguageRequest): Response<User>

    @PUT("api/User/users/{id}/emailsortorder")
    suspend fun updateEmailOrder(@Path("id") userId: String, @Body request: UpdateEmailSortOrderRequest): Response<User>

}