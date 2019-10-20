package khalykbayev.bitcoinproject.Models

import com.google.gson.annotations.SerializedName

class Transaction(date: String, tid: Int, price: String, type: Int, amount: String) {
    @SerializedName("date")
    var date: String? = date

    @SerializedName("tid")
    var tid: Int? = tid

    @SerializedName("price")
    var price: String? = price

    @SerializedName("type")
    var type: Int? = type

    @SerializedName("amount")
    var amount: String? = amount

}
