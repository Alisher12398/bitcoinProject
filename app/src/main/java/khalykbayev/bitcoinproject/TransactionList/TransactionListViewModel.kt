package khalykbayev.bitcoinproject.TransactionList

import android.content.Context
import android.util.JsonWriter
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSerializer
import khalykbayev.bitcoinproject.Api.App
import khalykbayev.bitcoinproject.Models.PicsumImage
import khalykbayev.bitcoinproject.Models.Transaction
import khalykbayev.bitcoinproject.ObservableTransactionArrayList
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.*
import com.google.gson.JsonObject
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.SharedPreferences
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.io.File
import java.io.FileOutputStream
import khalykbayev.bitcoinproject.Adapter.TinyDB
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class TransactionListViewModel : ViewModel() {

    companion object {
        private const val TAG = "TransactionListViewModel"
    }

    //var transactionList: ArrayList<Transaction> = ArrayList()
    var transactionList = ObservableTransactionArrayList()

    fun loadTransactions(count: Int) {
        App.bitstampApi.getTransactionList("hour").enqueue(object :
            Callback<ArrayList<Transaction>> {

            override fun onResponse(call: Call<ArrayList<Transaction>>, response: Response<ArrayList<Transaction>>) {
                Log.d(TAG, "getTransactionList onResponse")
                if (response.code() == 200) {
                    Log.d(TAG, "getTransactionList onResponse 200, count: ${response.body()!!.count()}")
                    transactionList.setList(filterTransactionList(response.body()!!, count))
                }
            }
            override fun onFailure(call: Call<ArrayList<Transaction>>, t: Throwable) {
                Log.d(TAG, "getTransactionList onFailure:${t.localizedMessage}")
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
