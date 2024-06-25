package com.example.mspp_cogassessapp.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import firebase.ErrorManager

class User(private val errorManager: ErrorManager) {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Retrieve user data
    fun getUserData(onComplete: (UserDataDto?) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            errorManager.showError("User not logged in")
            onComplete(null)
            return
        }

        Log.d("User", "Fetching data for UID: $uid")

        db.collection("users").document(uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userData = task.result?.toObject(UserDataDto::class.java)
                    if (userData != null) {
                        Log.d("User", "User data retrieved successfully")
                        onComplete(userData)
                    } else {
                        Log.d("User", "No user data found for UID: $uid")
                        errorManager.showError("No user data found")
                        onComplete(null)
                    }
                } else {
                    Log.e("User", "Failed to retrieve user data: ${task.exception?.message}")
                    errorManager.showError("Failed to retrieve user data: ${task.exception?.message}")
                    onComplete(null)
                }
            }
    }
}
