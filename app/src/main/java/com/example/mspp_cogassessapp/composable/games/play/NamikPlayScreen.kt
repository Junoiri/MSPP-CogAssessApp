import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mspp_cogassessapp.R
import kotlinx.coroutines.delay
import android.widget.Toast
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext


@Composable
fun NamikPlayScreen(navController: NavController) {
    val context = LocalContext.current  // Get the context for the toast message
    val pressStartFontFamily = FontFamily(Font(R.font.press_start))
    var showAnswers by remember { mutableStateOf(false) }
    val blueColor = colorResource(id = R.color.blue)
    val redColor = colorResource(id = R.color.red)
    var loadingColor by remember { mutableStateOf(blueColor) }
    var isPaused by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(4) }
    var cycleCounter by remember { mutableStateOf(0) }  // Cycle counter
    var correctAnswers by remember { mutableStateOf(0) }  // Correct answers counter
    var totalQuestions by remember { mutableStateOf(0) }  // Total questions counter
    var sequence by remember { mutableStateOf<List<Int>>(emptyList()) } // Sequence state
    var answerOptions by remember { mutableStateOf<List<List<Int>>>(emptyList()) } // Answer options state
    var selectedOption by remember { mutableStateOf<List<Int>?>(null) } // Selected option state

    LaunchedEffect(cycleCounter) {
        if (cycleCounter > 0 && cycleCounter % 10 == 0) {
            isPaused = true
            showResultDialog = true
        }
    }

    LaunchedEffect(Unit) {
        sequence = generateSequence()
        answerOptions = generateAnswerOptions(sequence)
        while (true) {
            while (counter > 0) {
                delay(1000)
                if (!isPaused) {
                    counter--
                }
            }
            loadingColor = if (loadingColor == blueColor) redColor else blueColor
            showAnswers = !showAnswers

            if (showAnswers) {
                // Displaying answers, do nothing special here
            } else {
                // After the answer phase, increment cycleCounter and generate new sequence and answers
                cycleCounter++  // Increment cycle counter only when an answer cycle ends
                if (cycleCounter > 0 && cycleCounter % 10 == 0) {
                    isPaused = true
                    showResultDialog = true
                }
                sequence = generateSequence()
                answerOptions = generateAnswerOptions(sequence)
                selectedOption = null // Reset selected option
            }
            counter = 4
        }
    }

    val loadingProgress by animateFloatAsState(
        targetValue = if (counter > 0) counter / 4f else 0f,
        animationSpec = tween(durationMillis = 4000, easing = LinearEasing)
    )

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
                            text = "Namik",
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
            Image(
                painter = painterResource(id = R.drawable.bg_namik_play),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top // Align elements to the top
            ) {
                if (showAnswers) {
                    DisplayAnswerGrid(answerOptions, selectedOption) { option ->
                        // Handle answer click
                        totalQuestions++
                        if (option == sequence) {
                            correctAnswers++
                        }
                        selectedOption = option
                    }
                } else {
                    DisplaySequence(sequence)
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 150.dp), // Move everything down by 150 dp
                contentAlignment = Alignment.Center // Align to the center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        progress = 1f - (counter / 4f),
                        color = loadingColor,
                        modifier = Modifier
                            .size(100.dp) // Set the size of the loading circle
                            .padding(top = 50.dp), // Add top margin
                        strokeWidth = 8.dp // Make the loading circle thicker
                    )
                    Text(
                        text = counter.toString(),
                        fontFamily = pressStartFontFamily,
                        color = Color.White,
                        fontSize = 32.sp,
                        modifier = Modifier.align(Alignment.Center)
                            .padding(top = 100.dp)
                    )
                }
            }
            if (showDialog) {
                PauseDialog(
                    onResume = { isPaused = false; showDialog = false },
                    onQuit = { navController.popBackStack() }
                )
            }
            if (showResultDialog) {
                ResultDialog(
                    correctAnswers = correctAnswers,
                    totalQuestions = totalQuestions,
                    onContinue = { isPaused = false; showResultDialog = false },
                    onQuit = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun DisplaySequence(sequence: List<Int>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Remember the sequence!",
            fontFamily = FontFamily(Font(R.font.press_start)),
            color = colorResource(id = R.color.crust),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            sequence.forEach { icon ->
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

@Composable
fun DisplayAnswerGrid(options: List<List<Int>>, selectedOption: List<Int>?, onOptionClick: (List<Int>) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        options.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                row.forEach { option ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = if (selectedOption == option) Color.White else Color.White.copy(alpha = 0.8f),
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clickable { onOptionClick(option) }
                            .shadow(if (selectedOption == option) 8.dp else 0.dp, RoundedCornerShape(8.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            option.forEach { icon ->
                                Image(
                                    painter = painterResource(id = icon),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }
                    }
                }
            }
        }
    }
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

@Composable
fun ResultDialog(correctAnswers: Int, totalQuestions: Int, onContinue: () -> Unit, onQuit: () -> Unit) {
    val pressStartFontFamily = FontFamily(Font(R.font.press_start))

    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(text = "Results", fontFamily = pressStartFontFamily) },
        text = { Text("You got $correctAnswers out of $totalQuestions correct.", fontFamily = pressStartFontFamily) },
        confirmButton = {
            Button(onClick = onContinue) {
                Text("Continue", fontFamily = pressStartFontFamily)
            }
        },
        dismissButton = {
            Button(onClick = onQuit) {
                Text("Quit", fontFamily = pressStartFontFamily)
            }
        }
    )
}

fun generateSequence(): List<Int> {
    val icons = listOf(
        R.drawable.namik_ic_1,
        R.drawable.namik_ic_2,
        R.drawable.namik_ic_3,
        R.drawable.namik_ic_4,
        R.drawable.namik_ic_5
    ).shuffled()

    return icons.take(4)
}

fun generateAnswerOptions(correctSequence: List<Int>): List<List<Int>> {
    val icons = listOf(
        R.drawable.namik_ic_1,
        R.drawable.namik_ic_2,
        R.drawable.namik_ic_3,
        R.drawable.namik_ic_4,
        R.drawable.namik_ic_5
    )

    val otherOptions = mutableListOf<List<Int>>()

    // Generate three other random sequences
    for (i in 0 until 3) {
        val option = icons.shuffled().take(4)
        if (option != correctSequence && option !in otherOptions) {
            otherOptions.add(option)
        }
    }

    // Add the correct sequence to the options and shuffle them
    return (otherOptions + listOf(correctSequence)).shuffled()
}

@Preview(showBackground = true)
@Composable
fun PreviewNamikPlayScreen() {
    val navController = rememberNavController()
    NamikPlayScreen(navController)
}
