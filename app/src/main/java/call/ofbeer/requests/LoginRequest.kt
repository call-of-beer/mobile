package call.ofbeer.requests

data class LoginRequest(
    val email: String,
    val password: String
)