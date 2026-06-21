package com.example.mp0901

import android.app.Application
import android.util.Log.e
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class NoteRepository(private val noteDao: NoteDao) {
    val notesFlow: Flow<List<Note>> = noteDao.getAllNotes()
    suspend fun insert(note: Note) = noteDao.insert(note)
    suspend fun delete(note: Note) = noteDao.delete(note)
}

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    // TODO: Undo기능 추가
    // 최근 삭제한 메모 저장
    private val deletedNotes = ArrayDeque<Note>()

    init {
        val db = AppDatabase.getDatabase(application)
        repository = NoteRepository(db.noteDao())

        viewModelScope.launch {
            repository.notesFlow.collect { noteList ->
                _notes.value = noteList
            }
        }
    }

    fun addNote(text: String) {
        viewModelScope.launch {
            try {
                repository.insert(Note(text = text))
            } catch (e: Exception) {
                // 에러 로그 출력 등
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                // TODO: Undo기능 추가
                // 삭제 전에 저장
                deletedNotes.addLast(note)
                repository.delete(note)
            } catch (e: Exception) {
                // 에러 로그 출력 등
            }
        }
    }

    // TODO: Undo기능 추가
    fun restoreNote() {
        viewModelScope.launch {
            try {
                if (deletedNotes.isNotEmpty()){
                    val note = deletedNotes.removeLast()
                    repository.insert(note)
                }
            } catch (e: Exception) {
                // 에러 로그 등
            }
        }
    }
}
