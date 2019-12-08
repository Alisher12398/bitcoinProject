package khalykbayev.bitcoinproject.Api

import khalykbayev.bitcoinproject.Models.Transaction
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.*
import io.reactivex.Observable
import khalykbayev.bitcoinproject.Models.PicsumImage
import khalykbayev.bitcoinproject.Models.Rate
import kotlin.collections.ArrayList


interface Api {
    @GET("transactions/btcusd/")
    fun getTransactionList(@Query("time") time: String): Call<ArrayList<Transaction>>

    @GET("list?page=2&limit=100")
    fun getPicsumImageList(): Call<ArrayList<PicsumImage>>

    @GET("ticker/btcusd/")
    fun getRates(): Call<Rate>

//    @GET("live?pairs=USDKZT")
//    fun getUsdKzt(): Call<FreeForexAPI>

}