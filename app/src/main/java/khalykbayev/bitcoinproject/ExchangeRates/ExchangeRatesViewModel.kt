package khalykbayev.bitcoinproject.ExchangeRates

import android.util.Log
import androidx.lifecycle.ViewModel
import khalykbayev.bitcoinproject.Api.App
import khalykbayev.bitcoinproject.Models.Transaction
import khalykbayev.bitcoinproject.TransactionList.TransactionListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExchangeRatesViewModel : ViewModel() {
    fun loadTransactions(count: Int) {


//        App.bitstampApi.getTransactionList("hour").enqueue(object :
//            Callback<ArrayList<Transaction>> {
//
//            override fun onResponse(call: Call<ArrayList<Transaction>>, response: Response<ArrayList<Transaction>>) {
//                Log.d(TransactionListViewModel.TAG, "getTransactionList onResponse")
//                if (response.code() == 200) {
//                    Log.d(TransactionListViewModel.TAG, "getTransactionList onResponse 200, count: ${response.body()!!.count()}")
//                    transactionList.setList(filterTransactionList(response.body()!!, count))
//                }
//            }
//            override fun onFailure(call: Call<ArrayList<Transaction>>, t: Throwable) {
//                Log.d(TransactionListViewModel.TAG, "getTransactionList onFailure:${t.localizedMessage}")
//            }
//        })
    }
}
