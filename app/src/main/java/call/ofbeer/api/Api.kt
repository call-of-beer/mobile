package call.ofbeer.api

import call.ofbeer.requests.LoginRequest
import call.ofbeer.requests.RegisterRequest
import retrofit2.http.*

interface Api {

    @POST("api/register")
    fun register(
        @Body registration: RegisterRequest
    ): retrofit2.Call<RegisterResponse>

    @POST("api/login")
    fun login(
        @Body login: LoginRequest
    ): retrofit2.Call<LoginResponse>

    @POST("api/auth/logout")
    fun logout(
        @Header("Authorization")  token: String
    ): retrofit2.Call<LogoutResponse>
}