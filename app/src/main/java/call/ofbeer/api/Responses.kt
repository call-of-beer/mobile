package call.ofbeer.api

import call.ofbeer.models.*
import com.google.gson.annotations.SerializedName
import java.util.*

data class RegisterResponse(val status: String,
                            val message: String,
                            val errors: String)

data class LoginResponse(val token: String)

data class LogoutResponse(val message: String)

data class UserResponse1 (val id:Int,
                         val firstname:String,
                         val surname: String,
                         val email: String,
                         @SerializedName("email_verified_at")
                         val emailVerifiedAt: Date,
                         val password: String,
                         @SerializedName("remember_token")
                         val rememberToken: String,
                         @SerializedName("created_at")
                         val createdAt: String,
                         @SerializedName("updated_at")
                         val updatedAt: String,
                         val error: String
)

data class UserResponsee (val id:Int,
                         val firstname:String,
                         val surname: String,
                         val email: String,
                         @SerializedName("email_verified_at")
                         val emailVerifiedAt: Date,
                         val password: String,
                         @SerializedName("remember_token")
                         val rememberToken: String,
                         @SerializedName("created_at")
                         val createdAt: String,
                         @SerializedName("updated_at")
                         val updatedAt: String,
                         val error: String
)

data class UserResponse(
    val result : User
)

data class BeerResponse(
    val result: List<Beer>
)

data class GroupResponse(
    val result: List<Group>
)

data class GroupAddResponse(
    val result: Group
)

data class GroupInfoResponse(
    val result: List<User>
)

data class TastingGetResponse(
    val result: List<Tasting>
)

data class CountryGetResponse(
    val result: List<Country>
)

data class TypeGetResponse(
    val result: List<Type>
)

data class TastingResponse(
    val result: Tasting
)

data class SuccesfulResponse(val message: String)
