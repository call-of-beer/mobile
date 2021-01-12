package call.ofbeer.requests

import com.google.gson.annotations.SerializedName

data class NewRateRequest(
    val aroma: Int,
    val color: Int,
    val taste: Int,
    val bitterness: Int,
    val texture: Int,
    @SerializedName("user_id")
    val userId: Int

)