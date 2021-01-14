package call.ofbeer.models

import com.google.gson.annotations.SerializedName

data class Tasting(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("group_id")
    val groupId: String,
    @SerializedName("beer_id")
    val beerId: String,
    val beer: Beer
)