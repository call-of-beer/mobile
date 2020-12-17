package call.ofbeer.requests

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserRequest (
                        val firstame:String,
                        val surname: String,
                        val email: String


)