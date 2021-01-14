package call.ofbeer.models

import com.google.gson.annotations.SerializedName

data class Group(
    val id: Int,
    val name: String,
    @SerializedName("moderator_id")
    val moderatorId: Int
)