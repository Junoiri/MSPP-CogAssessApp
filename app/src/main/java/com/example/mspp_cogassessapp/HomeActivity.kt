package com.example.mspp_cogassessapp

import HomeScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mspp_cogassessapp.composable.LoginScreen
import com.example.mspp_cogassessapp.util.Screen

/**
 * This class is responsible for handling the Home Activity.
 *
 * It sets the content view to the HomeScreen and LoginScreen and sets up the navigation between them.
 */
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Login.route) {
                composable(Screen.Login.route) { LoginScreen(navController) }
                composable(Screen.Home.route) { HomeScreen(navController) }
            }
        }
    }
}