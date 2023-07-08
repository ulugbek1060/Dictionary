package gh.code.dictionary

import android.content.Context
import androidx.room.Room
import gh.code.dictionary.core.CommonUi
import gh.code.dictionary.core.Resource
import gh.code.dictionary.core.ResourceImp
import gh.code.dictionary.core.ScreenCommunication
import gh.code.dictionary.data.database.AppDatabase
import gh.code.dictionary.data.network.RetrofitClient
import gh.code.dictionary.data.repository.DictionaryRepository
import gh.code.dictionary.data.repository.DictionaryRepositoryImpl
import gh.code.dictionary.utils.ConnectivityManagerNetworkMonitor
import gh.code.dictionary.utils.Logger
import gh.code.dictionary.utils.LoggerImpl
import gh.code.dictionary.utils.Mapper
import gh.code.dictionary.utils.MapperImpl
import gh.code.dictionary.utils.NetworkMonitor

object DependencyProvider {

    private lateinit var applicationContext: Context
    fun init(context: Context) {
        applicationContext = context
    }

    private val mapper: Mapper = MapperImpl()

    private val appDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    private val dictionaryApi by lazy {
        RetrofitClient.getDictionaryApi(
            retrofit = RetrofitClient.getClient()
        )
    }

    private val dictionaryDao by lazy { appDatabase.dictionaryDao() }

    val resource: Resource by lazy { ResourceImp(applicationContext) }

    val commonUi: CommonUi by lazy { ScreenCommunication(applicationContext) }

    val logger: Logger by lazy { LoggerImpl(applicationContext) }

    val networkMonitor: NetworkMonitor by lazy {
        ConnectivityManagerNetworkMonitor(applicationContext)
    }

    val repository: DictionaryRepository by lazy {
        DictionaryRepositoryImpl(
            dictionaryApi = dictionaryApi,
            dictionaryDao = dictionaryDao,
            mapper = mapper,
        )
    }

    private const val DB_NAME = "cd-ghost-dictionary"

}