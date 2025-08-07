package com.example.quicknotes.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.quicknotes.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(1500)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            navController.navigate(Screen.NotesList.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.SignIn.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Quick Notes", style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun TestSplashScreen() {
    LaunchedEffect(Unit) {
        delay(1500)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
//            navController.navigate(Screen.NotesList.route) {
//                popUpTo(Screen.Splash.route) { inclusive = true }
//            }
        } else {
//            navController.navigate(Screen.SignIn.route) {
//                popUpTo(Screen.Splash.route) { inclusive = true }
//            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Quick Notes", style = MaterialTheme.typography.headlineMedium)
    }
}