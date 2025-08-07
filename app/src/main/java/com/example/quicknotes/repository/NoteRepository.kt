package com.example.quicknotes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quicknotes.model.Note
import com.example.quicknotes.room.NoteDao
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao){
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    val localNotes = noteDao.getRoomNotes(auth.currentUser?.uid ?: "")

    private fun userNotesRef(): CollectionReference {
        val userId = auth.currentUser?.uid ?: throw Exception("Not logged in")
        return db.collection("users").document(userId).collection("notes")
    }

    fun getNotes(): LiveData<List<Note>> {
        val liveData = MutableLiveData<List<Note>>()
        userNotesRef()
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val notes = snapshot.toObjects(Note::class.java)
                    liveData.value = notes
                }
            }
        return liveData
    }

    fun getNoteById(noteId: String, onResult: (Note?) -> Unit) {
        userNotesRef().document(noteId).get().addOnSuccessListener {
            onResult(it.toObject(Note::class.java))
        }
    }

    fun saveNote(note: Note) {
        val ref = if (note.id.isEmpty()) {
            userNotesRef().document() // new note
        } else {
            userNotesRef().document(note.id) // update note
        }

        val finalNote = note.copy(id = ref.id)
        ref.set(finalNote)
    }

    fun deleteNote(noteId: String) {
        userNotesRef().document(noteId).delete()
    }

    suspend fun saveNoteInRoom(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun deleteNoteInRoom(note: Note) {
        noteDao.delete(note)
    }

    suspend fun getNoteByIdFromRoom(noteId: String) : Note?{
        return noteDao.getNoteById(noteId)
    }

}