package call.ofbeer.models

import com.google.gson.annotations.SerializedName

data class Rate(
    val aroma: Int,
    val color: Int,
    val taste: Int,
    val bitterness: Int,
    val texture: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("beer_id")
    val beerId: Int

)