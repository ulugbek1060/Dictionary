package gh.code.dictionary.data.database

import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import gh.code.dictionary.data.database.entity.EntityWord
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {

    @Insert
    suspend fun saveToHistory(word: EntityWord)

    @Query("SELECT * FROM word_entity")
    fun getAllWords(): Flow<List<EntityWord>>

    @Query("SELECT * FROM word_entity WHERE phonetic = :phonetic LIMIT 1")
    fun getWordByQuery(phonetic: String): Flow<EntityWord>

    @Query("DELETE FROM word_entity WHERE phonetic = :phonetic")
    suspend fun removeFromHistory(phonetic: String)

    @Query("DELETE FROM word_entity")
    suspend fun clearHistory()

}