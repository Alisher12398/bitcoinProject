package khalykbayev.bitcoinproject

import khalykbayev.bitcoinproject.Models.BitcoidPriceIndexValue

class ObservableBitcoinPriceIndexValueArrayList {

    private var list: ArrayList<BitcoidPriceIndexValue> = ArrayList()
    private var listener: ChangeListener? = null

    fun setList(list: ArrayList<BitcoidPriceIndexValue>) {
        this.list = list
        if (listener != null) listener?.onChange()
    }

    fun getList(): ArrayList<BitcoidPriceIndexValue> {
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