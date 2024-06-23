package com.example.mspp_cogassessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mspp_cogassessapp.composable.ForgotPasswordScreen
import com.example.mspp_cogassessapp.composable.HomeScreen
import com.example.mspp_cogassessapp.composable.LoginScreen
import com.example.mspp_cogassessapp.composable.RegisterScreen
import com.example.mspp_cogassessapp.composable.WelcomeScreen
import com.example.mspp_cogassessapp.util.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController) }
    }
}