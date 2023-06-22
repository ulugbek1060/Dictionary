package gh.code.dictionary.data.repository

import gh.code.dictionary.data.models.ResponseWord

interface DictionaryRepository {

    suspend fun getWord(word: String): List<ResponseWord>

    suspend fun saveToHistory(word: String)

    suspend fun removeFromHistory(word: String)

    suspend fun clearHistory()

    suspend fun bookmark(word: String)

    suspend fun removeBookmark(word: String)

}