package khalykbayev.bitcoinproject.ExchangeRates

import android.util.Log
import androidx.lifecycle.ViewModel
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Waterfall
import khalykbayev.bitcoinproject.Api.App
import khalykbayev.bitcoinproject.Models.BitcoidPriceIndexValue
import khalykbayev.bitcoinproject.Models.BitcoinPriceIndex
import khalykbayev.bitcoinproject.Models.Transaction
import khalykbayev.bitcoinproject.ObservableBitcoinPriceIndexValueArrayList
import khalykbayev.bitcoinproject.TransactionList.TransactionListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ExchangeRatesViewModel : ViewModel() {

    companion object {
        private const val TAG = "ExchangeRatesViewModel"
    }

    var bpi = ObservableBitcoinPriceIndexValueArrayList()

    fun loadBPI() {

        App.blockchainApi.getBPI().enqueue(object :
            Callback<BitcoinPriceIndex> {

            override fun onResponse(call: Call<BitcoinPriceIndex>, response: Response<BitcoinPriceIndex>) {
                Log.d(TAG, "getBPI onResponse")
                if (response.code() == 200) {
                    Log.d(TAG, "getBPI onResponse 200, status: ${response.body()!!.status}")
                    if (response.body() != null) {
                        if (response.body()!!.values != null) {
                            if (response.body()!!.values!!.count() > 0) {
                                bpi.setList(response.body()!!.values!!)
                            }
                        }
                    }
                    //transactionList.setList(filterTransactionList(response.body()!!, count))
                }
            }
            override fun onFailure(call: Call<BitcoinPriceIndex>, t: Throwable) {
                Log.d(TAG, "getBPI onFailure:${t.localizedMessage}")
            }
        })
    }

    fun getChart(): Waterfall {
        val waterfall = AnyChart.waterfall()

        waterfall.yScale().minimum(0.0)
        waterfall.labels().format("График биткоина \nза последние 2 недели")
//        waterfall.yAxis(0).labels().format("\${%Value}{scale:(1000000)(1)|(mln)}")
//
//        waterfall.labels().format(
//            "function() {\n" +
//                    "      if (this['isTotal']) {\n" +
//                    "        return anychart.format.number(this.absolute, {\n" +
//                    "          scale: true\n" +
//                    "        })\n" +
//                    "      }\n" +
//                    "\n" +
//                    "      return anychart.format.number(this.value, {\n" +
//                    "        scale: true\n" +
//                    "      })\n" +
//                    "    }")

        val data = ArrayList<DataEntry>()

        for (i in 0 until bpi.getList().count()) {
            val value = String.format("%.2f", bpi.getList()[i].y)
            data.add(ValueDataEntry(getDateTime(bpi.getList()[i].x.toString()), value.toDouble()))
        }
        val end = DataEntry()
        //end.setValue("x", "End")
        //end.setValue("isTotal", true)
        data.add(end)

        waterfall.data(data)

        return waterfall
    }

    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("dd/MM")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}
