package call.ofbeer.models

import com.google.gson.annotations.SerializedName

data class Comment (
    val id: Int,
    val content: String,
    @SerializedName("tasting_id")
    val tastingId: Int
)