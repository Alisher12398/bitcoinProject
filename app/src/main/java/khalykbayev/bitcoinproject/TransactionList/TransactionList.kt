package khalykbayev.bitcoinproject.TransactionList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import khalykbayev.bitcoinproject.Api.BitstampApi
import khalykbayev.bitcoinproject.App
import khalykbayev.bitcoinproject.Auth.AuthActivity
import khalykbayev.bitcoinproject.Models.Transaction

import khalykbayev.bitcoinproject.R
//import okhttp3.*
import retrofit2.*
import retrofit2.Response
import java.io.IOException

class TransactionList : Fragment() {

    //private var client = OkHttpClient()

    companion object {
        fun newInstance() = TransactionList()
        private const val TAG = "TransactionListActivity"
    }

    private lateinit var viewModel: TransactionListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.transaction_list_fragment, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TransactionListViewModel::class.java)
        // TODO: Use the ViewModel
        Log.d(TAG, "onActivityCreated")


    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")

        //run("https://www.bitstamp.net/api/transactions/?time=minute")


        App.api.getTransactionList("minute").enqueue(object :
            Callback<ArrayList<Transaction>> {

            override fun onResponse(call: Call<ArrayList<Transaction>>, response: Response<ArrayList<Transaction>>) {
                if (response.code() == 200) {
                    val response = response.body()!!
                    Log.d(TAG, "bodybody success:${response[2].amount}")
                } else {
                    Log.d(TAG, "bodybody success not 200")
                }
            }
            override fun onFailure(call: Call<ArrayList<Transaction>>, t: Throwable) {
                Log.d(TAG, "bodybody onFailure")
            }
        })
    }

//    fun run(url: String) {
//        val request = Request.Builder()
//            .url(url)
//            .build()
////= println(response.body()?.string())
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d(TAG, "response onFailure")
//            }
//            override fun onResponse(call: Call, response: Response) {
//                Log.d(TAG, "response true:${response.body()?.string()}")
//            }
//        })
//    }

}
