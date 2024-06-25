package com.example.mspp_cogassessapp.composable

import android.graphics.Color as AndroidColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
import com.example.mspp_cogassessapp.firebase.Game
import com.example.mspp_cogassessapp.firebase.GameDto
import com.google.firebase.auth.FirebaseAuth
import firebase.ErrorManager
import kotlinx.coroutines.delay
import java.sql.Time
import java.util.Date
import kotlin.random.Random

@Composable
fun StrooperPlayScreen(navController: NavController) {
    val pressStartFontFamily = FontFamily(Font(R.font.press_start))
    var showAnswer by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(18) }
    var score by remember { mutableStateOf(0) }
    var totalTime by remember { mutableStateOf(0L) }
    var responseTimes = remember { mutableStateListOf<Long>() }
    var currentWord by remember { mutableStateOf("") }
    var currentColor by remember { mutableStateOf(Color.White) }
    var isMatching by remember { mutableStateOf(false) }

    val words = listOf("RED", "BLUE", "GREEN", "YELLOW", "BLACK")
    val colors = listOf(
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Yellow,
        Color.Black
    )
    val wordColorMap = mapOf(
        "RED" to Color.Red,
        "BLUE" to Color.Blue,
        "GREEN" to Color.Green,
        "YELLOW" to Color.Yellow,
        "BLACK" to Color.Black
    )

    fun updateTest() {
        val wordIndex = Random.nextInt(words.size)
        val colorIndex = Random.nextInt(colors.size)
        currentWord = words[wordIndex]
        currentColor = if (isMatching) wordColorMap[words[wordIndex]]!! else colors[colorIndex]
        isMatching = counter < 9
    }

    fun endGame(context: android.content.Context) {
        val email = FirebaseAuth.getInstance().currentUser?.email ?: ""
        val averageResponseTime = if (responseTimes.isNotEmpty()) responseTimes.average() else 0.0
        val accuracy = (score.toDouble() / 18) * 100

        val gameData = GameDto(
            email = email,
            title = "Stroop Test",
            date = Date(System.currentTimeMillis()),
            responseTime = Time(averageResponseTime.toLong()),
            totalTime = Time(totalTime),
            accuracy = accuracy
        )

        val errorManager = ErrorManager(context)
        val game = Game(errorManager)
        game.storeGame(gameData) { success ->
            if (success) {
                // Show success message
            } else {
                // Handle error
            }
        }
    }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        while (counter > 0) {
            val startTime = System.currentTimeMillis()
            delay(3000)
            if (!isPaused) {
                val endTime = System.currentTimeMillis()
                totalTime += (endTime - startTime)
                responseTimes.add(endTime - startTime)
                counter--
                updateTest()
            }
        }
        showAnswer = true
        endGame(context)
    }

    val loadingProgress by animateFloatAsState(
        targetValue = if (counter > 0) counter / 18f else 0f,
        animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
    )

    fun checkMatch() {
        if (currentColor.toArgb() == wordColorMap[currentWord]?.toArgb()) {
            score++
        }
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
                            text = "Stroop Test",
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
                Text(
                    text = currentWord,
                    fontFamily = pressStartFontFamily,
                    color = currentColor,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = { checkMatch() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Press if matching", fontFamily = pressStartFontFamily)
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = counter.toString(),
                        fontFamily = pressStartFontFamily,
                        color = Color.White,
                        fontSize = 32.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LoadingStrip(colorResource(id = R.color.red), loadingProgress)
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

@Composable
fun LoadingStrip(color: Color, progress: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .background(color)
            .fillMaxWidth(fraction = progress)
    )
}

@Composable
fun PauseDialog(onResume: () -> Unit, onQuit: () -> Unit) {
    val pressStartFontFamily = FontFamily(Font(R.font.press_start))

    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(text = "Game Paused", fontFamily = pressStartFontFamily) },
        text = { Text("Would you like to resume or quit the game?", fontFamily = pressStartFontFamily) },
        confirmButton = {
            Button(onClick = onResume) {
                Text("Resume", fontFamily = pressStartFontFamily)
            }
        },
        dismissButton = {
            Button(onClick = onQuit) {
                Text("Quit", fontFamily = pressStartFontFamily)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewStrooperPlayScreen() {
    val navController = rememberNavController()
    StrooperPlayScreen(navController)
}
