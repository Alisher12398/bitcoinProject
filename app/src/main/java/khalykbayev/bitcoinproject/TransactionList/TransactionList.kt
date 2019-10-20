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
import java.lang.Exception


class TransactionList : Fragment() {

    companion object {
        private const val TAG = "TransactionListFragment"
    }
    private lateinit var viewModel: TransactionListViewModel
    lateinit var transactionRecyclerView: FastScrollRecyclerView
    lateinit var adapter: TransactionListAdapter

    lateinit var tinydb: TinyDB

    lateinit var detailConstraint: ConstraintLayout
    lateinit var detailBackgroundButton: Button
    lateinit var detailCardImage: ImageView
    lateinit var detailCardCloseButton: Button
    lateinit var detailCardIdValue: TextView
    lateinit var detailCardDateValue: TextView
    lateinit var detailCardTypeValue: TextView
    lateinit var detailCardPriceValue: TextView
    lateinit var detailCardAmountValue: TextView
    lateinit var detailCardShareButton: Button
    lateinit var loadingTextview: TextView

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
        loadingTextview.isVisible = true
    }

    override fun onStart() {
        super.onStart()
        try {
            tinydb = TinyDB(context!!)
        } catch (e: Exception) {}
        loadImages()
        Log.d(TAG, "onStart")

    }

    private fun setListeners() {
        viewModel.transactionList.setListener(object : ObservableTransactionArrayList.ChangeListener {
            override fun onChange() {
                adapter = TransactionListAdapter(viewModel.transactionList.getList())
                transactionRecyclerView.adapter = adapter
                loadingTextview.isVisible = viewModel.transactionList.getList().count() == 0

            }
        })

        transactionRecyclerView.addOnItemTouchListener(RecyclerTouchListener(activity!!.applicationContext, transactionRecyclerView, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                getSelectedTransactionInfo(position)
                showDetailView()
            }

            override fun onLongClick(view: View?, position: Int) {}
        }))

        detailCardCloseButton.setOnClickListener {
            hideDetailView()
        }

        detailCardShareButton.setOnClickListener {
            share()
        }

    }

    private fun configureView(root: View) {
        detailConstraint = root.findViewById(R.id.detail_constraint)
        detailBackgroundButton = root.findViewById(R.id.detail_background_button)
        detailCardImage = root.findViewById(R.id.detail_card_image)
        detailCardCloseButton = root.findViewById(R.id.detail_card_close_button)
        detailCardIdValue = root.findViewById(R.id.detail_card_id_value)
        detailCardDateValue = root.findViewById(R.id.detail_card_date_value)
        detailCardTypeValue = root.findViewById(R.id.detail_card_type_value)
        detailCardPriceValue = root.findViewById(R.id.detail_card_price_value)
        detailCardAmountValue = root.findViewById(R.id.detail_card_amount_value)
        detailCardShareButton = root.findViewById(R.id.detail_card_share_button)
        loadingTextview = root.findViewById(R.id.loading_textview)

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
        detailConstraint.isVisible = true
    }

    private fun hideDetailView() {
        detailConstraint.isVisible = false
    }


    fun refresh() {
        viewModel.loadTransactions(200)
    }

    private val key = "key"

    fun getImageUrl(): String {
        var list: ArrayList<String> = ArrayList()
        try {
            list = tinydb.getListString(key)
        } catch (e: Exception) {}
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
                    try {
                        tinydb.putListString("key", list)
                    } catch (e: Exception) {}
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
                "Тип: ${detailCardTypeValue.text}" + "\n" +
                "Время: ${detailCardDateValue.text}" + "\n" +
                "Сумма: ${detailCardAmountValue.text}" + "\n"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Поделиться"))
    }

    private fun getSelectedTransactionInfo(position: Int) {
        val transaction = viewModel.transactionList.getList()[position]
        Picasso.get().load(getImageUrl()).into(detailCardImage)
        detailCardAmountValue.text = transaction.amount
        detailCardIdValue.text = transaction.tid.toString()
        detailCardPriceValue.text = transaction.price
        if (transaction.date != null) {
            detailCardDateValue.text = getDate(transaction.date!!.toLong() * 1000, "dd:MM:yyyy HH:mm:ss")
        }
        if (transaction.type == 0) {
            detailCardTypeValue.text = "Покупка"
        } else {
            detailCardTypeValue.text = "Продажа"
        }
    }

}
