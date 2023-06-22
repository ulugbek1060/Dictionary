package gh.code.dictionary.data.repository

import gh.code.dictionary.core.ConnectionException
import gh.code.dictionary.core.DataNotFoundException
import gh.code.dictionary.core.ParseBackendException
import gh.code.dictionary.data.api.DictionaryApi
import gh.code.dictionary.data.models.ResponseWord
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class DictionaryRepositoryImpl(
    private val dictionaryApi: DictionaryApi,
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
        TODO("Not yet implemented")
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
        } else { "error" }
        DataNotFoundException(e.code(), message)
    } catch (e: Exception) {
        ParseBackendException(e)
    }
}