package com.example.mspp_cogassessapp.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mspp_cogassessapp.R
/**
 * This composable function displays the Forgot Password Screen.
 *
 * @param navController The NavController that this function will use to navigate between composables.
 */
@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    val blue = colorResource(R.color.blue)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back Button
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Text(text = "ForgotPassword", style = MaterialTheme.typography.h4)

        Text("Please enter your email to reset the Password", style = MaterialTheme.typography.body1)

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Your Email") },
            placeholder = { Text("Enter your email") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Reset Password Button
        Button(onClick = { /* Handle Reset Password click */ }, colors = ButtonDefaults.buttonColors(backgroundColor = blue)) {
            Text("Reset Password")
        }
    }
}