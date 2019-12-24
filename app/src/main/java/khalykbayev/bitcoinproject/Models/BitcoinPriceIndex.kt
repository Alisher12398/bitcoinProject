package khalykbayev.bitcoinproject.Models

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

class BitcoinPriceIndex(status: String, values: ArrayList<BitcoidPriceIndexValue>) {
    @SerializedName("status")
    var status: String? = status

    @SerializedName("values")
    var values: ArrayList<BitcoidPriceIndexValue>? = values
}

class BitcoidPriceIndexValue(x: Long, y: Double) {
    @SerializedName("x")
    var x: Long? = x

    @SerializedName("y")
    var y: Double? = y
}
