package com.example.mspp_cogassessapp.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mspp_cogassessapp.R

@Composable
fun SimonInfoScreen(navController: NavController) {
    // Load the font family
    val pressStartFontFamily = remember { FontFamily(Font(R.font.press_start)) }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.crust),
                title = { Text("Test Info", color = colorResource(id = R.color.text)) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = colorResource(id = R.color.sapphire))
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Other", tint = colorResource(id = R.color.lavender))
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(id = R.color.mantle))
                .padding(16.dp)  // Added padding inside the column
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_card_blue),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 16.dp)  // Added padding below the image
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Simon",
                    fontSize = 32.sp,
                    fontFamily = pressStartFontFamily,
                    color = colorResource(id = R.color.blue),  // Use the blue color from colors.xml
                    modifier = Modifier.padding(start = 16.dp)  // Increased padding around the game name text
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Visual Memory Test",
                    fontSize = 18.sp,
                    fontFamily = pressStartFontFamily,
                    color = colorResource(id = R.color.text),
                    modifier = Modifier.padding(start = 16.dp, top = 6.dp)  // Padding on the left side
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Info",
                    fontSize = 24.sp,
                    fontFamily = pressStartFontFamily,
                    color = colorResource(id = R.color.blue),
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)  // Padding for the info header
                )
                Text(
                    "The Simon Test combines elements from well-known cognitive assessments like the Symbol Search Test (WAIS), the Wisconsin Card Sorting Test (WCST), and Raven's Progressive Matrices.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.text),
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, top = 8.dp)  // Increased padding around the content text
                )
                Text(
                    text = "Instructions",
                    fontSize = 24.sp,
                    fontFamily = pressStartFontFamily,
                    color = colorResource(id = R.color.blue),
                    modifier = Modifier
                        .padding(end = 16.dp, top = 16.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)  // Align to the right
                )
                Text(
                    "In this test, you'll see the word 'left' or 'right' appear in different positions on the screen. Press the left button if the word is 'left' and the right button if the word is 'right', regardless of its position. Your score will be based on the number of correct answers, which will determine your accuracy percentage.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.text),
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, top = 8.dp)  // Increased padding around the content text
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.button_blue),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSimonInfoScreen() {
    val navController = rememberNavController()
    SimonInfoScreen(navController)
}
