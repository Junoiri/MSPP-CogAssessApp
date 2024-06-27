package com.example.mspp_cogassessapp

import HomeScreen
import NamikInfoScreen
import NamikPlayScreen
import VismerPlayScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mspp_cogassessapp.composable.ForgotPasswordScreen
import com.example.mspp_cogassessapp.composable.LoginScreen
import com.example.mspp_cogassessapp.composable.RegisterScreen
import com.example.mspp_cogassessapp.composable.StrooperInfoScreen
import com.example.mspp_cogassessapp.composable.StrooperPlayScreen
import com.example.mspp_cogassessapp.composable.VismerInfoScreen
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
        composable(Screen.NamikInfo.route) { NamikInfoScreen(navController) }
        composable(Screen.NamikPlay.route) { NamikPlayScreen(navController) }
        composable(Screen.StrooperInfo.route) { StrooperInfoScreen(navController) }
        composable(Screen.StrooperPlay.route) { StrooperPlayScreen(navController) }
        composable(Screen.VismerInfo.route) { VismerInfoScreen(navController) }
        composable(Screen.VismerPlay.route) { VismerPlayScreen(navController) }



    }
}
