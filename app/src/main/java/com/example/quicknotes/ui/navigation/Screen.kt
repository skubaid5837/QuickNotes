package com.example.quicknotes.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object SignIn : Screen("sign_in")
    object NotesList : Screen("notes_list")
    object AddNote : Screen("add_edit_note?noteId={noteId}") {
        fun passNoteId(noteId: String?): String {
            return if (noteId.isNullOrEmpty()) {
                "add_edit_note?noteId="
            } else {
                "add_edit_note?noteId=$noteId"
            }
        }
    }
    object NoteDetail : Screen("note_detail?noteId={noteId}") {
        fun passNoteId(noteId: String?) = "note_detail?noteId=$noteId"
    }
}