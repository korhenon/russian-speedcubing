package com.example.russianspeedcubing.view.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.russianspeedcubing.view.screens.home.HomeScreen
import com.example.russianspeedcubing.view.screens.home.HomeViewModel

@Composable
fun NavGraph(hasDefaultSplash: Boolean, homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    val colorScheme = MaterialTheme.colorScheme

    NavHost(navController = navController, startDestination = Screens.Home) {
        composable(Screens.Home) {
            HomeScreen(navController, homeViewModel, colorScheme)
        }
    }
}