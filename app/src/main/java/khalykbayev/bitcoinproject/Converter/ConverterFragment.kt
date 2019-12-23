package khalykbayev.bitcoinproject.Converter

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import khalykbayev.bitcoinproject.Api.App
import khalykbayev.bitcoinproject.Models.Transaction

import khalykbayev.bitcoinproject.R
import khalykbayev.bitcoinproject.TransactionList.TransactionListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConverterFragment : Fragment() {

    companion object {
        fun newInstance() = ConverterFragment()
        private const val TAG = "ConverterFragment"
    }

    private lateinit var viewModel: ConverterViewModel

    lateinit var fromEditText: EditText
    lateinit var fromTextView: TextView
    lateinit var toTextView: TextView
    lateinit var toValueTextView: TextView
    lateinit var changeCurrencyButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.converter_fragment, container, false)
        configureView(root)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        viewModel = ViewModelProviders.of(this).get(ConverterViewModel::class.java)
        viewModel.loadRates()
    }

    private fun configureView(root: View) {
        fromEditText = root.findViewById(R.id.from_editText)
        fromTextView = root.findViewById(R.id.from_textview)
        toTextView = root.findViewById(R.id.to_textview)
        toValueTextView = root.findViewById(R.id.to_value_textview)
        changeCurrencyButton = root.findViewById(R.id.change_currency_button)
    }


    private fun setListeners() {
        fromEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    var value = p0.toString()
                    toValueTextView.text = viewModel.getRateForCurrentCurrency(value)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

    }

}
