package com.example.mspp_cogassessapp.composable

import android.content.Context
import com.example.mspp_cogassessapp.firebase.Game
import com.example.mspp_cogassessapp.firebase.GameDto
import com.google.firebase.auth.FirebaseAuth
import firebase.ErrorManager
import java.sql.Time
import java.util.Date

/**
 * This function ends the game and stores the game data.
 *
 * @param context The context in which this function is called.
 * @param email The email of the user.
 * @param title The title of the game.
 * @param averageResponseTime The average response time of the user in the game.
 * @param totalTime The total time the user spent in the game.
 * @param accuracy The accuracy of the user's answers in the game.
 * @param onComplete A function to call when the game data has been stored.
 */
fun endGame(context: Context, email: String, title: String, averageResponseTime: Double, totalTime: Long, accuracy: Double, onComplete: (Boolean) -> Unit) {
    val gameData = GameDto(
        email = email,
        title = title,
        date = Date(System.currentTimeMillis()),
        responseTime = Time(averageResponseTime.toLong()),
        totalTime = Time(totalTime),
        accuracy = accuracy
    )

    val errorManager = ErrorManager(context)
    val game = Game(errorManager)
    game.storeGame(gameData, onComplete)
}
