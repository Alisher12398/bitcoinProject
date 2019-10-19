package khalykbayev.bitcoinproject.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import khalykbayev.bitcoinproject.Models.Transaction
import khalykbayev.bitcoinproject.R
import khalykbayev.bitcoinproject.getDate
import kotlinx.android.synthetic.main.transaction_list_item.view.*
import kotlin.collections.ArrayList


class TransactionListAdapter(var transactions:ArrayList<Transaction>): RecyclerView.Adapter<TransactionListAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (transactions[position].date != null) {
            holder.date.text = getDate(transactions[position].date!!.toLong() * 1000,"dd/MM/yyyy hh:mm")
        }
        holder.amount.text = transactions[position].amount

        var type = ""
        if (transactions[position].type == 0) {
            holder.view.setBackgroundResource(R.color.red)
            type = "Покупка"
        } else if (transactions[position].type == 1) {
            type = "Продажа"
        }
        holder.type.text = type
        holder.number.text = (position + 1).toString() + "."

        //Picasso.get().load("https://source.unsplash.com/random/300x300").into(holder.image)
    }

    fun getId(position: Int): Int? {
        return transactions[position].tid
    }

    override fun onCreateViewHolder(holder: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(holder.context)
        val view = inflater.inflate(R.layout.transaction_list_item, holder, false)
        return ViewHolder(view)
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
        var number: TextView = itemView.transaction_number
        var image: ImageView = itemView.transaction_image
        var view: View = itemView.transaction_view
    }
}