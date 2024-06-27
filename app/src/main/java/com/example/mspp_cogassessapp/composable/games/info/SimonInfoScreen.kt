package com.example.mspp_cogassessapp.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.mspp_cogassessapp.util.Screen

@Composable
fun VismerInfoScreen(navController: NavController) {
    // Load the font family
    val pressStartFontFamily = remember { FontFamily(Font(R.font.press_start)) }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.crust),
                title = { Text("Test Info", color = colorResource(id = R.color.text)) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = colorResource(id = R.color.green))
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
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.bg_card_green),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Vismer",
                        fontSize = 32.sp,
                        fontFamily = pressStartFontFamily,
                        color = colorResource(id = R.color.teal),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "INH-REST: Equivalencies Test",
                        fontSize = 18.sp,
                        fontFamily = pressStartFontFamily,
                        color = colorResource(id = R.color.text),
                        modifier = Modifier.padding(start = 16.dp, top = 6.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Info",
                        fontSize = 24.sp,
                        fontFamily = pressStartFontFamily,
                        color = colorResource(id = R.color.teal),
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                    Text(
                        "The Stroop Test INH-REST is a neurocognitive assessment based on the classic Stroop test. It measures cognitive abilities related to attention and focusing by requiring the test-taker to respond to colour-word stimuli under go/no-go conditions.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.text),
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, top = 8.dp)
                    )
                    Text(
                        text = "Instructions",
                        fontSize = 24.sp,
                        fontFamily = pressStartFontFamily,
                        color = colorResource(id = R.color.teal),
                        modifier = Modifier
                            .padding(end = 16.dp, top = 16.dp)
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                    )
                    Text(
                        "A single word, consisting of a colour name, will be displayed at the center of the screen.\n" +
                                "You must press the button only if the colour name is printed in the matching colour.\n" +
                                "Refrain from pressing the spacebar if the colour of the letters does not match the printed colour name."
                        ,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.text),
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, top = 8.dp)
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(100.dp)) // Add some space for the button
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.button_green),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(10.dp)
                        .clickable {
                            // Navigate to the next screen here
                            navController.navigate(Screen.VismerPlay.route)
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVismerInfoScreen() {
    val navController = rememberNavController()
    VismerInfoScreen(navController)
}
