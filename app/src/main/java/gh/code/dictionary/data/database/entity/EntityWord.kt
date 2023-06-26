package gh.code.dictionary.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_entity")
data class EntityWord(
    @PrimaryKey val word: String,
    val phonetic: String?,
    val meanings: List<MeaningR>? = listOf(),
    val phonetics: List<PhoneticR>? = listOf(),
    val sourceUrl: List<String>? = listOf()
)