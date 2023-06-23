package gh.code.dictionary.data.repository

import gh.code.dictionary.core.ConnectionException
import gh.code.dictionary.core.DataNotFoundException
import gh.code.dictionary.core.ParseBackendException
import gh.code.dictionary.data.Mapper
import gh.code.dictionary.data.database.AppDatabase
import gh.code.dictionary.data.database.DictionaryDao
import gh.code.dictionary.data.network.DictionaryApi
import gh.code.dictionary.data.network.models.ResponseWord
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.lang.Appendable

class DictionaryRepositoryImpl(
    private val dictionaryApi: DictionaryApi,
    private val dictionaryDao: DictionaryDao,
    private val mapper: Mapper
) : DictionaryRepository {

    override suspend fun getWord(word: String): List<ResponseWord> = try {
        dictionaryApi.getWord(word = word)
    } catch (e: HttpException) {
        throw createBackendException(e)
    } catch (e: IOException) {
        throw ConnectionException(e)
    } catch (e: Exception) {
        throw e
    }

    override suspend fun saveToHistory(word: String) {
//        dictionaryDao.bookmark()
    }

    override suspend fun removeFromHistory(word: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearHistory() {
        TODO("Not yet implemented")
    }

    override suspend fun bookmark(word: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeBookmark(word: String) {
        TODO("Not yet implemented")
    }

    private fun createBackendException(e: HttpException): Exception = try {
        val jsonObject = JSONObject(e.response()!!.errorBody()!!.string())
        val message = if (jsonObject.has("message")) {
            jsonObject.getString("message")
        } else if (jsonObject.has("error")) {
            jsonObject.getString("error")
        } else {
            "error"
        }
        DataNotFoundException(e.code(), message)
    } catch (e: Exception) {
        ParseBackendException(e)
    }
}