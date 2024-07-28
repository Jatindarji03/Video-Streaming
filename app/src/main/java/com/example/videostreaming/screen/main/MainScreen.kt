package com.example.videostreaming.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.videostreaming.route.Route
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.Blue
import com.example.videostreaming.ui.theme.DarkGray
import com.example.videostreaming.ui.theme.Gray
import com.example.videostreaming.ui.theme.VideoStreamingTheme

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        containerColor = Black,
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        // Add content here and respect the innerPadding
        Box(modifier = Modifier.padding(innerPadding)) {
            // Your screen content goes here
            BottomNavGraph(navController)
        }

    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavigationItem(
            "Home",
            Route.Home.name,
            Icons.Filled.Home,
            Icons.Outlined.Home
        ),
        BottomNavigationItem(
            "Favourites",
            Route.Favourites.name,
            Icons.Filled.Favorite,
            Icons.Outlined.FavoriteBorder
        ),
        BottomNavigationItem(
            "Search",
            Route.Search.name,
            Icons.Filled.Search,
            Icons.Outlined.Search
        ),
        BottomNavigationItem(
            "Profile",
            Route.Profile.name,
            Icons.Filled.Person,
            Icons.Outlined.Person
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(containerColor = Black) {
        items.forEach { item ->
            AddItem(
                item = item,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }


}

@Composable
fun RowScope.AddItem(
    item: BottomNavigationItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelLarge
            )
        },
        icon = {
            Icon(
                imageVector = if (currentDestination?.hierarchy?.any { it.route == item.route } == true) item.selectedIcon else item.unselectedIcon,
                contentDescription = item.title
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == item.route
        } == true,
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedTextColor = Blue,
            unselectedTextColor = Gray,
            selectedIconColor = Blue,
            unselectedIconColor = DarkGray,
            indicatorColor = DarkGray
        )
    )

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MainScreenPreview() {
    VideoStreamingTheme {
        MainScreen()
    }
}