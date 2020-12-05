package call.ofbeer.api

data class RegisterResponse(val status: String,
                            val message: String,
                            val errors: String)

data class LoginResponse(val token: String)

data class LogoutResponse(val message: String)