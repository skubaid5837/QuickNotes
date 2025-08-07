package com.example.quicknotes.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quicknotes.model.Note
import com.example.quicknotes.utils.TimeFormatterUtil
import com.google.firebase.annotations.concurrent.Background

@Composable
fun NoteCard(note: Note, onClick: () -> Unit, modifier: Modifier = Modifier, backgroundColor: Color) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val time = TimeFormatterUtil.formatTimestamp(note.timestamp)
            Text(text = note.title, color = Color.Black, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.content,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Last edited: $time",
                color = Color.DarkGray,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun Test(){
//    // Create some fake notes to display in the preview.
//    val fakeNotes = listOf(
//        Note("1", "First Note", "This is the content of the first note.", System.currentTimeMillis()),
//        Note("2", "Second Note", "The content of the second note is a bit longer to test wrapping.", System.currentTimeMillis()),
//        Note("3", "Third Note", "Another short note.", System.currentTimeMillis())
//    )
//    NoteCard(fakeNotes[0],  onClick = {})
//}
