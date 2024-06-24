package com.example.mspp_cogassessapp.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import firebase.ErrorManager

class Auth(private val errorManager: ErrorManager) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Register a new user
    fun registerUser(email: String, password: String, userData: UserDataDto, onComplete: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                    db.collection("users").document(userId).set(userData)
                        .addOnCompleteListener { userTask ->
                            if (userTask.isSuccessful) {
                                errorManager.showSuccess("Registered successfully!")
                                onComplete(true)
                            } else {
                                errorManager.showError("Failed to register user data: ${userTask.exception?.message}")
                                onComplete(false)
                            }
                        }
                } else {
                    errorManager.showError("Registration failed: ${task.exception?.message}")
                    onComplete(false)
                }
            }
    }

    // Login an existing user
    fun loginUser(email: String, password: String, onComplete: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    errorManager.showSuccess("Logged in successfully!")
                    onComplete(true)
                } else {
                    errorManager.showError("Login failed: ${task.exception?.message}")
                    onComplete(false)
                }
            }
    }
}

// Data class for user data
data class UserDataDto(
    var email: String = "",
    var name: String = "",
    var sex: String = "",
    var age: Int = 0
)
