package com.example.mspp_cogassessapp.util

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object ForgotPassword : Screen("forgotPassword")
    object NamikInfo : Screen("namikInfo")
    object NamikPlay : Screen("namikPlay")
    object StrooperInfo : Screen("strooperInfo")
    object StrooperPlay : Screen("strooperPlay")
    object VismerPlay : Screen("vismerPlay")
    object VismerInfo : Screen("vismerInfo")
    object SimonInfo : Screen("simonInfo")
    object SimonPlay : Screen("simonPlay")
}
