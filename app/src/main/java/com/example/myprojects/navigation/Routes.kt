package com.example.myprojects.navigation

sealed class Routes(val routes: String) {

    data object Home: Routes("home")
    data object Notification: Routes("notification")
   data object Profile: Routes("profile")
    data object Search: Routes("search")
    data object Splash: Routes("splash")
    data object AddThreads: Routes("add_thread")
    data object BottomNav: Routes("bottom_nav")




}