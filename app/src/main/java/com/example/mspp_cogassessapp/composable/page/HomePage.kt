import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mspp_cogassessapp.R

/**
 * This composable function displays the Home Page.
 */
@Preview
@Composable
fun HomePage() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(colorResource(id = R.color.mantle))
//            .padding(10.dp)
//            .background(colorResource(id = R.color.base), RoundedCornerShape(10.dp))
    ) {
        Text("Welcome to the Home Page!")
    }
}
/**
 * This composable function is used to preview the HomePage in Android Studio.
 */
@Preview
@Composable
fun PreviewHomePage() {
    HomePage()
}