package com.example.mspp_cogassessapp.composable

import android.os.SystemClock
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mspp_cogassessapp.R
import com.google.firebase.auth.FirebaseAuth

/**
 * This composable function displays the Simon game screen.
 *
 * @param navController The NavController that this function will use to navigate between composables.
 */
@Composable
fun SimonPlayScreen(navController: NavController) {
    val pressStartFontFamily = FontFamily(Font(R.font.press_start))
    var currentWord by remember { mutableStateOf("left") }
    var currentPosition by remember { mutableStateOf("center") }
    var trialsLeft by remember { mutableStateOf(10) }
    var score by remember { mutableStateOf(0) }
    var totalTime by remember { mutableStateOf(0L) }
    val responseTimes = remember { mutableStateListOf<Long>() }
    var isPaused by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf(0L) }

    val context = LocalContext.current
    val words = listOf("left", "right")
    val positions = listOf("left", "center", "right")

    /**
     * This function updates the test by generating a new word and position.
     */
    fun updateTest() {
        currentWord = words.random()
        currentPosition = positions.random()
        startTime = SystemClock.elapsedRealtime()
    }

    /**
     * This function checks the user's response and updates the score and trials left accordingly.
     *
     * @param isLeft A boolean indicating whether the user's response was "left".
     */
    fun checkResponse(isLeft: Boolean) {
        val endTime = SystemClock.elapsedRealtime()
        val responseTime = endTime - startTime
        totalTime += responseTime
        responseTimes.add(responseTime)

        if ((currentWord == "left" && isLeft) || (currentWord == "right" && !isLeft)) {
            score++
        }

        trialsLeft--

        if (trialsLeft > 0) {
            updateTest()
        } else {
            endGame(
                context = context,
                email = FirebaseAuth.getInstance().currentUser?.email ?: "",
                title = "Simon",
                averageResponseTime = if (responseTimes.isNotEmpty()) responseTimes.average() else 0.0,
                totalTime = totalTime,
                accuracy = (score.toDouble() / 10) * 100
            ) { success ->
                if (success) {
                    navController.popBackStack()
                } else {
                    // Handle error
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        updateTest()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.crust),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Simon",
                            fontFamily = pressStartFontFamily,
                            color = colorResource(id = R.color.blue),
                            modifier = Modifier.padding(end = 60.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Filled.Pause, contentDescription = "Pause", tint = colorResource(id = R.color.sapphire))
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Gray, RoundedCornerShape(10.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = currentWord,
                        fontFamily = pressStartFontFamily,
                        color = Color.White,
                        fontSize = 32.sp,
                        modifier = Modifier.align(
                            when (currentPosition) {
                                "left" -> Alignment.CenterStart
                                "right" -> Alignment.CenterEnd
                                else -> Alignment.Center
                            }
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { checkResponse(isLeft = true) }) {
                        Text("Left", fontFamily = pressStartFontFamily)
                    }
                    Button(onClick = { checkResponse(isLeft = false) }) {
                        Text("Right", fontFamily = pressStartFontFamily)
                    }
                }
            }
            if (showDialog) {
                PauseDialog(
                    onResume = { isPaused = false; showDialog = false },
                    onQuit = { navController.popBackStack() }
                )
            }
        }
    }
}


/**
 * This composable function is used to preview the SimonPlayScreen in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun PreviewSimonPlayScreen() {
    val navController = rememberNavController()
    SimonPlayScreen(navController)
}
