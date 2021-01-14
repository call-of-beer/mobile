package call.ofbeer.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {

    private const val BASE_URL: String = "http://10.0.2.2:8000/"
   // private const val BASE_URL: String ="http://dinnertime.dizajnstudio.eu/callofbeer/"
    private val logger = HttpLoggingInterceptor()
       .setLevel(HttpLoggingInterceptor.Level.BODY)
    private val cookiesInterceptor: CookieManager by lazy {
        CookieManager()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(this.cookiesInterceptor)
            .addInterceptor(logger)
        return httpClient.build()
    }

    var gson = GsonBuilder()
        .setLenient()
        .create()

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(provideOkHttpClient())
            .build()

        retrofit.create(Api::class.java)

    }

}