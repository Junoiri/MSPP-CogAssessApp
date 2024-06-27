package com.example.mspp_cogassessapp.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import firebase.ErrorManager
import java.util.Date
import java.sql.Time

/**
 * This class is responsible for handling game-related operations with Firebase.
 *
 * @property errorManager An instance of ErrorManager to handle error scenarios.
 */
class Game(private val errorManager: ErrorManager) {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    /**
     * Stores a new game in Firebase Firestore.
     *
     * @param gameData A GameDto object containing the game data to be stored.
     * @param onComplete A callback function that is invoked when the game storing operation is complete.
     * The function takes a Boolean as a parameter. If the game storing is successful, the Boolean is true.
     * If the game storing fails, the Boolean is false.
     */
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

    /**
     * Retrieves games for a specific user from Firebase Firestore.
     *
     * @param email The email address of the user.
     * @param onComplete A callback function that is invoked when the game retrieval operation is complete.
     * The function takes a List of GameDto objects as a parameter. If the game retrieval is successful,
     * the List contains the retrieved games. If the game retrieval fails, the List is null.
     */
    fun getGamesByUser(email: String, onComplete: (List<GameDto>?) -> Unit) {
        db.collection("games")
            .whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val games = task.result?.toObjects<GameDto>()
                    onComplete(games)
                } else {
                    errorManager.showError("Failed to retrieve games: ${task.exception?.message}")
                    onComplete(null)
                }
            }
    }

    /**
     * Fetches games for the currently logged in user from Firebase Firestore.
     *
     * @param onComplete A callback function that is invoked when the game fetching operation is complete.
     * The function takes a List of GameDto objects as a parameter. If the game fetching is successful,
     * the List contains the fetched games. If the game fetching fails, the List is null.
     */
    fun fetchUserGames(onComplete: (List<GameDto>?) -> Unit) {
        val email = FirebaseAuth.getInstance().currentUser?.email ?: ""
        getGamesByUser(email, onComplete)
    }
}
