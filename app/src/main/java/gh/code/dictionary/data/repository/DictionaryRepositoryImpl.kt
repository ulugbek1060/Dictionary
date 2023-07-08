package gh.code.dictionary.data.repository

import gh.code.dictionary.core.ConnectionException
import gh.code.dictionary.core.DataNotFoundException
import gh.code.dictionary.core.EmptyFieldException
import gh.code.dictionary.core.ParseBackendException
import gh.code.dictionary.utils.Mapper
import gh.code.dictionary.data.database.DictionaryDao
import gh.code.dictionary.data.network.DictionaryApi
import gh.code.dictionary.data.network.models.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class DictionaryRepositoryImpl(
    private val dictionaryApi: DictionaryApi,
    private val dictionaryDao: DictionaryDao,
    private val mapper: Mapper
) : DictionaryRepository {

    override suspend fun getHistory(): Flow<List<Word>> {
        return dictionaryDao
            .getAllWords()
            .map { list ->
                list
                    .map { entity -> mapper.toModel(entity) }
                    .reversed()
            }
    }

    override suspend fun searchWord(word: String): List<Word> = try {
        dictionaryApi.getWord(word = word)
    } catch (e: HttpException) {
        throw createBackendException(e)
    } catch (e: IOException) {
        throw ConnectionException(e)
    } catch (e: Exception) {
        throw e
    }

    override suspend fun saveToHistory(word: Word) {
        if (word.word.isNullOrBlank()) throw EmptyFieldException()
        dictionaryDao.saveToHistory(mapper.fromModel(word))
    }

    override suspend fun removeFromHistory(word: Word) {
        if (word.word.isNullOrBlank()) throw DataNotFoundException("Word does not exist!")
        dictionaryDao.removeFromHistory(word.word)
    }

    override fun getWordFromHistory(phonetic: String): Flow<Word> {
        return dictionaryDao
            .getWordByQuery(phonetic)
            .map {
                mapper.toModel(it)
            }
    }

    override suspend fun clearHistory() = dictionaryDao.clearHistory()

    private fun createBackendException(e: HttpException): Exception = try {
        val jsonObject = JSONObject(e.response()!!.errorBody()!!.string())
        val message = if (jsonObject.has("message")) {
            jsonObject.getString("message")
        } else if (jsonObject.has("error")) {
            jsonObject.getString("error")
        } else {
            "error"
        }
        DataNotFoundException(message)
    } catch (e: Exception) {
        ParseBackendException(e)
    }
}