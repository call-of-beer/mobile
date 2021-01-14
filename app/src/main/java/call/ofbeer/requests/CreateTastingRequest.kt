package call.ofbeer.requests

import com.google.gson.annotations.SerializedName

data class CreateTastingRequest(
    val title: String,
    val description: String,
    @SerializedName("user_id")
    val user: Int,
    @SerializedName("group_id")
    val groupId: Int,
    @SerializedName("beer_id")
    val beerId: Int
)