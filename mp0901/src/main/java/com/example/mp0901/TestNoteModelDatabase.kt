package com.example.mp0901

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "notes.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE state = 0 ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query( "UPDATE notes SET state = 1, timestamp = :timestamp WHERE id = :id" )
    suspend fun deleteUndo(id: Int, timestamp: Long)

    @Query( "UPDATE notes SET state = 0 WHERE state = 1 and id = (SELECT id FROM notes WHERE state = 1 ORDER BY timestamp DESC LIMIT 1)" )
    suspend fun undo()

}

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val state: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

