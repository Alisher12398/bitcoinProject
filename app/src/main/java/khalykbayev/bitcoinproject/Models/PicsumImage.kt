package khalykbayev.bitcoinproject.Models

import com.google.gson.annotations.SerializedName

class PicsumImage(url: String) {
    @SerializedName("download_url")
    var url: String? = url
}