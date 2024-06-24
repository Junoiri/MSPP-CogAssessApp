package com.example.mspp_cogassessapp.firebase

import java.util.Date
import java.sql.Time

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
