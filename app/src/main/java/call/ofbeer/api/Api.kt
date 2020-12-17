package call.ofbeer.api

import call.ofbeer.models.Beer
import call.ofbeer.models.User
import call.ofbeer.requests.LoginRequest
import call.ofbeer.requests.RegisterRequest
import call.ofbeer.requests.SearchRequest
import call.ofbeer.requests.UserRequest
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("api/register")
    fun register(
        @Body registration: RegisterRequest
    ):retrofit2.Call<RegisterResponse>

    @POST("api/login")
    fun login(
        @Body login: LoginRequest
    ): retrofit2.Call<LoginResponse>

    @POST("api/auth/logout")
    fun logout(
        @Header("Authorization")  token: String
    ): retrofit2.Call<LogoutResponse>

    @GET("api/beer/all")
    fun getAllBeers(
    ): retrofit2.Call<List<Beer>>

    @POST("/api/search")
    fun search(
        @Body add: SearchRequest
    ): retrofit2.Call<List<Beer>>

    @PATCH("api/user/{id}/edit")
    fun updateUser(
        @Header("Authorization")  token: String,
        @Path("id") id: Int,
        @Body add: UserRequest
    ): retrofit2.Call<UserResponse>

    @GET("api/auth/me")
    fun getAccount(
        @Header("Authorization")  token: String
    ) : retrofit2.Call<UserResponse>

    @DELETE("api/user/delete/{id}")
    fun deleteUser(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<User>
}