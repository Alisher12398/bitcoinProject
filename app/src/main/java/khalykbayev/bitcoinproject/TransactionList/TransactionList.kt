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
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.android_app.Adapter.RecyclerTouchListener
import khalykbayev.bitcoinproject.Adapter.TransactionListAdapter
import khalykbayev.bitcoinproject.R
import androidx.recyclerview.widget.DividerItemDecoration
import com.l4digital.fastscroll.FastScrollRecyclerView
import com.squareup.picasso.Picasso
import khalykbayev.bitcoinproject.Adapter.TinyDB
import khalykbayev.bitcoinproject.Api.App
import khalykbayev.bitcoinproject.Models.PicsumImage
import khalykbayev.bitcoinproject.ObservableTransactionArrayList
import khalykbayev.bitcoinproject.getDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList
import android.content.Intent


class TransactionList : Fragment() {

    companion object {
        private const val TAG = "TransactionListFragment"
    }
    private lateinit var viewModel: TransactionListViewModel
    lateinit var transactionRecyclerView: FastScrollRecyclerView
    lateinit var adapter: TransactionListAdapter

    lateinit var tinydb: TinyDB

    lateinit var detail_constraint: ConstraintLayout
    lateinit var detail_background_button: Button
    lateinit var detail_card_image: ImageView
    lateinit var detail_card_close_button: Button
    lateinit var detail_card_id_value: TextView
    lateinit var detail_card_date_value: TextView
    lateinit var detail_card_type_value: TextView
    lateinit var detail_card_price_value: TextView
    lateinit var detail_card_amount_value: TextView
    lateinit var detail_card_share_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        val root = inflater.inflate(R.layout.transaction_list_fragment, container, false)
        configureView(root)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TransactionListViewModel::class.java)
        Log.d(TAG, "onActivityCreated")
        setListeners()
        hideDetailView()
        viewModel.loadTransactions(200)

    }

    override fun onStart() {
        super.onStart()
        tinydb = TinyDB(context!!)
        loadImages()
        Log.d(TAG, "onStart")

    }

    private fun setListeners() {
        viewModel.transactionList.setListener(object : ObservableTransactionArrayList.ChangeListener {
            override fun onChange() {
                adapter = TransactionListAdapter(viewModel.transactionList.getList())
                transactionRecyclerView.adapter = adapter
            }
        })

        transactionRecyclerView.addOnItemTouchListener(RecyclerTouchListener(activity!!.applicationContext, transactionRecyclerView, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                val transaction = viewModel.transactionList.getList()[position]
                detail_card_amount_value.text = transaction.amount
                detail_card_id_value.text = transaction.tid.toString()
                detail_card_price_value.text = transaction.price
                if (transaction.date != null) {
                    detail_card_date_value.text = getDate(transaction.date!!.toLong() * 1000, "dd:MM:yyyy HH:mm:ss")
                }
                if (transaction.type == 0) {
                    detail_card_type_value.text = "Покупка"
                } else {
                    detail_card_type_value.text = "Продажа"
                }
                Picasso.get().load(getImageUrl()).into(detail_card_image)
                showDetailView()
            }

            override fun onLongClick(view: View?, position: Int) {}
        }))

        detail_card_close_button.setOnClickListener {
            hideDetailView()
        }

        detail_card_share_button.setOnClickListener {
            share()
        }

    }

    private fun configureView(root: View) {
        detail_constraint = root.findViewById(R.id.detail_constraint)
        detail_background_button = root.findViewById(R.id.detail_background_button)
        detail_card_image = root.findViewById(R.id.detail_card_image)
        detail_card_close_button = root.findViewById(R.id.detail_card_close_button)
        detail_card_id_value = root.findViewById(R.id.detail_card_id_value)
        detail_card_date_value = root.findViewById(R.id.detail_card_date_value)
        detail_card_type_value = root.findViewById(R.id.detail_card_type_value)
        detail_card_price_value = root.findViewById(R.id.detail_card_price_value)
        detail_card_amount_value = root.findViewById(R.id.detail_card_amount_value)
        detail_card_share_button = root.findViewById(R.id.detail_card_share_button)

        transactionRecyclerView = root.findViewById(R.id.recycler_view)
        transactionRecyclerView.layoutManager = LinearLayoutManager(context)
        transactionRecyclerView.addItemDecoration(
            DividerItemDecoration(
                transactionRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun showDetailView() {
        detail_constraint.isVisible = true
    }

    private fun hideDetailView() {
        detail_constraint.isVisible = false
    }


    fun refresh() {
        viewModel.loadTransactions(200)
    }

    private val key = "key"

    fun getImageUrl(): String {
        val list = tinydb.getListString(key)
        val randomInt = (0 until list.count()).random()
        return list[randomInt]
    }


    fun loadImages() {
        App.picsumApi.getPicsumImageList().enqueue(object :
            Callback<ArrayList<PicsumImage>> {

            override fun onResponse(call: Call<ArrayList<PicsumImage>>, response: Response<ArrayList<PicsumImage>>) {
                Log.d(TAG, "getPicsumImageList onResponse")
                if (response.code() == 200) {
                    Log.d(TAG, "getPicsumImageList onResponse 200, count: ${response.body()!!.count()}")
                    //val json = Gson().toJson(response.body()!!)
                    val list: ArrayList<String> = ArrayList()
                    for (i in 0 until response.body()!!.count()) {
                        if (response.body()!![i].url != null) {
                            list.add(response.body()!![i].url!!)
                        }
                    }
                    tinydb.putListString("key", list)
                }
            }
            override fun onFailure(call: Call<ArrayList<PicsumImage>>, t: Throwable) {
                Log.d(TAG, "getPicsumImageList onFailure:${t.localizedMessage}")
            }
        })
    }

    private fun share() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody: String = "Транзакция:\n" +
                "Тип: ${detail_card_type_value.text}" + "\n" +
                "Время: ${detail_card_date_value.text}" + "\n" +
                "Сумма: ${detail_card_amount_value.text}" + "\n"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Поделиться"))
    }

}
