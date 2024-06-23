package com.example.mspp_cogassessapp.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mspp_cogassessapp.R
import com.example.mspp_cogassessapp.util.Screen

@Composable
fun RegisterScreen(navController: NavController) {
    RegisterScreenContent(
        onLoginClick = { navController.navigate(Screen.Login.route) },
        onRegisterClick = { navController.navigate(Screen.Home.route) }
    )
}

@Composable
fun RegisterScreenContent(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var age by remember { mutableStateOf(16) }
    val pink = colorResource(R.color.pink)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register", style = MaterialTheme.typography.h4)

        // Social Media Register Options
        Row {
            Icon(Icons.Default.AccountCircle, contentDescription = "Google Register")
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Default.AccountCircle, contentDescription = "Facebook Register")
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Default.AccountCircle, contentDescription = "Twitter Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Name Field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sex Selection
        Row {
            Icon(Icons.Default.Male, contentDescription = "Male")
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Default.Female, contentDescription = "Female")
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Default.Person, contentDescription = "Other")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Age Selector
        Row {
            IconButton(onClick = { if (age > 16) age-- }) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease Age")
            }
            Text("$age")
            IconButton(onClick = { age++ }) {
                Icon(Icons.Default.Add, contentDescription = "Increase Age")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(onClick = onRegisterClick, colors = ButtonDefaults.buttonColors(backgroundColor = pink)) {
            Text("REGISTER")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        TextButton(onClick = onLoginClick) {
            Text("Already have an account? Login")
        }
    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    RegisterScreenContent({}, {})
}