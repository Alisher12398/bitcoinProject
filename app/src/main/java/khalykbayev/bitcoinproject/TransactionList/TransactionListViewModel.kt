package khalykbayev.bitcoinproject.TransactionList

import android.util.Log
import androidx.lifecycle.ViewModel
import khalykbayev.bitcoinproject.Api.App
import khalykbayev.bitcoinproject.Models.Transaction
import khalykbayev.bitcoinproject.ObservableTransactionArrayList
import retrofit2.*

class TransactionListViewModel : ViewModel() {

    companion object {
        private const val TAG = "TransactionListViewModel"
    }
    //var transactionList: ArrayList<Transaction> = ArrayList()
    var transactionList = ObservableTransactionArrayList()
    //var transactionListObserver: Observa

    fun loadTransactions(count: Int) {
        App.api.getTransactionList("hour").enqueue(object :
            Callback<ArrayList<Transaction>> {

            override fun onResponse(call: Call<ArrayList<Transaction>>, response: Response<ArrayList<Transaction>>) {
                Log.d(TAG, "onResponse")
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse 200, count: ${response.body()!!.count()}")
                    transactionList.setList(filterTransactionList(response.body()!!, count))
                }
            }
            override fun onFailure(call: Call<ArrayList<Transaction>>, t: Throwable) {
                Log.d(TAG, "onFailure:${t.localizedMessage}")
            }
        })
    }

    private fun filterTransactionList(list: ArrayList<Transaction>, count: Int): ArrayList<Transaction> {
        val result: ArrayList<Transaction> = ArrayList()
        for (i in 0 until list.count()) {
            if (count > i) {
                result.add(list[i])
            }
        }
        return result
    }

}
