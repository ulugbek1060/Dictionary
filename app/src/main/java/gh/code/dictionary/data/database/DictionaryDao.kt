package gh.code.dictionary.data.database

import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gh.code.dictionary.data.database.entity.EntityWord
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveToHistory(word: EntityWord)

    @Query("SELECT * FROM word_entity")
    fun getAllWords(): Flow<List<EntityWord>>

    @Query("SELECT * FROM word_entity WHERE word = :word LIMIT 1")
    fun getWordByQuery(word: String): Flow<EntityWord>

    @Query("DELETE FROM word_entity WHERE word = :word")
    suspend fun removeFromHistory(word: String)

    @Query("DELETE FROM word_entity")
    suspend fun clearHistory()

}