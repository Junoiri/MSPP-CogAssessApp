package com.example.mspp_cogassessapp

import java.sql.Date
import java.sql.Time

data class Game(
    var email: String = "",
    var date: Date=Date(System.currentTimeMillis()),
    var responseTime: Time,
    val totalTime: Time,
    val accuracy: Double,
){
    constructor() : this("", Date(System.currentTimeMillis()), Time(0), Time(0), 0.0)
}
