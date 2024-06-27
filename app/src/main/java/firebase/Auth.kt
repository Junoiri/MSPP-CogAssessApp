package com.example.mspp_cogassessapp.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import firebase.ErrorManager

/**
 * This class is responsible for handling authentication-related operations with Firebase.
 *
 * @property errorManager An instance of ErrorManager to handle error scenarios.
 */
class Auth(private val errorManager: ErrorManager) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Registers a new user with Firebase Authentication and stores user data in Firebase Firestore.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param userData A UserDataDto object containing the user data to be stored.
     * @param onComplete A callback function that is invoked when the user registration operation is complete.
     * The function takes a Boolean as a parameter. If the user registration is successful, the Boolean is true.
     * If the user registration fails, the Boolean is false.
     */
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

    /**
     * Logs in an existing user with Firebase Authentication.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param onComplete A callback function that is invoked when the user login operation is complete.
     * The function takes a Boolean as a parameter. If the user login is successful, the Boolean is true.
     * If the user login fails, the Boolean is false.
     */
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

/**
 * A data class that represents user data.
 *
 * @property email The email address of the user.
 * @property name The name of the user.
 * @property sex The sex of the user.
 * @property age The age of the user.
 */
data class UserDataDto(
    var email: String = "",
    var name: String = "",
    var sex: String = "",
    var age: Int = 0
)
