package khalykbayev.bitcoinproject.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import khalykbayev.bitcoinproject.Models.Transaction
import khalykbayev.bitcoinproject.R
import kotlinx.android.synthetic.main.transaction_list_item.view.*




class TransactionListAdapter(var transactions:ArrayList<Transaction>): RecyclerView.Adapter<TransactionListAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.date.text = transactions[position].date
        holder.amount.text = transactions[position].amount

        var type = ""
        if (transactions[position].type == 0) {
            type = "Покупка"
        } else if (transactions[position].type == 1) {
            type = "Продажа"
        }
        holder.type.text = type
    }

    fun getId(position: Int): Int? {
        return transactions[position].tid
    }

    override fun onCreateViewHolder(holder: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(holder.context)
        val view = inflater.inflate(R.layout.transaction_list_item, holder, false)
        return ViewHolder(view)
    }
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun refresh() {
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var date: TextView = itemView.transaction_date
        var type: TextView = itemView.transaction_type
        var amount: TextView = itemView.transaction_amount
    }
}