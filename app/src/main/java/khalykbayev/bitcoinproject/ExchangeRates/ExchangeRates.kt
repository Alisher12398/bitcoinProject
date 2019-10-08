package khalykbayev.bitcoinproject.ExchangeRates

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import khalykbayev.bitcoinproject.R

class ExchangeRates : Fragment() {

    companion object {
        fun newInstance() = ExchangeRates()
    }

    private lateinit var viewModel: ExchangeRatesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.exchange_rates_fragment, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ExchangeRatesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
