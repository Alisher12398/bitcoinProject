package khalykbayev.bitcoinproject.TransactionList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
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
    private var transactionList: ArrayList<Transaction> = ArrayList()
    lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.transaction_list_fragment, container, false)

        val adapter = ArrayAdapter(root.context,
            R.layout.transactions_listview_item, array)

        listView = root.findViewById(R.id.transactions_listview)
        adapter.setNotifyOnChange(true)
        listView.adapter = adapter
        listView.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View,
                                     position: Int, id: Long) {

                // value of item that is clicked
                val itemValue = listView.getItemAtPosition(position) as String

                // Toast the values
                Toast.makeText(context,
                    "Position :$position\nItem Value : $itemValue", Toast.LENGTH_LONG)
                    .show()
            }
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TransactionListViewModel::class.java)
        // TODO: Use the ViewModel
        Log.d(TAG, "onActivityCreated")

        App.api.getTransactionList("minute").enqueue(object :
            Callback<ArrayList<Transaction>> {

            override fun onResponse(call: Call<ArrayList<Transaction>>, response: Response<ArrayList<Transaction>>) {
                if (response.code() == 200) {
                    val response = response.body()!!
                    transactionList = response
                    listView.invalidateViews()
                    Log.d(TAG, "bodybody success count:${transactionList.count()}")
                    Log.d(TAG, "bodybody success 2:${transactionList[2].amount}")
                }
            }
            override fun onFailure(call: Call<ArrayList<Transaction>>, t: Throwable) {
                Log.d(TAG, "bodybody onFailure")
            }
        })

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")

    }

}
