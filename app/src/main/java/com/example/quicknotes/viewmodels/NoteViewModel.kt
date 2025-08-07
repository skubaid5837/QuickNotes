package com.example.quicknotes.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicknotes.model.Note
import com.example.quicknotes.repository.NoteRepository
import com.example.quicknotes.utils.Utils.Companion.isOnline
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    val notes: LiveData<List<Note>> = if (isOnline(context)) {
        repository.getNotes()
    } else {
        repository.localNotes
    }
    private val _deletedNote = mutableStateOf<Note?>(null)
    val deletedNotes: State<Note?> = _deletedNote

    fun getNoteById(noteId: String, onResult: (Note?) -> Unit) {
        if (isOnline(context)) {
            repository.getNoteById(noteId, onResult)
        } else {
            viewModelScope.launch {
                val note = repository.getNoteByIdFromRoom(noteId)
                onResult(note)
            }
        }
    }

    fun saveNote(title: String, content: String, id: String = "") {
        val uid = UUID.randomUUID().toString()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val note = Note(id = uid, title = title, content = content, userId = userId.toString())
        repository.saveNote(note)

        // Also save in Room
        viewModelScope.launch {
            repository.saveNoteInRoom(note)
        }
    }

    fun deleteNote(note: Note) {
        _deletedNote.value = note
        repository.deleteNote(note.id)
        viewModelScope.launch {
            repository.deleteNoteInRoom(note)
        }
    }

    fun restoreDeletedNote() {
        _deletedNote.value?.let {
            repository.saveNote(it)
            // Also save in Room
            viewModelScope.launch {
                repository.saveNoteInRoom(it)
            }
            _deletedNote.value = null
        }
    }

    fun getNoteFromRoom(note: Note) {
        viewModelScope.launch {
            repository.getNoteByIdFromRoom(note.id)
        }
    }
}