package call.ofbeer.models

import com.google.gson.annotations.SerializedName

data class Beer(
    val id: Int,
    val name: String,
    @SerializedName("alcohol_volume")
    val alcoholVolume: String,
    val country: String,
    val description: String,
    @SerializedName("type_beer_id")
    val typeId: Int,
    @SerializedName("user_id")
    val userId: Int,
    val avgAroma: Float,
    val avgTaste: Float,
    val avgColor: Float,
    val avgBitterness: Float,
    val avgTexture: Float,
    val overall: Float,
    @SerializedName("tasting_id")
    val tastingId: Int

)