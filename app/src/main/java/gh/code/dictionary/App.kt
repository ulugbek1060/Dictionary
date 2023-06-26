package gh.code.dictionary

import android.app.Application
import gh.code.dictionary.core.Resource
import gh.code.dictionary.core.ResourceImp
import gh.code.dictionary.data.Mapper
import gh.code.dictionary.data.MapperImpl
import gh.code.dictionary.data.database.AppDatabase
import gh.code.dictionary.data.database.DatabaseBuilder
import gh.code.dictionary.data.network.RetrofitClient
import gh.code.dictionary.data.repository.DictionaryRepository
import gh.code.dictionary.data.repository.DictionaryRepositoryImpl
import gh.code.dictionary.utils.Logger
import gh.code.dictionary.utils.LoggerImpl

class App : Application() {

    private lateinit var client: RetrofitClient
    private lateinit var databaseBuilder: DatabaseBuilder
    private lateinit var appDatabase: AppDatabase
    private lateinit var mapper: Mapper

    lateinit var dictionaryRepository: DictionaryRepository
    lateinit var resource: Resource
    lateinit var logger: Logger

    override fun onCreate() {
        super.onCreate()
        client = RetrofitClient
        databaseBuilder = DatabaseBuilder
        mapper = MapperImpl()
        logger = LoggerImpl(this)
        appDatabase = databaseBuilder.getInstance(this)
        resource = ResourceImp(this)

        dictionaryRepository = DictionaryRepositoryImpl(
            dictionaryApi = client.getDictionaryApi(),
            dictionaryDao = appDatabase.dictionaryDao(),
            mapper = mapper,
        )
    }
}



