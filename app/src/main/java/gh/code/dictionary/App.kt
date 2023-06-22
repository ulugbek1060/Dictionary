package gh.code.dictionary

import android.app.Application
import android.util.Log
import gh.code.dictionary.core.Resource
import gh.code.dictionary.core.ResourceImp
import gh.code.dictionary.data.api.DictionaryApi
import gh.code.dictionary.data.repository.DictionaryRepository
import gh.code.dictionary.data.repository.DictionaryRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    private lateinit var client: RetrofitClient

    lateinit var dictionaryRepository: DictionaryRepository
    lateinit var resource: Resource

    override fun onCreate() {
        super.onCreate()
        client = RetrofitClient
        resource = ResourceImp(this)
        dictionaryRepository = DictionaryRepositoryImpl(client.getDictionaryApi())
    }
}


object RetrofitClient {

    private const val TAG = "App_RetrofitClient"

    private var retrofit: Retrofit? = null

    private fun getClient(): Retrofit? {
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

    fun getDictionaryApi(): DictionaryApi {
        return getClient()?.create(DictionaryApi::class.java)!!
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


