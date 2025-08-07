package com.example.quicknotes.ui.navigation

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quicknotes.R
import com.example.quicknotes.ui.screen.AddNoteScreen
import com.example.quicknotes.ui.screen.HomeScreen
import com.example.quicknotes.ui.screen.NoteScreen
import com.example.quicknotes.ui.screen.SignInScreen
import com.example.quicknotes.ui.screen.SplashScreen
import com.example.quicknotes.viewmodels.SignInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screen.SignIn.route) {
            val context = LocalContext.current
            val viewModel: SignInViewModel = hiltViewModel()
            val isSignedIn by viewModel.isSignedIn.observeAsState()

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val idToken = account.idToken
                    viewModel.handleSignInResult(idToken)
                } catch (e: ApiException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
                }
            }

            LaunchedEffect(isSignedIn) {
                if (isSignedIn == true) {
                    navController.navigate(Screen.NotesList.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                }
            }
            SignInScreen(
                navController = navController,
                onSignInClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(context.getString(R.string.default_web_client_id)) // from google-services.json
                        .requestEmail()
                        .build()

                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                }
            )
        }
        composable(Screen.NotesList.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(navArgument("noteId") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) {
            val noteId = it.arguments?.getString("noteId")
            NoteScreen(navController, noteId)
        }
        composable(
            Screen.AddNote.route,
            arguments = listOf(navArgument("noteId") {
                type = NavType.StringType
                defaultValue = ""
                nullable= true
            })
        ){ backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            AddNoteScreen(navController, noteId)
        }
    }
}