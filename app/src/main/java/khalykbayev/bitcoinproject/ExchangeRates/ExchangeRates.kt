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
import androidx.databinding.adapters.NumberPickerBindingAdapter.setValue
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.AnyChart



class ExchangeRates : Fragment() {

    companion object {
        fun newInstance() = ExchangeRates()
    }

    private lateinit var viewModel: ExchangeRatesViewModel
    private lateinit var chartView: AnyChartView

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
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ExchangeRatesViewModel::class.java)
        addChart()
        // TODO: Use the ViewModel
    }

    fun addChart() {
        val waterfall = AnyChart.waterfall()

        waterfall.yScale().minimum(0.0)

        waterfall.yAxis(0).labels().format("\${%Value}{scale:(1000000)(1)|(mln)}")
        waterfall.labels().enabled(true)
        waterfall.labels().format(
                "function() {\n" +
                "      if (this['isTotal']) {\n" +
                "        return anychart.format.number(this.absolute, {\n" +
                "          scale: true\n" +
                "        })\n" +
                "      }\n" +
                "\n" +
                "      return anychart.format.number(this.value, {\n" +
                "        scale: true\n" +
                "      })\n" +
                "    }")

        val data = ArrayList<DataEntry>()
        data.add(ValueDataEntry("Start", 23000000))
        data.add(ValueDataEntry("Jan", 2200000))
        data.add(ValueDataEntry("Feb", -4600000))
        data.add(ValueDataEntry("Mar", -9100000))
        data.add(ValueDataEntry("Apr", 3700000))
        data.add(ValueDataEntry("May", -2100000))
        data.add(ValueDataEntry("Jun", 5300000))
        data.add(ValueDataEntry("Jul", 3100000))
        data.add(ValueDataEntry("Aug", -1500000))
        data.add(ValueDataEntry("Sep", 4200000))
        data.add(ValueDataEntry("Oct", 5300000))
        data.add(ValueDataEntry("Nov", -1500000))
        data.add(ValueDataEntry("Dec", 5100000))
        val end = DataEntry()
        end.setValue("x", "End")
        end.setValue("isTotal", true)
        data.add(end)

        waterfall.data(data)

        chartView.setChart(waterfall)
    }

}
