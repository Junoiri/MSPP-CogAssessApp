package com.example.mspp_cogassessapp.game.activities
import NamikInfoScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mspp_cogassessapp.R
import com.example.mspp_cogassessapp.composable.RegisterScreen
import com.example.mspp_cogassessapp.util.Screen

/**
 * This class is responsible for handling the NamikInfo Activity.
 *
 * It sets the content view to the NamikInfoScreen and sets up the navigation for it.
 */
class NamikInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Register.route) {
                composable(Screen.NamikInfo.route) { NamikInfoScreen(navController) }
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