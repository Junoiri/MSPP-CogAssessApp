import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mspp_cogassessapp.R
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val pageCount = 3
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.crust),
                title = { Text("Home", color = colorResource(id = R.color.text)) },
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
        },
        bottomBar = {
            BottomNavigation(backgroundColor = colorResource(id = R.color.crust)) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile", tint = colorResource(id = R.color.teal)) },
                    selected = pagerState.currentPage == 0,
                    onClick = { scope.launch { pagerState.animateScrollToPage(0) } }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = colorResource(id = R.color.sky)) },
                    selected = pagerState.currentPage == 1,
                    onClick = { scope.launch { pagerState.animateScrollToPage(1) } }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Gamepad, contentDescription = "Games", tint = colorResource(id = R.color.pink)) },
                    selected = pagerState.currentPage == 2,
                    onClick = { scope.launch { pagerState.animateScrollToPage(2) } }
                )
            }
        },
        backgroundColor = colorResource(id = R.color.crust)  // Set the background color for the Scaffold to crust
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(innerPadding)
                .background(colorResource(id = R.color.base))  // Ensure the background color of the pager container is set to crust
                .padding(horizontal = 10.dp)  // Apply horizontal padding to maintain swipe gesture space
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)  // Inner padding for each page content
                    .background(colorResource(id = R.color.mantle), RoundedCornerShape(8.dp))  // Individual page backgrounds
            ) {
                when (page) {
                    0 -> ProfilePage()
                    1 -> HomePage()
                    2 -> GamesPage(navController)  // Pass the navController here
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController)
}
