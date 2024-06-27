package firebase
import android.content.Context
import android.widget.Toast

/**
 * This class is responsible for handling error and success messages in the application.
 *
 * @property context An instance of Context to show Toast messages.
 */
class ErrorManager(private val context: Context) {

    /**
     * Shows an error message using a Toast.
     *
     * @param message The error message to be shown.
     */
    fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Shows a success message using a Toast.
     *
     * @param message The success message to be shown.
     */
    fun showSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}