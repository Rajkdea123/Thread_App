package com.example.myprojects.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myprojects.screens.AddThreads
import com.example.myprojects.screens.BottomNav
import com.example.myprojects.screens.Home
import com.example.myprojects.screens.Notification
import com.example.myprojects.screens.Search
import com.example.myprojects.screens.Splash

@Composable
fun NavGraph(navController: NavHostController){


    NavHost(navController= navController, startDestination = Routes.Splash.routes){


        composable(Routes.Splash.routes) {
            Splash(navController)
        }
        composable(Routes.Home.routes) {
            Home()
        }
        composable(Routes.Notification.routes) {
            Notification()
        }
        composable(Routes.Search.routes) {
            Search()
        }
        composable(Routes.BottomNav.routes) {
            BottomNav(navController)

        }
        composable(Routes.AddThreads.routes) {
            AddThreads()
        }
    }

}


