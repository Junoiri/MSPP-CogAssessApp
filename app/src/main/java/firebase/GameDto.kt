package com.example.mspp_cogassessapp.firebase

import java.util.Date
import java.sql.Time

/**
 * Fetches games for the currently logged in user from Firebase Firestore.
 *
 * @param onComplete A callback function that is invoked when the game fetching operation is complete.
 * The function takes a List of GameDto objects as a parameter. If the game fetching is successful,
 * the List contains the fetched games. If the game fetching fails, the List is null.
 */
data class GameDto(
    var email: String = "",
    var title: String = "",
    var date: Date = Date(System.currentTimeMillis()),
    var responseTime: Time = Time(0),
    var totalTime: Time = Time(0),
    var accuracy: Double = 0.0
) {
    constructor() : this("", "", Date(System.currentTimeMillis()), Time(0), Time(0), 0.0)
}
