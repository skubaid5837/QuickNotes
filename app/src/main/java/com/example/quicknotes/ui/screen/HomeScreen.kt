package com.example.quicknotes.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.quicknotes.R
import com.example.quicknotes.model.Note
import com.example.quicknotes.ui.navigation.Screen
import com.example.quicknotes.viewmodels.NoteViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: NoteViewModel = hiltViewModel()
) {

    val notes by viewModel.notes.observeAsState(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val noteColors = listOf(
        colorResource(id = R.color.c1),
        colorResource(id = R.color.c2),
        colorResource(id = R.color.c3),
        colorResource(id = R.color.c4),
        colorResource(id = R.color.c5),
        colorResource(id = R.color.c6),
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddNote.route) },
                containerColor = Color.Red,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 16.dp,  // A visible shadow when idle
                    pressedElevation = 24.dp   // Lifts the button up when pressed
                )
            ){
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Notes") },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Info")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.Black
    ) { padding ->
        if (notes.isEmpty()) {
            EmptyNoteView(modifier = Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                itemsIndexed(notes, key = { _, note -> note.id }) { index, note ->
                    // Get the haptic feedback controller
                    val haptic = LocalHapticFeedback.current
                    val backgroundColor = noteColors[index % noteColors.size]
                    val dismissState = rememberSwipeToDismissBoxState(
                        positionalThreshold = { distance -> distance * 0.5f },
                        confirmValueChange = { value ->
                            if (value == SwipeToDismissBoxValue.EndToStart ||
                                value == SwipeToDismissBoxValue.StartToEnd
                            ) {
                                viewModel.deleteNote(note)
                                coroutineScope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Note Deleted",
                                        actionLabel = "Undo",
                                        duration = SnackbarDuration.Short
                                    )

                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.restoreDeletedNote()
                                    }
                                }
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                true
                            } else {
                                false
                            }
                        }
                    )

                    val shape = RoundedCornerShape(16.dp)

                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            // Red delete background
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 8.dp, vertical = 8.dp)
                                    .clip(shape)
                                    .background(Color.Red)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White,
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White,
                                    )
                                }
                            }
                        },
                        content = {

                            NoteCard(note = note,
                                onClick = {
                                    navController.navigate(Screen.NoteDetail.passNoteId(note.id)) },
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 8.dp)
                                    .clip(shape),
                                backgroundColor = backgroundColor
                                )

                        }
                    )
                }
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(
                "Log Out",
                color = Color.White) },
            text = { Text("Are you sure you want to log out?", color = Color.White) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    // perform logout here
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(0) // clear back stack
                    }
                }) {
                    Text("Yes", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel", color = Color.White)
                }
            },
            containerColor = Color.Black
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestHomeScreen() {

    val fakeNotes = listOf(
        Note("1", "First Note", "This is the content of the first note.", System.currentTimeMillis()),
        Note("2", "Second Note", "The content of the second note is a bit longer to test wrapping.", System.currentTimeMillis()),
        Note("3", "Third Note", "Another short note.", System.currentTimeMillis())
    )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                },
                containerColor = Color.Blue,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 16.dp,  // A visible shadow when idle
                    pressedElevation = 24.dp   // Lifts the button up when pressed
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Notes") },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Info, contentDescription = "Info")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.Black
    ) { padding ->
        if (fakeNotes.isEmpty()) {
            EmptyNoteView(modifier = Modifier.padding(padding))
        } else {
            val noteColors = listOf(
                colorResource(id = R.color.c1),
                colorResource(id = R.color.c2),
                colorResource(id = R.color.c3),
                colorResource(id = R.color.c4),
                colorResource(id = R.color.c5),
                colorResource(id = R.color.c6)
            )

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                itemsIndexed(fakeNotes) { index, note ->
                    val backgroundColor = noteColors[index % noteColors.size]
                    NoteCard(note = note, onClick = {}, backgroundColor = backgroundColor)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Test(){
    TestHomeScreen()
}