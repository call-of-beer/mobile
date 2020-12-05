package call.ofbeer.requests

import com.google.gson.annotations.SerializedName

data class RegisterRequest (


    val email: String,
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String,
    val firstname: String,
    val surname: String
)