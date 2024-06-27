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
import androidx.compose.ui.graphics.Color
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

/**
 * This composable function displays the information screen for the Namik game.
 *
 * @param navController The NavController that this function will use to navigate between composables.
 */
@Composable
fun NamikInfoScreen(navController: NavController) {
    // Load the font family
    val pressStartFontFamily = remember { FontFamily(Font(R.font.press_start)) }

    // Scaffold provides a framework that adheres to Material Design guidelines
    Scaffold(
        topBar = {
            // TopAppBar is a container for the top app bar
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
            // LazyColumn is a vertically scrolling list that only composes and lays out the currently visible items
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)  // Ensure there's enough space at the bottom for the button
            ) {
                item {
                    // Display the game image
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
                    // Display the game name
                    Text(
                        text = "Namik",
                        fontSize = 32.sp,
                        fontFamily = pressStartFontFamily,
                        color = colorResource(id = R.color.blue),  // Use the blue color from colors.xml
                        modifier = Modifier.padding(start = 16.dp)  // Increased padding around the game name text
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Display the game type
                    Text(
                        text = "Visual Memory Test",
                        fontSize = 18.sp,
                        fontFamily = pressStartFontFamily,
                        color = colorResource(id = R.color.text),
                        modifier = Modifier.padding(start = 16.dp, top = 6.dp)  // Padding on the left side
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Display the game info
                    Text(
                        text = "Info",
                        fontSize = 24.sp,
                        fontFamily = pressStartFontFamily,
                        color = colorResource(id = R.color.blue),
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)  // Padding for the info header
                    )
                    Text(
                        "The Recognition Test WOM-REST combines elements from well-known cognitive assessments like the Symbol Search Test (WAIS), the Wisconsin Card Sorting Test (WCST), and Raven's Progressive Matrices.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.text),
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, top = 8.dp)  // Increased padding around the content text
                    )
                    // Display the game instructions
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
                        "In this test, you'll see a sequence of three objects on the screen. First, you'll need to memorize these objects. Then, on the next screen, you'll be asked to identify the original sequence from a group of four options. Your score will be based on the number of correct answers, which will determine your accuracy percentage.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.text),
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, top = 8.dp)  // Increased padding around the content text
                    )
                }
            }
            // Display the start game button at the bottom of the screen
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
                            .padding(10.dp, top = 50.dp)
                            .clickable {
                                navController.navigate(Screen.NamikPlay.route)
                            }
                    )
                }
            }
        }
    }
}

/**
 * This composable function is used to preview the NamikInfoScreen in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun PreviewNamikInfoScreen() {
    val navController = rememberNavController()
    NamikInfoScreen(navController)
}