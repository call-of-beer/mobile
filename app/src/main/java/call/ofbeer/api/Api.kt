package call.ofbeer.api

import call.ofbeer.models.Beer
import call.ofbeer.models.User
import call.ofbeer.requests.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("api/auth/register")
    fun register(
        @Body registration: RegisterRequest
    ):retrofit2.Call<RegisterResponse>

    @POST("api/auth/login")
    fun login(
        @Body login: LoginRequest
    ): retrofit2.Call<LoginResponse>

    @POST("api/auth/logout")
    fun logout(
        @Header("Authorization")  token: String
    ): retrofit2.Call<LogoutResponse>

    @GET("api/beers")
    fun getAllBeers(
    ): retrofit2.Call<BeerResponse>

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

    @GET("api/user/me")
    fun getAccount(
        @Header("Authorization")  token: String
    ) : retrofit2.Call<UserResponse>

    @DELETE("api/user/delete/{id}")
    fun deleteUser(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<User>


    @GET("api/user/group/all/member")
    fun getGroups(
        @Header("Authorization")  token: String
    ) : retrofit2.Call<GroupResponse>

    @POST("/api/group/add")
    fun newGroup(
        @Body add: GroupRequest,
        @Header("Authorization")  token: String
    ): retrofit2.Call<GroupAddResponse>

    @GET("api/group/users/{id}")
    fun getGroupInfo(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ) : retrofit2.Call<GroupInfoResponse>

    @DELETE("api/group/{idGroup}")
    fun deleteGroup(
        @Header("Authorization")  token: String,
        @Path("idGroup") idGroup: Int
    ): retrofit2.Call<SuccesfulResponse>

    @DELETE("api/group/{idGroup}/{idUser}/delete")
    fun leaveGroup(
        @Header("Authorization")  token: String,
        @Path("idGroup") idGroup: Int,
        @Path("idUser") idUser: Int
    ): retrofit2.Call<SuccesfulResponse>

    @POST("/api/group/{id}/addUser")
    fun addUserToGroup(
        @Body add: AddUserToGroupRequest,
        @Path("id") id: Int,
        @Header("Authorization")  token: String
    ): retrofit2.Call<SuccesfulResponse>

    @GET("api/group/all")
    fun getGroupWhereModerator(
        @Header("Authorization")  token: String
    ) : retrofit2.Call<GroupResponse>

    @GET("api/tastings/{id}")
    fun getTastingOfGroup(
        @Path("id") id: Int,
        @Header("Authorization")  token: String
    ) : retrofit2.Call<TastingGetResponse>

    @GET("api/tastings/{id}")
    fun getTastingOfUser(
        @Path("id") id: Int,
        @Header("Authorization")  token: String
    ) : retrofit2.Call<TastingGetResponse>


    @GET("api/country")
    fun getCountriesList(
        @Header("Authorization")  token: String
    ) : retrofit2.Call<CountryGetResponse>

    @GET("api/type")
    fun getTypesList(
        @Header("Authorization")  token: String
    ) : retrofit2.Call<TypeGetResponse>

    @POST("/api/beer/store/{typeBeer}/{country}")
    fun addNewBeer(
        @Body add: AddBeerRequest,
        @Path("typeBeer") typeBeer: Int,
        @Path("country") country: Int,
        @Header("Authorization")  token: String
    ): retrofit2.Call<SuccesfulResponse>

    @POST("/api/tasting/{group}/{beer}")
    fun createTasting(
        @Body add: CreateTastingRequest,
        @Path("group") group: Int,
        @Path("beer") beer: Int,
        @Header("Authorization")  token: String
    ): retrofit2.Call<TastingResponse>

    @POST("/api/rating/store/{beer}/{raitings}")
    fun addRate(
        @Body add: NewRateRequest,
        @Path("beer") beer: Int,
        @Path("raitings") raitings: Int,
        @Header("Authorization")  token: String
    ): retrofit2.Call<SuccesfulResponse>

    @POST("/api/comment/new/{id}")
    fun addComment(
        @Body add: AddCommentRequest,
        @Path("id") id: Int,
        @Header("Authorization")  token: String
    ): retrofit2.Call<SuccesfulResponse>
}