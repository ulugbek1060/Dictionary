package gh.code.dictionary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gh.code.dictionary.data.database.entity.EntityWord


@Database(entities = [EntityWord::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
}