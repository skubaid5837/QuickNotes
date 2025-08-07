package com.example.quicknotes.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quicknotes.R
import com.example.quicknotes.model.Note
import com.example.quicknotes.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    navController: NavHostController,
    noteId: String?,
    viewModel: NoteViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(noteId) {
        if (noteId != null && noteId != "null") {
            isLoading = true // Show loading indicator
            val existingNote = viewModel.getNoteById(noteId){
                note ->
                title = note?.title ?: ""
                content = note?.content ?: ""
            }
            isLoading = false // Hide loading indicator
        } else {
            // If noteId is null, it's a new note, so clear the fields
            title = ""
            content = ""
            isLoading = false // No loading needed for new note
        }
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                title = {
                    Text("Notes")
                },
                actions = {
                    IconButton(onClick = { /* Preview Logic */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Preview", tint = Color.White)
                    }
                    IconButton(onClick = {
                        viewModel.saveNote(title, content, noteId.toString())
                        navController.popBackStack()
                    }) {
                        val isSaveEnabled = title.isNotBlank() || content.isNotBlank()
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Save",
                            tint = if (isSaveEnabled) Color.White else colorResource(R.color.disableIconColor)
                        )
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
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // Title input
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title", color = Color.Gray) },
                textStyle = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.LightGray,
                    unfocusedLabelColor = Color.LightGray,
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black,
                    disabledIndicatorColor = Color.Black,
                    errorIndicatorColor = Color.Black,
                    cursorColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            // Content input
            TextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("Type something...", color = Color.Gray) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.LightGray,
                    unfocusedLabelColor = Color.LightGray,
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black,
                    disabledIndicatorColor = Color.Black,
                    errorIndicatorColor = Color.Black,
                    cursorColor = Color.White
                ),
                maxLines = Int.MAX_VALUE
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen() {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                title = { },
                actions = {
                    IconButton(onClick = { /* Preview Logic */ }) {
                        Icon(Icons.Default.Favorite, contentDescription = "Preview", tint = Color.White)
                    }
                    IconButton(onClick = {
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Save", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // Title input
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title", color = Color.Gray) },
                textStyle = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.LightGray,
                    unfocusedLabelColor = Color.LightGray,
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black,
                    disabledIndicatorColor = Color.Black,
                    errorIndicatorColor = Color.Black,
                    cursorColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Content input
            TextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("Type something...", color = Color.Gray) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.LightGray,
                    unfocusedLabelColor = Color.LightGray,
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black,
                    disabledIndicatorColor = Color.Black,
                    errorIndicatorColor = Color.Black,
                    cursorColor = Color.White
                ),
                maxLines = Int.MAX_VALUE
            )

            Spacer(modifier = Modifier.height(12.dp))

//            // Rich text formatting row (icons only for UI)
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                val icons = listOf(
//                    R.drawable.ic_bold,
//                    R.drawable.ic_italic,
//                    R.drawable.ic_underline,
//                    R.drawable.ic_link,
//                    R.drawable.ic_format_align_left,
//                    R.drawable.ic_format_list_bulleted,
//                    R.drawable.ic_quote,
//                    R.drawable.ic_code,
//                    R.drawable.ic_strikethrough,
//                    R.drawable.ic_clear_format
//                )
//
//                icons.forEach { iconRes ->
//                    IconButton(onClick = {
//                        // TODO: Apply formatting
//                    }) {
//                        Icon(
//                            painter = painterResource(id = iconRes),
//                            contentDescription = "Format",
//                            tint = Color.White,
//                            modifier = Modifier.size(24.dp)
//                        )
//                    }
//                }
//            }
        }
    }
}

