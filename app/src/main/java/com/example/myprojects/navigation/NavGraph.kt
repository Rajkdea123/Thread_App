package com.example.myprojects.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myprojects.screens.AddThreads
import com.example.myprojects.screens.BottomNav
import com.example.myprojects.screens.Home
import com.example.myprojects.screens.Login
import com.example.myprojects.screens.Notification
import com.example.myprojects.screens.OtherUsers
import com.example.myprojects.screens.Profile
import com.example.myprojects.screens.Register
import com.example.myprojects.screens.Search
import com.example.myprojects.screens.Splash

@Composable
fun NavGraph(navController: NavHostController){


    NavHost(navController= navController, startDestination = Routes.Splash.routes){


        composable(Routes.Splash.routes) {
            Splash(navController)
        }
        composable(Routes.Home.routes) {
            Home(navController)
        }
        composable(Routes.Notification.routes) {
            Notification()
        }
        composable(Routes.Search.routes) {
            Search(navController)
        }
        composable(Routes.BottomNav.routes) {
            BottomNav(navController)

        }
        composable(Routes.AddThreads.routes) {
            AddThreads(navController)
        }
        composable(Routes.Login.routes) {
            Login(navController)

        }
        composable(Routes.Register.routes) {
            Register(navController)
        }
        composable(Routes.Profile.routes) {
            Profile(navController)
        }
        composable(Routes.OtherUsers.routes) {
            val data = it.arguments!!.getString("data")
            OtherUsers(navController,data!!)
        }
    }


}


