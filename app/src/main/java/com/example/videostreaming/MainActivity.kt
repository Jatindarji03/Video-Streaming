package com.example.videostreaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videostreaming.route.Route
import com.example.videostreaming.screen.displaycontent.DisplayContentScreen
import com.example.videostreaming.screen.favourite.FavouriteScreen
import com.example.videostreaming.screen.home.HomeScreen

import com.example.videostreaming.screen.login.LoginScreen
import com.example.videostreaming.screen.main.MainScreen
import com.example.videostreaming.screen.profile.ProfileScreen
import com.example.videostreaming.screen.search.SearchScreen
import com.example.videostreaming.screen.signup.SignupScreen
import com.example.videostreaming.ui.theme.VideoStreamingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoStreamingTheme {
                VideoStreamingApp()
            }
        }
    }
}

@Composable
fun VideoStreamingApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Main.name) {
        composable(Route.Login.name) {
            LoginScreen(navController)
        }
        composable(Route.Signup.name) {
            SignupScreen(navController)
        }
        composable(Route.Main.name){
            MainScreen()
        }

    }
}



