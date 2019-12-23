package khalykbayev.bitcoinproject.Models

import com.google.gson.annotations.SerializedName

class Rate(last: String) {
    @SerializedName("last")
    var last: String? = last
}