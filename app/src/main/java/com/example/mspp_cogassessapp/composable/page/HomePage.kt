import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mspp_cogassessapp.R
import com.example.mspp_cogassessapp.firebase.Game
import com.example.mspp_cogassessapp.firebase.GameDto
import firebase.ErrorManager

@Composable
fun HomePage() {
    var games by remember { mutableStateOf<List<GameDto>?>(null) }
    var loading by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val errorManager = ErrorManager(context)
    val game = Game(errorManager)

    LaunchedEffect(Unit) {
        game.fetchUserGames { fetchedGames ->
            games = fetchedGames
            loading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.mantle))
            .padding(16.dp)
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else {
            games?.let {
                if (it.isNotEmpty()) {
                    LazyColumn {
                        items(it) { game ->
                            GameItem(game)
                        }
                    }
                } else {
                    Text("No games found for this user.")
                }
            } ?: run {
                Text("Failed to load games.")
            }
        }
    }
}

@Composable
fun GameItem(game: GameDto) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.base), RoundedCornerShape(10.dp))
            .padding(16.dp)
            .padding(bottom = 8.dp)
    ) {
        Column {
            Text(text = "Title: ${game.title}", style = MaterialTheme.typography.h6, color = Color.White)
            Text(text = "Date: ${game.date}", color = Color.White)
            Text(text = "Response Time: ${game.responseTime}", color = Color.White)
            Text(text = "Total Time: ${game.totalTime}", color = Color.White)
            Text(text = "Accuracy: ${game.accuracy}", color = Color.White)
        }
    }
}

@Preview
@Composable
fun PreviewHomePage() {
    HomePage()
}
