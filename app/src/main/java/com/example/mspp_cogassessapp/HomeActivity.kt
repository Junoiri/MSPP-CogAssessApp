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