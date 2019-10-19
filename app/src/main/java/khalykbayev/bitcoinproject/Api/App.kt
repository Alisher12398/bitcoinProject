package khalykbayev.bitcoinproject.Api

import android.app.Application

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    //private lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()

//        retrofit = Retrofit.Builder()
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://www.bitstamp.net/api/")
//            .build()
//
//        api = retrofit.create(BitstampApi::class.java)
    }

//    fun create(): BitstampApi {
//        retrofit = Retrofit.Builder()
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://www.bitstamp.net/api/")
//            .build()
//
//        return retrofit.create(BitstampApi::class.java)
//    }


    companion object {
        val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://www.bitstamp.net/api/v2/")
        .build()
        var api: BitstampApi = retrofit.create(BitstampApi::class.java)
    }
}