package call.ofbeer.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class RegisterResponse(val status: String,
                            val message: String,
                            val errors: String)

data class LoginResponse(val token: String)

data class LogoutResponse(val message: String)

data class UserResponse (val id:Int,
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
