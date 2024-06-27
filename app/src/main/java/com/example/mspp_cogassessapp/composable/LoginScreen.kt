package com.example.mspp_cogassessapp.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mspp_cogassessapp.R
import com.example.mspp_cogassessapp.firebase.Auth
import com.example.mspp_cogassessapp.util.Screen
import firebase.ErrorManager

/**
 * This composable function displays the Login Screen.
 *
 * @param navController The NavController that this function will use to navigate between composables.
 */
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val errorManager = ErrorManager(context)

    LoginScreenContent(
        onRegisterClick = { navController.navigate(Screen.Register.route) },
        onLoginClick = { email, password ->
            val auth = Auth(errorManager)
            auth.loginUser(email, password) { success ->
                if (success) {
                    navController.navigate(Screen.Home.route)
                } else {
                    // Error handling is done inside Auth class
                }
            }
        },
        onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) }
    )
}
/**
 * This composable function displays the content of the Login Screen.
 *
 * @param onRegisterClick A function to call when the Register button is clicked.
 * @param onLoginClick A function to call when the Login button is clicked. It takes the entered email and password as parameters.
 * @param onForgotPasswordClick A function to call when the Forgot Password text is clicked.
 */
@Composable
fun LoginScreenContent(
    onRegisterClick: () -> Unit,
    onLoginClick: (String, String) -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val pink = colorResource(R.color.pink)
    val textColor = colorResource(R.color.text)
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.h4, color = textColor)

        // Social Media Login Options
        Row {
            Icon(Icons.Default.AccountCircle, contentDescription = "Google Login", tint = textColor)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Default.AccountCircle, contentDescription = "Facebook Login", tint = textColor)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Default.AccountCircle, contentDescription = "Twitter Login", tint = textColor)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = textColor) },
            isError = emailError,
            singleLine = true,
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
            isError = passwordError,
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
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

        // Forgot Password
        TextButton(onClick = onForgotPasswordClick) {
            Text("Forgot Password?", color = textColor)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        Button(
            onClick = {
                emailError = email.isEmpty()
                passwordError = password.isEmpty()
                if (!emailError && !passwordError) {
                    onLoginClick(email, password)
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = pink)
        ) {
            Text("LOGIN", color = colorResource(id = R.color.crust))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        TextButton(onClick = onRegisterClick) {
            Text("Don't have an account? Register", color = textColor)
        }
    }
}
/**
 * This composable function is used to preview the LoginScreen in Android Studio.
 */
@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreenContent({}, { _, _ -> }, {})
}
