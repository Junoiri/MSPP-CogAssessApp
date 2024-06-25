import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mspp_cogassessapp.R
import com.example.mspp_cogassessapp.util.Screen

@Composable
fun GamesPage(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(24.dp)  // Increased outer padding
            .background(colorResource(id = R.color.mantle))
            .fillMaxSize()
    ) {
        Text(
            "Welcome to the Games Page!",
            fontSize = 32.sp,  // Significantly larger font size
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)  // Increased padding below the title
        )

        // List of items for the grid with specific backgrounds for the first three items
        val items = listOf(
            Pair("Vismer", painterResource(id = R.drawable.bg_card_green)),
            Pair("Namik", painterResource(id = R.drawable.bg_card_blue)),
            Pair("Strooper", painterResource(id = R.drawable.bg_card_purple))
        ) + List(17) { Pair("Coming Soon!", null) }  // First 3 items with specific backgrounds, rest are placeholders

        // Creating a grid with 2 columns
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),  // Increased padding around the grid
            horizontalArrangement = Arrangement.spacedBy(16.dp),  // Increased horizontal spacing
            verticalArrangement = Arrangement.spacedBy(16.dp)  // Increased vertical spacing
        ) {
            items(items) { item ->
                val (title, painter) = item
                val backgroundColor = if (title == "Coming Soon!") colorResource(id = R.color.pink) else Color.Transparent
                Card(
                    backgroundColor = backgroundColor,
                    shape = RoundedCornerShape(16.dp),  // Increased rounding
                    modifier = Modifier
                        .aspectRatio(1f)  // Ensures the cards are square
                        .fillMaxWidth()
                        .clickable {
                            when (title) {
                                "Namik" -> navController.navigate(Screen.NamikInfo.route)
                                "Strooper" -> navController.navigate(Screen.StrooperInfo.route)
                                "Vismer" -> navController.navigate(Screen.VismerInfo.route)
                            }
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        if (painter != null) {
                            Image(
                                painter = painter,
                                contentDescription = title,
                                contentScale = ContentScale.Crop,  // Crop the image to fit the area
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        SubcomposeLayout { constraints ->
                            val placeables = subcompose("blurredBackground") {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .background(Color.Transparent)
                                        .align(Alignment.BottomCenter)
                                ) {
                                    if (painter != null) {
                                        Image(
                                            painter = painter,
                                            contentDescription = title,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .blur(25.dp)
                                                .clip(
                                                    RoundedCornerShape(
                                                        bottomStart = 16.dp,
                                                        bottomEnd = 16.dp
                                                    )
                                                )
                                        )
                                    }
                                }
                            }.map { it.measure(constraints) }

                            val placeableText = subcompose("text") {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .align(Alignment.Center)
                                ) {
                                    Text(
                                        text = title,
                                        fontSize = 20.sp,  // Increased font size for game titles
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }.map { it.measure(constraints) }

                            layout(constraints.maxWidth, constraints.maxHeight) {
                                placeables.forEach { it.placeRelative(0, constraints.maxHeight - 50.dp.roundToPx()) }
                                placeableText.forEach { it.placeRelative(0, constraints.maxHeight - 50.dp.roundToPx()) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewGamesPage() {
    val navController = rememberNavController()
    GamesPage(navController)
}

