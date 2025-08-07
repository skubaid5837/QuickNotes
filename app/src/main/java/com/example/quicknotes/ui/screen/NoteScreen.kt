package com.example.quicknotes.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.quicknotes.model.Note
import com.example.quicknotes.ui.navigation.Screen
import com.example.quicknotes.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    navController: NavHostController,
    noteId: String?,
    viewModel: NoteViewModel = hiltViewModel()
) {
    var note by remember { mutableStateOf<Note?>(null) }

    LaunchedEffect(noteId) {
        if (!noteId.isNullOrBlank()) {
            viewModel.getNoteById(noteId) {
                note = it
            }
        }
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {
                    Text("Edit")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Navigate to edit screen (reuse NoteDetail route)
                        navController.navigate(Screen.AddNote.passNoteId(noteId)){
                            popUpTo(Screen.NoteDetail.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            if (note != null) {
                Text(
                    text = note!!.title,
                    style = MaterialTheme.typography.headlineMedium.copy(color = Color.White)
                )
                Spacer(modifier = Modifier.height(16.dp))
                note!!.content.split("\n\n").forEach { paragraph ->
                    if (paragraph.contains("*") && paragraph.startsWith("*")) {
                        Text(
                            text = paragraph.removeSurrounding("*"),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.LightGray,
                                fontStyle = FontStyle.Italic
                            ),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    } else {
                        Text(
                            text = paragraph,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.LightGray),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Loading...", color = Color.White)
                }
            }
        }
    }
}



@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen() {

    val fakeNotes = listOf(
        Note("1", "First Note", "This is the content of the first note.", System.currentTimeMillis()),
        Note("2", "Second Note", "The content of the second note is a bit longer to test wrapping.", System.currentTimeMillis()),
        Note("3", "Third Note", "Another short note.", System.currentTimeMillis())
    )

    val data = fakeNotes[0]

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.headlineSmall.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.height(16.dp))
            data.content.split("\n\n").forEach { paragraph ->
                if (paragraph.contains("*") && paragraph.startsWith("*")) {
                    Text(
                        text = paragraph.removeSurrounding("*"),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.LightGray,
                            fontStyle = FontStyle.Italic
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                } else {
                    Text(
                        text = paragraph,
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.LightGray),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}
