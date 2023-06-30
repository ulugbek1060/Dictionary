package gh.code.dictionary.data.network

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {

    private const val TAG = "App_RetrofitClient"

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    fun getDictionaryApi(retrofit: Retrofit?): DictionaryApi {
        return retrofit?.create(DictionaryApi::class.java)!!
    }

    private fun createClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(createInterceptor())
            .addInterceptor(createLoggingInterceptor()).build()
    }

    private fun createInterceptor(): Interceptor {
        return Interceptor { chain ->
            Log.d(TAG, "createInterceptor: ${chain.request().url}")
            val newBuilder = chain.request().newBuilder()
            return@Interceptor chain.proceed(newBuilder.build())
        }
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/"
}

