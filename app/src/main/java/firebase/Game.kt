package com.example.mspp_cogassessapp.firebase

import com.google.firebase.firestore.FirebaseFirestore
import firebase.ErrorManager
import java.util.Date
import java.sql.Time

class Game(private val errorManager: ErrorManager) {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Store a new game
    fun storeGame(gameData: GameDto, onComplete: (Boolean) -> Unit) {
        db.collection("games").add(gameData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    errorManager.showSuccess("Game stored successfully!")
                    onComplete(true)
                } else {
                    errorManager.showError("Failed to store game: ${task.exception?.message}")
                    onComplete(false)
                }
            }
    }

    // Retrieve games for a specific user
    fun getGamesByUser(email: String, onComplete: (List<GameDto>?) -> Unit) {
        db.collection("games")
            .whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val games = task.result?.toObjects(GameDto::class.java)
                    onComplete(games)
                } else {
                    errorManager.showError("Failed to retrieve games: ${task.exception?.message}")
                    onComplete(null)
                }
            }
    }
}
