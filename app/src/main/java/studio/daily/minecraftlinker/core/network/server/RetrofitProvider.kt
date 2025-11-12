package studio.daily.minecraftlinker.core.network.server

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitProvider {
    private val gson = GsonBuilder()
        .disableHtmlEscaping()
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.22:8000/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val serverAPI : ServerAPI by lazy {
        retrofit.create(ServerAPI::class.java)
    }
}