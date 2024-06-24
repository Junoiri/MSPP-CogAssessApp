package com.example.mspp_cogassessapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mspp_cogassessapp.composable.LoginScreen
import com.example.mspp_cogassessapp.composable.RegisterScreen
import com.example.mspp_cogassessapp.composable.WelcomeScreen
import com.example.mspp_cogassessapp.util.Screen
class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Welcome.route) {
                composable(Screen.Welcome.route) { WelcomeScreen(navController) }
//                composable(Screen.Login.route) { LoginScreen(navController) }
//                composable(Screen.Register.route) { RegisterScreen(navController) }
//                composable(Screen.Home.route) { HomeScreen(navController) }
            }
        }
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}