package khalykbayev.bitcoinproject

import khalykbayev.bitcoinproject.Models.Transaction

class ObservableTransactionArrayList {

    private var list: ArrayList<Transaction> = ArrayList()
    private var listener: ChangeListener? = null

    fun setList(list: ArrayList<Transaction>) {
        this.list = list
        if (listener != null) listener?.onChange()
    }

    fun getList(): ArrayList<Transaction> {
        return list
    }

    fun getListener(): ChangeListener? {
        return listener
    }

    fun setListener(listener: ChangeListener) {
        this.listener = listener
    }

    interface ChangeListener {
        fun onChange()
    }
}