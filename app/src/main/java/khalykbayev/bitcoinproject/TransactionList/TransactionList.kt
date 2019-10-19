package khalykbayev.bitcoinproject.TransactionList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.android_app.Adapter.RecyclerTouchListener
import khalykbayev.bitcoinproject.Adapter.TransactionListAdapter
import khalykbayev.bitcoinproject.Models.Transaction
import khalykbayev.bitcoinproject.R
import androidx.recyclerview.widget.DividerItemDecoration
import com.l4digital.fastscroll.FastScrollRecyclerView
import khalykbayev.bitcoinproject.ObservableTransactionArrayList
import kotlinx.android.synthetic.main.transaction_list_fragment.*
import kotlin.collections.ArrayList



class TransactionList : Fragment() {

    companion object {
        private const val TAG = "TransactionListFragment"
    }
    private lateinit var viewModel: TransactionListViewModel
    lateinit var transactionRecyclerView: FastScrollRecyclerView
    lateinit var adapter: TransactionListAdapter

    lateinit var detail_constraint: ConstraintLayout
    lateinit var detail_background_button: Button
    lateinit var detail_card_image: ImageView
    lateinit var detail_card_close_button: Button
    lateinit var detail_card_id_value: TextView
    lateinit var detail_card_date_value: TextView
    lateinit var detail_card_type_value: TextView
    lateinit var detail_card_price_value: TextView
    lateinit var detail_card_amount_value: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "onCreateView")
        val root = inflater.inflate(R.layout.transaction_list_fragment, container, false)

        detail_constraint = root.findViewById(R.id.detail_constraint)
        detail_background_button = root.findViewById(R.id.detail_background_button)
        detail_card_image = root.findViewById(R.id.detail_card_image)
        detail_card_close_button = root.findViewById(R.id.detail_card_close_button)
        detail_card_id_value = root.findViewById(R.id.detail_card_id_value)
        detail_card_date_value = root.findViewById(R.id.detail_card_date_value)
        detail_card_type_value = root.findViewById(R.id.detail_card_type_value)
        detail_card_price_value = root.findViewById(R.id.detail_card_price_value)
        detail_card_amount_value = root.findViewById(R.id.detail_card_amount_value)
        
        transactionRecyclerView = root.findViewById(R.id.recycler_view)
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
        viewModel.loadTransactions(200)

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

    fun refresh() {
        viewModel?.loadTransactions(200)
    }

}
