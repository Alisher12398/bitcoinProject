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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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

    lateinit var leftEditText: EditText
    lateinit var rightEditText: EditText

    var leftEditTextLastValue = "-1"
    var rightEditTextLastValue = "-1"

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
        leftEditText = root.findViewById(R.id.left_editText)
        rightEditText = root.findViewById(R.id.right_editText)
    }

    var toRight = "-1"
    var toLeft = "-1"

    private fun setListeners() {
        leftEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null && p0.toString() != toLeft) {
                    if (leftEditTextLastValue != p0.toString()) {
                        leftEditTextLastValue = p0.toString()
                        Log.d(TAG, "left $p0")
                        toRight = viewModel.getBTCToUSDRate(p0).toString()
                        Log.d(TAG, "to Right ${viewModel.getBTCToUSDRate(p0).toString()}")
                        rightEditText.setText(viewModel.getBTCToUSDRate(p0).toString())
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                leftEditText.setSelection(leftEditText.text.length)
            }
        })

        rightEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null && p0.toString() != toRight) {
                    if (p0.toString() != rightEditTextLastValue) {
                        rightEditTextLastValue = p0.toString()
                        toRight = viewModel.getUSDToBTCRate(p0).toString()
                        Log.d(TAG, "right $p0")
                        Log.d(TAG, "To left ${viewModel.getUSDToBTCRate(p0).toString()}")
                        leftEditText.setText(viewModel.getUSDToBTCRate(p0).toString())
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                rightEditText.setSelection(leftEditText.text.length)
            }
        })


    }

}
