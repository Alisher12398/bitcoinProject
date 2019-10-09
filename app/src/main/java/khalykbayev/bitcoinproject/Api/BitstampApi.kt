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
import kotlin.collections.ArrayList


interface BitstampApi {
    @GET("transactions")
    fun getTransactionList(@Query("time") time: String): Call<ArrayList<Transaction>>

//    companion object Factory {
//        fun create(): BitstampApi {
//            val retrofit = Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("https://www.bitstamp.net/api/")
//                .build()
//
//            return retrofit.create(BitstampApi::class.java)
//        }
//    }
}