package call.ofbeer.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class User (val id:Int,
                 val firstname:String,
                 val surname: String,
                 val email: String,
                 val password: String,
                 @SerializedName("remember_token")
                 val rememberToken: String,
                 @SerializedName("created_at")
                 val createdAt: Date,
                 @SerializedName("updated_at")
                 val updatedAt: Date


)