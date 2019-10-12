package khalykbayev.bitcoinproject.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import khalykbayev.bitcoinproject.Models.Transaction
import khalykbayev.bitcoinproject.R

class TransactionListAdapter(private val context: Context,
                    private val dataSource: ArrayList<Transaction>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Transaction {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rowView = inflater.inflate(R.layout.transactions_listview_item, parent, false)

        val transactionTypeTextView = rowView.findViewById(R.id.transaction_type) as TextView
        val transactionDateTextView = rowView.findViewById(R.id.transaction_date) as TextView
        val transactionAmountTextView = rowView.findViewById(R.id.transaction_amount) as TextView

        val transaction = getItem(position)

        if (transaction.type == 0) {
            transactionTypeTextView.text = "Покупка"
        } else {
            transactionTypeTextView.text = "Продажа"
        }
        transactionDateTextView.text = transaction.date
        transactionAmountTextView.text = transaction.amount

        return rowView
    }
}