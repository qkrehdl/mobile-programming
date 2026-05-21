package com.example.mp0901

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Stack

class NoteRepository(private val noteDao: NoteDao) {
    val notesFlow: Flow<List<Note>> = noteDao.getAllNotes()
    suspend fun insert(note: Note) = noteDao.insert(note)
    suspend fun delete(note: Note) = noteDao.delete(note)
}

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    // 여러 단계 Undo 저장
    private val deletedNotesStack = Stack<Note>()

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
                Log.e("NoteViewModel","추가 실패",e)
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                // 삭제 기록 저장
                deletedNotesStack.push(note)

                repository.delete(note)

            } catch (e: Exception) {
                Log.e("NoteViewModel","삭제 실패",e)
            }
        }
    }

    fun restoreNote() {
        viewModelScope.launch {
            try {
                if (deletedNotesStack.isNotEmpty()) {

                    val noteToRestore =
                        deletedNotesStack.pop()

                    repository.insert(noteToRestore)
                }

            } catch (e: Exception) {
                Log.e("NoteViewModel","복원 실패",e)
            }
        }
    }
}