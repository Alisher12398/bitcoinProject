package khalykbayev.bitcoinproject.ExchangeRates

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anychart.AnyChartView

import khalykbayev.bitcoinproject.R
import android.R.attr.data
import android.util.Log
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.adapters.NumberPickerBindingAdapter.setValue
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.AnyChart
import khalykbayev.bitcoinproject.Adapter.TransactionListAdapter
import khalykbayev.bitcoinproject.Api.App
import khalykbayev.bitcoinproject.Models.BitcoidPriceIndexValue
import khalykbayev.bitcoinproject.Models.BitcoinPriceIndex
import khalykbayev.bitcoinproject.Models.Transaction
import khalykbayev.bitcoinproject.ObservableBitcoinPriceIndexValueArrayList
import khalykbayev.bitcoinproject.ObservableTransactionArrayList
import khalykbayev.bitcoinproject.TransactionList.TransactionListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ExchangeRates : Fragment() {

    companion object {
        private const val TAG = "ExchangeRatesFragment"
    }

    fun refresh() {
        viewModel.loadBPI()
    }

    private lateinit var viewModel: ExchangeRatesViewModel
    private lateinit var chartView: AnyChartView
    private lateinit var loadingTextview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.exchange_rates_fragment, container, false)
        chartView = root.findViewById(R.id.any_chart_view)
        loadingTextview = root.findViewById(R.id.loading_textview_exchange)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ExchangeRatesViewModel::class.java)
        setListeners()
        viewModel.loadBPI()
        loadingTextview.isVisible = true
    }

    private fun setListeners() {
        viewModel.bpi.setListener(object :
            ObservableBitcoinPriceIndexValueArrayList.ChangeListener {
            override fun onChange() {
                chartView.setChart(viewModel.getChart())
                loadingTextview.isVisible = viewModel.bpi.getList().count() == 0
            }
        })
    }

}
