package call.ofbeer.models

import com.google.gson.annotations.SerializedName

data class Beer (
    val id:Int,
    val name: String,
    @SerializedName("alcohol_volume")
    val alcoholVolume:String,
    val country:String,
    val description:String,
    @SerializedName("type_id")
    val typeId: Int
)