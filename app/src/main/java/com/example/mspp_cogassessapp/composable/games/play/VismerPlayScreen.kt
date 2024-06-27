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

/**
 * This composable function displays the Vismer game screen.
 *
 * @param navController The NavController that this function will use to navigate between composables.
 */
@Composable
fun VismerPlayScreen(navController: NavController) {
    val context = LocalContext.current  // Get the context for the toast message
    val pressStartFontFamily = FontFamily(Font(R.font.press_start))
    var showAnswers by remember { mutableStateOf(false) }
    val greenColor = colorResource(id = R.color.green)
    val redColor = colorResource(id = R.color.red)
    var loadingColor by remember { mutableStateOf(greenColor) }
    var isPaused by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(4) }
    var cycleCounter by remember { mutableStateOf(0) }  // Cycle counter
    var correctAnswers by remember { mutableStateOf(0) }  // Correct answers counter
    var totalQuestions by remember { mutableStateOf(0) }  // Total questions counter
    var currentIcon by remember { mutableStateOf(R.drawable.ic_vismer_1) } // Current icon state
    var letterOptions by remember { mutableStateOf<List<Char>>(emptyList()) } // Letter options state
    var selectedOption by remember { mutableStateOf<Char?>(null) } // Selected option state

    val objectIcons = listOf(
        R.drawable.ic_vismer_1,
        R.drawable.ic_vismer_2,
        R.drawable.ic_vismer_3,
        R.drawable.ic_vismer_4,
        R.drawable.ic_vismer_5,
        R.drawable.ic_vismer_6
    )
    val objectNames = listOf("Duck", "Snake", "Frog", "Butterfly", "Cherry", "Leaf")

    LaunchedEffect(cycleCounter) {
        if (cycleCounter > 0 && cycleCounter % 10 == 0) {
            isPaused = true
            showResultDialog = true
        }
    }

    LaunchedEffect(Unit) {
        val (newIcon, newLetters) = generateIconAndLetters(objectIcons, objectNames)
        currentIcon = newIcon
        letterOptions = newLetters
        while (true) {
            while (counter > 0) {
                delay(1000)
                if (!isPaused) {
                    counter--
                }
            }
            loadingColor = if (loadingColor == greenColor) redColor else greenColor
            showAnswers = !showAnswers

            if (showAnswers) {
                // Displaying answers, do nothing special here
            } else {
                // After the answer phase, increment cycleCounter and generate new icon and letters
                cycleCounter++  // Increment cycle counter only when an answer cycle ends
                if (cycleCounter > 0 && cycleCounter % 10 == 0) {
                    isPaused = true
                    showResultDialog = true
                }
                val (newIcon, newLetters) = generateIconAndLetters(objectIcons, objectNames)
                currentIcon = newIcon
                letterOptions = newLetters
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
                            text = "Vismer",
                            fontFamily = pressStartFontFamily,
                            color = colorResource(id = R.color.green),
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
                painter = painterResource(id = R.drawable.bg_vismer_play),
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
                    DisplayLetterGrid(letterOptions, selectedOption) { option ->
                        // Handle letter click
                        totalQuestions++
                        if (option == objectNames[objectIcons.indexOf(currentIcon)][0]) {
                            correctAnswers++
                        }
                        selectedOption = option
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                    DisplayIcon(currentIcon)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp), // Move everything down by 150 dp
                contentAlignment = Alignment.BottomCenter // Align to the bottom
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        progress = 1f - (counter / 4f),
                        color = loadingColor,
                        modifier = Modifier
                            .size(100.dp) // Set the size of the loading circle
                            .padding(bottom = 16.dp, top = 50.dp), // Add bottom margin
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
                PauseDialogVismer(
                    onResume = { isPaused = false; showDialog = false },
                    onQuit = { navController.popBackStack() }
                )
            }
            if (showResultDialog) {
                ResultDialogVismer(
                    correctAnswers = correctAnswers,
                    totalQuestions = totalQuestions,
                    onContinue = { isPaused = false; showResultDialog = false },
                    onQuit = { navController.popBackStack() }
                )
            }
        }
    }
}

/**
 * This composable function displays the current icon in the Vismer game.
 *
 * @param icon The resource ID of the icon to display.
 */
@Composable
fun DisplayIcon(icon: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Click the first letter of the object!",
            fontFamily = FontFamily(Font(R.font.press_start)),
            color = colorResource(id = R.color.crust),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(96.dp) // Reduced size of the square
        )
    }
}

/**
 * This composable function displays a grid of letter options in the Vismer game.
 *
 * @param options The list of letter options to display.
 * @param selectedOption The currently selected letter option.
 * @param onOptionClick A function to call when a letter option is clicked.
 */
@Composable
fun DisplayLetterGrid(options: List<Char>, selectedOption: Char?, onOptionClick: (Char) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center, // Center the grid vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center the grid horizontally
    ) {
        options.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center // Center the row horizontally
            ) {
                row.forEach { option ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = if (selectedOption == option) colorResource(id = R.color.green).copy(alpha = 0.8f) else Color.White.copy(alpha = 0.8f),
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(8.dp) // Padding between cards
                            .clickable { onOptionClick(option) }
                            .shadow(if (selectedOption == option) 8.dp else 0.dp, RoundedCornerShape(8.dp))
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = option.toString(),
                                fontFamily = FontFamily(Font(R.font.press_start)),
                                fontSize = 32.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * This composable function displays a dialog that allows the user to resume or quit the Vismer game.
 *
 * @param onResume A function to call when the "Resume" button is clicked.
 * @param onQuit A function to call when the "Quit" button is clicked.
 */
@Composable
fun PauseDialogVismer(onResume: () -> Unit, onQuit: () -> Unit) {
    val pressStartFontFamily = FontFamily(Font(R.font.press_start))

    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(text = "Game Paused", fontFamily = pressStartFontFamily) },
        text = { Text("Would you like to resume or quit the game?", fontFamily = pressStartFontFamily) },
        confirmButton = {
            Button(
                onClick = onResume,
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.green))
            ) {
                Text("Resume", fontFamily = pressStartFontFamily, color = colorResource(id = R.color.crust))
            }
        },
        dismissButton = {
            Button(
                onClick = onQuit,
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.green))
            ) {
                Text("Quit", fontFamily = pressStartFontFamily, color = colorResource(id = R.color.crust))
            }
        }
    )
}

/**
 * This composable function displays a dialog that shows the user's results in the Vismer game.
 *
 * @param correctAnswers The number of correct answers the user gave.
 * @param totalQuestions The total number of questions the user answered.
 * @param onContinue A function to call when the "Continue" button is clicked.
 * @param onQuit A function to call when the "Quit" button is clicked.
 */
@Composable
fun ResultDialogVismer(correctAnswers: Int, totalQuestions: Int, onContinue: () -> Unit, onQuit: () -> Unit) {
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

/**
 * This function generates a new icon and a list of letter options for the Vismer game.
 *
 * @param icons The list of possible icons.
 * @param names The list of names corresponding to the icons.
 * @return A pair containing the new icon and the list of letter options.
 */
fun generateIconAndLetters(icons: List<Int>, names: List<String>): Pair<Int, List<Char>> {
    val index = (icons.indices).random()
    val icon = icons[index]
    val name = names[index]
    val letters = listOf(name.first()) + List(3) { ('A'..'Z').filterNot { it == name.first() }.random() }
    return Pair(icon, letters.shuffled())
}

/**
 * This composable function is used to preview the VismerPlayScreen in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun PreviewVismerPlayScreen() {
    val navController = rememberNavController()
    VismerPlayScreen(navController)
}
