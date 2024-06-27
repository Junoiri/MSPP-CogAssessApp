package com.example.mspp_cogassessapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.rememberNavController
import com.example.mspp_cogassessapp.composable.ForgotPasswordScreen
/**
 * This class is responsible for handling the Forgot Password Activity.
 *
 * It sets the content view to the ForgotPasswordScreen and enables edge-to-edge drawing.
 */
class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForgotPasswordNavigator()
        }
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

/**
 * This composable function sets up the navigation for the Forgot Password Screen.
 */
@Composable
fun ForgotPasswordNavigator() {
    val navController = rememberNavController()
    ForgotPasswordScreen(navController)
}