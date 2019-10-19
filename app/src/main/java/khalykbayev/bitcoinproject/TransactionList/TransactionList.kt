package khalykbayev.bitcoinproject.TransactionList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.android_app.Adapter.RecyclerTouchListener
import khalykbayev.bitcoinproject.Adapter.TransactionListAdapter
import khalykbayev.bitcoinproject.Models.Transaction
import khalykbayev.bitcoinproject.R
import androidx.recyclerview.widget.DividerItemDecoration
import khalykbayev.bitcoinproject.ObservableTransactionArrayList
import kotlin.collections.ArrayList



class TransactionList : Fragment() {

    companion object {
        private const val TAG = "TransactionListFragment"
    }
    private lateinit var viewModel: TransactionListViewModel
    private var transactionList: ArrayList<Transaction> = ArrayList()
    lateinit var transactionRecyclerView: RecyclerView
    lateinit var adapter: TransactionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "onCreateView")
        val root = inflater.inflate(R.layout.transaction_list_fragment, container, false)

        transactionRecyclerView = root.findViewById(R.id.transactions_list)
        transactionRecyclerView.layoutManager = LinearLayoutManager(context)
        transactionRecyclerView.addItemDecoration(
            DividerItemDecoration(
                transactionRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        transactionRecyclerView.addOnItemTouchListener(RecyclerTouchListener(activity!!.applicationContext, transactionRecyclerView, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                val id = adapter.getId(position)
                Toast.makeText(activity, "click : $position, :id: $id", Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(view: View?, position: Int) {
                Toast.makeText(activity, "LongPress : $position", Toast.LENGTH_SHORT).show()
            }
        }))

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TransactionListViewModel::class.java)
        // TODO: Use the ViewModel
        Log.d(TAG, "onActivityCreated")

        //viewModel.transactionList.listener()

        viewModel.transactionList.setListener(object : ObservableTransactionArrayList.ChangeListener {
            override fun onChange() {
                if (viewModel.transactionList.getList().count() == 0) {
                    Toast.makeText(context, "0", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "   ne 0: ${viewModel.transactionList.getList().count()}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.loadTransactions(300)

        //MainActivity().showProgressDialog()

//        App.api.getTransactionList("minute").enqueue(object :
//            Callback<ArrayList<Transaction>> {
//
//            override fun onResponse(call: Call<ArrayList<Transaction>>, response: Response<ArrayList<Transaction>>) {
//                if (response.code() == 200) {
//                    val response = response.body()!!
//                    transactionList = response
//                    adapter = TransactionListAdapter(transactionList)
//                    transactionRecyclerView.adapter = adapter
//                    adapter.refresh()
//                    Log.d(TAG, "bodybody success count:${response.count()}")
//                }
//            }
//            override fun onFailure(call: Call<ArrayList<Transaction>>, t: Throwable) {
//                Log.d(TAG, "bodybody onFailure: ${t.localizedMessage}")
//            }
//        })

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")

    }

    //val array: ArrayList<Transaction> = ArrayList()

//    override fun onResume() {
//        super.onResume()
//        Log.d(TAG, "onResume()")
//        array.add(Transaction("dsf", 5, "sdf", 0, "2344"))
//        viewModel.transactionList.setList(array)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d(TAG, "onPause()")
//    }

}
