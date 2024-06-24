package firebase
import android.content.Context
import android.widget.Toast

class ErrorManager(private val context: Context) {
    fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
