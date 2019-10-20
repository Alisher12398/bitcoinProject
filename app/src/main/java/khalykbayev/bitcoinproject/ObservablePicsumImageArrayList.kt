package khalykbayev.bitcoinproject

import khalykbayev.bitcoinproject.Models.PicsumImage

class ObservablePicsumImageArrayList {

    private var list: ArrayList<PicsumImage> = ArrayList()
    private var listener: ChangeListener? = null

    fun setList(list: ArrayList<PicsumImage>) {
        this.list = list
        if (listener != null) listener?.onChange()
    }

    fun getList(): ArrayList<PicsumImage> {
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