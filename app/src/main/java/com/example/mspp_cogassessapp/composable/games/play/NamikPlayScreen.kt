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

@Composable
fun NamikPlayScreen(navController: NavController) {
    val pressStartFontFamily = FontFamily(Font(R.font.press_start))
    var showAnswers by remember { mutableStateOf(false) }
    val blueColor = colorResource(id = R.color.blue)
    val redColor = colorResource(id = R.color.red)
    var loadingColor by remember { mutableStateOf(blueColor) }
    var isPaused by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(4) }

    LaunchedEffect(Unit) {
        while (counter > 0) {
            delay(1000)
            if (!isPaused) {
                counter--
            }
        }
        loadingColor = redColor
        showAnswers = true
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
                    DisplayAnswerGrid()
                } else {
                    DisplaySequence()
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter) // Align to the bottom
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
                    LoadingStrip(loadingColor, loadingProgress)
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
fun DisplaySequence() {
    val icons = listOf(
        R.drawable.namik_ic_1,
        R.drawable.namik_ic_2,
        R.drawable.namik_ic_3,
        R.drawable.namik_ic_4,
        R.drawable.namik_ic_5
    ).shuffled()

    val sequence = remember { icons.take(4) }

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
fun LoadingStrip(color: Color, progress: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp) // Thicker height
            .background(color) // White color for the loading strip
            .fillMaxWidth(fraction = progress) // Adjust width according to progress
    )
}

@Composable
fun DisplayAnswerGrid() {
    val icons = listOf(
        R.drawable.namik_ic_1,
        R.drawable.namik_ic_2,
        R.drawable.namik_ic_3,
        R.drawable.namik_ic_4,
        R.drawable.namik_ic_5
    ).shuffled()

    val sequence = icons.take(4)
    val otherOptions = mutableListOf<List<Int>>()

    // Generate three other random sequences
    for (i in 0 until 3) {
        val option = icons.shuffled().take(4)
        if (option != sequence && option !in otherOptions) {
            otherOptions.add(option)
        }
    }

    // Add the correct sequence to the options and shuffle them
    val options = (otherOptions + listOf(sequence)).shuffled()

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
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
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

@Preview(showBackground = true)
@Composable
fun PreviewNamikPlayScreen() {
    val navController = rememberNavController()
    NamikPlayScreen(navController)
}
