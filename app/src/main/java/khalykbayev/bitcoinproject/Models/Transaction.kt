package khalykbayev.bitcoinproject.Models

import com.google.gson.annotations.SerializedName

class Transaction {
    @SerializedName("date")
    var date: String? = null

    @SerializedName("tid")
    var tid: Int? = null

    @SerializedName("price")
    var price: String? = null

    @SerializedName("type")
    var type: Int? = null

    @SerializedName("amount")
    var amount: String? = null

    constructor(date: String, tid: Int, price: String, type: Int, amount: String) {
        this.date = date
        this.tid = tid
        this.price = price
        this.type = type
        this.amount = amount
    }

}
