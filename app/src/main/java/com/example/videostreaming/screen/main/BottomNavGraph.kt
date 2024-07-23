package com.example.videostreaming.screen.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.videostreaming.route.Route
import com.example.videostreaming.screen.favourite.FavouriteScreen
import com.example.videostreaming.screen.home.HomeScreen
import com.example.videostreaming.screen.profile.ProfileScreen
import com.example.videostreaming.screen.search.SearchScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.Home.name) {
        composable(Route.Home.name) {
            HomeScreen()
        }
        composable(Route.Favourites.name) {
            FavouriteScreen()
        }
        composable(Route.Search.name) {
            SearchScreen()
        }
        composable(Route.Profile.name) {
            ProfileScreen()
        }

    }
}