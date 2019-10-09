package khalykbayev.bitcoinproject.Models

import com.google.gson.annotations.SerializedName

class Transaction {
    @SerializedName("date")
    val date: String? = null

    @SerializedName("tid")
    val tid: Int? = null

    @SerializedName("price")
    val price: String? = null

    @SerializedName("type")
    val type: Int? = null

    @SerializedName("amount")
    val amount: String? = null
}
