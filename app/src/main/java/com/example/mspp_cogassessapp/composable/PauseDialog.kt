package com.example.mspp_cogassessapp.composable

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.mspp_cogassessapp.R

/**
 * This composable function displays a dialog when the game is paused.
 *
 * @param onResume A function to call when the Resume button is clicked.
 * @param onQuit A function to call when the Quit button is clicked.
 */
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
