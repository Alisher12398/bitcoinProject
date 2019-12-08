package khalykbayev.bitcoinproject.Converter

import android.util.Log
import androidx.lifecycle.ViewModel
import khalykbayev.bitcoinproject.Api.App
import khalykbayev.bitcoinproject.Models.Rate
import khalykbayev.bitcoinproject.Models.Transaction
import khalykbayev.bitcoinproject.TransactionList.TransactionListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConverterViewModel : ViewModel() {

    companion object {
        private const val TAG = "ConverterViewModel"
    }

    var rate: Double = 0.0

    fun loadRates() {
        App.bitstampApi.getRates().enqueue(object :
            Callback<Rate> {

            override fun onResponse(call: Call<Rate>, response: Response<Rate>) {
                Log.d(TAG, "getTransactionList onResponse")
                if (response.code() == 200) {
                    Log.d(TAG, "getTransactionList onResponse 200: ${response.body()!!.last}")
                    rate = response.body()!!.last!!.toDouble()
                }
            }
            override fun onFailure(call: Call<Rate>, t: Throwable) {
                Log.d(TAG, "getTransactionList onFailure:${t.localizedMessage}")
            }
        })
    }

    fun getBTCToUSDRate(btc: CharSequence?): Int {
        var result: Int = 0
        if (btc != null) {
            var string: String = btc.toString()
            var double = string.toDoubleOrNull()
            if (double != null) {
                result = (double * rate).toInt()
            }
        }
        return result
    }

    fun getUSDToBTCRate(usd: CharSequence?): Int {
        var result: Int = 0
        if (usd != null) {
            var string: String = usd.toString()
            var double = string.toDoubleOrNull()
            if (double != null) {
                result = (double / rate).toInt()
            }
        }
        return result
    }

}
