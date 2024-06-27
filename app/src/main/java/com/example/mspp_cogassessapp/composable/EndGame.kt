package com.example.mspp_cogassessapp.composable

import android.content.Context
import com.example.mspp_cogassessapp.firebase.Game
import com.example.mspp_cogassessapp.firebase.GameDto
import com.google.firebase.auth.FirebaseAuth
import firebase.ErrorManager
import java.sql.Time
import java.util.Date

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
