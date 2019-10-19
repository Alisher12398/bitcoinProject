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
        Log.d(TAG, "onActivityCreated")
        setListener()
        viewModel.loadTransactions(500)

    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")

    }

    private fun setListener() {
        viewModel.transactionList.setListener(object : ObservableTransactionArrayList.ChangeListener {
            override fun onChange() {
                adapter = TransactionListAdapter(viewModel.transactionList.getList())
                transactionRecyclerView.adapter = adapter
            }
        })
    }
}
