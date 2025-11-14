package studio.daily.minecraftlinker.core.network.mojang

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MojangRetrofitProvider {
    private val gson = GsonBuilder()
        .disableHtmlEscaping()
        .create()

    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            )
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://sessionserver.mojang.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    val mojangApi: MojangApi by lazy {
        retrofit.create(MojangApi::class.java)
    }

}