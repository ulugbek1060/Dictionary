package gh.code.dictionary.data.repository

import gh.code.dictionary.data.network.models.Word
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {

    suspend fun searchWord(word: String): List<Word>

    suspend fun saveToHistory(word: Word)

    suspend fun removeFromHistory(word: Word)

    fun getWordFromHistory(phonetic: String): Flow<Word>

    suspend fun clearHistory()

    suspend fun getHistory(): Flow<List<Word>>

}