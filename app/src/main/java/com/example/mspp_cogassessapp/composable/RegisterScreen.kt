package com.example.mspp_cogassessapp.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mspp_cogassessapp.R
import com.example.mspp_cogassessapp.firebase.Auth
import com.example.mspp_cogassessapp.firebase.UserDataDto
import com.example.mspp_cogassessapp.util.Screen
import firebase.ErrorManager

/**
 * This composable function displays the Register Screen.
 *
 * @param navController The NavController that this function will use to navigate between composables.
 */
@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val errorManager = ErrorManager(context)

    RegisterScreenContent(
        onLoginClick = { navController.navigate(Screen.Login.route) },
        onRegisterClick = { email, password, name, sex, age ->
            val auth = Auth(errorManager)
            val userData = UserDataDto(email = email, name = name, sex = sex, age = age)
            auth.registerUser(email, password, userData) { success ->
                if (success) {
                    navController.navigate(Screen.Home.route)
                } else {
                    // Error handling is done inside Auth class
                }
            }
        }
    )
}

/**
 * This composable function displays the content of the Register Screen.
 *
 * @param onLoginClick A function to call when the Login button is clicked.
 * @param onRegisterClick A function to call when the Register button is clicked. It takes the entered email, password, name, sex, and age as parameters.
 */
@Composable
fun RegisterScreenContent(
    onLoginClick: () -> Unit,
    onRegisterClick: (String, String, String, String, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var age by remember { mutableStateOf(16) }
    var sex by remember { mutableStateOf("Male") }
    val pink = colorResource(R.color.pink)
    val blue = colorResource(R.color.blue)
    val textColor = colorResource(R.color.text)
    val crust = colorResource(R.color.crust)
    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register", style = MaterialTheme.typography.h4, color = textColor)

        // Social Media Register Options
        Row {
            Icon(Icons.Default.AccountCircle, contentDescription = "Google Register", tint = textColor)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Default.AccountCircle, contentDescription = "Facebook Register", tint = textColor)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Default.AccountCircle, contentDescription = "Twitter Register", tint = textColor)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Name Field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name", color = textColor) },
            singleLine = true,
            isError = nameError,
            textStyle = LocalTextStyle.current.copy(color = textColor),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = pink,
                unfocusedBorderColor = pink,
                errorBorderColor = pink,
                errorLabelColor = pink,
                errorTrailingIconColor = pink
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = textColor) },
            singleLine = true,
            isError = emailError,
            textStyle = LocalTextStyle.current.copy(color = textColor),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = pink,
                unfocusedBorderColor = pink,
                errorBorderColor = pink,
                errorLabelColor = pink,
                errorTrailingIconColor = pink
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = textColor) },
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordError,
            textStyle = LocalTextStyle.current.copy(color = textColor),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = textColor
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = pink,
                unfocusedBorderColor = pink,
                errorBorderColor = pink,
                errorLabelColor = pink,
                errorTrailingIconColor = pink
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sex Selection
        Row {
            Icon(
                Icons.Default.Male, contentDescription = "Male", tint = if (sex == "Male") blue else Color.Gray,
                modifier = Modifier.clickable { sex = "Male" }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                Icons.Default.Female, contentDescription = "Female", tint = if (sex == "Female") pink else Color.Gray,
                modifier = Modifier.clickable { sex = "Female" }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                Icons.Default.Person, contentDescription = "Other", tint = if (sex == "Other") pink else Color.Gray,
                modifier = Modifier.clickable { sex = "Other" }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Age Selector
        Row {
            IconButton(onClick = { if (age > 16) age-- }) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease Age", tint = textColor)
            }
            Text("$age", color = textColor)
            IconButton(onClick = { age++ }) {
                Icon(Icons.Default.Add, contentDescription = "Increase Age", tint = textColor)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(
            onClick = {
                nameError = name.isEmpty()
                emailError = email.isEmpty()
                passwordError = password.isEmpty()
                if (!nameError && !emailError && !passwordError) {
                    onRegisterClick(email, password, name, sex, age)
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = pink)
        ) {
            Text("REGISTER", color = crust)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        TextButton(onClick = onLoginClick) {
            Text("Already have an account? Login", color = textColor)
        }
    }
}

/**
 * This composable function is used to preview the RegisterScreen in Android Studio.
 */
@Preview
@Composable
fun PreviewRegisterScreen() {
    RegisterScreenContent({}, { _, _, _, _, _ -> })
}
