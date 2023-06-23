package gh.code.dictionary.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_entity")
data class EntityWord(
    val word: String?,
    @PrimaryKey val phonetic: String,
    val meanings: List<MeaningR>? = listOf(),
    val sourceUrl: List<String>? = listOf()
)