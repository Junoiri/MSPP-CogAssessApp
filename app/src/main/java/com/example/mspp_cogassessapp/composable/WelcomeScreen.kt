package com.example.mspp_cogassessapp.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mspp_cogassessapp.R
import androidx.compose.ui.text.font.Font
import androidx.compose.foundation.clickable
import com.example.mspp_cogassessapp.util.Screen

@Composable
fun WelcomeScreen(navController: NavController) {
    val backgroundImage = painterResource(id = R.drawable.bg_login_cropped) // Ensure you have a background image
    val fontFamily = FontFamily(Font(R.font.press_start))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Use a plain color or another image as the actual background
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.4f)
                .clip(RectangleShape)
        )
        WelcomeScreenContent(
            onLoginClick = { navController.navigate(Screen.Login.route) },
            onRegisterClick = { navController.navigate(Screen.Register.route) },
            onContinueAsGuestClick = { navController.navigate(Screen.Home.route) },
            fontFamily = fontFamily
        )
    }
}

@Composable
fun WelcomeScreenContent(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onContinueAsGuestClick: () -> Unit,
    fontFamily: FontFamily
) {
    val loginImage = painterResource(id = R.drawable.login_b)
    val registerImage = painterResource(id = R.drawable.register_b)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(50.dp))  // Adjust the spacing as needed for layout

        Image(
            painter = loginImage,
            contentDescription = "Login Button",
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .clickable { onLoginClick() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = registerImage,
            contentDescription = "Register Button",
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .clickable { onRegisterClick() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onContinueAsGuestClick,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
        ) {
            Text("Continue as guest", fontFamily = fontFamily, color = Color.White)
        }
    }
}
@Preview
@Composable
fun PreviewWelcomeScreen() {
    val fontFamily = FontFamily(Font(R.font.press_start))
    WelcomeScreenContent({}, {}, {}, fontFamily)
}