package com.example.quicknotes.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quicknotes.R

@Composable
fun SignInScreen(
    navController: NavController,
    onSignInClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0076FF)) // Bright blue background
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Image
            Image(
                painter = painterResource(id = R.drawable.no_notes), // your image asset
                contentDescription = "Illustration",
                modifier = Modifier
                    .size(180.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            //App Title
            Text(
                text = "Entrepreneurship\nDictionary",
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(48.dp))

            //Sign in with Google Button
            GoogleSignInButton(onClick = onSignInClick)
        }
    }
}

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google), // use actual logo
                contentDescription = "Google Logo",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign in with Google", color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestSignInScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0076FF)) // Bright blue background
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Image
            Image(
                painter = painterResource(id = R.drawable.no_notes), // your image asset
                contentDescription = "Illustration",
                modifier = Modifier
                    .size(180.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // App Title
            Text(
                text = "Entrepreneurship\nDictionary",
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Sign in with Google Button
            GoogleSignInButton(onClick = {})
        }
    }
}
