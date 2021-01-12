package call.ofbeer.requests

import com.google.gson.annotations.SerializedName

data class AddBeerRequest(
    val name: String,
    @SerializedName("alcohol_volume")
    val alcVolume: String,
    val description: String
)