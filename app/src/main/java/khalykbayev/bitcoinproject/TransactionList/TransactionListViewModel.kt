package khalykbayev.bitcoinproject.TransactionList

import androidx.lifecycle.ViewModel
import com.google.android.gms.common.data.DataBufferObserver
import khalykbayev.bitcoinproject.App
import khalykbayev.bitcoinproject.Models.Transaction
import khalykbayev.bitcoinproject.ObservableTransactionArrayList
import retrofit2.*

class TransactionListViewModel : ViewModel() {
    //var transactionList: ArrayList<Transaction> = ArrayList()
    var transactionList = ObservableTransactionArrayList()
    //var transactionListObserver: Observa

    fun loadTransactions(count: Int) {
        App.api.getTransactionList("hour").enqueue(object :
            Callback<ArrayList<Transaction>> {

            override fun onResponse(call: Call<ArrayList<Transaction>>, response: Response<ArrayList<Transaction>>) {
                if (response.code() == 200) {
                    transactionList.setList(filterTransactionList(response.body()!!, count))
                }
            }
            override fun onFailure(call: Call<ArrayList<Transaction>>, t: Throwable) {
            }
        })
    }

    private fun filterTransactionList(list: ArrayList<Transaction>, count: Int): ArrayList<Transaction> {
        val result: ArrayList<Transaction> = ArrayList()
        for (i in 0 until list.count()) {
            if (count < i) {
                result.add(list[i])
            }
        }
        return result
    }

}
