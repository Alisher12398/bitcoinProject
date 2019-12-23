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

    enum class Currency {
        USD, BTC
    }

    var rate: Double = 0.0
    var currentFromCurrency: Currency = Currency.BTC

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

    fun getRateForCurrentCurrency(value: String): String {
        var result: Double = 0.0
        val double: Double? = value.toDoubleOrNull()
        if (!value.contains(" ") && double != null) {
            when (currentFromCurrency) {
                Currency.BTC -> {
                    result = (double * rate)
                }
                Currency.USD -> {
                    result = (double / rate)
                }
            }
        }
        return result.toString()
    }

}
