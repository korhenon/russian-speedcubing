package com.example.russianspeedcubing.view.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.russianspeedcubing.view.screens.competition.CompetitionScreen
import com.example.russianspeedcubing.view.screens.competition.CompetitionViewModel
import com.example.russianspeedcubing.view.screens.events.EventsScreen
import com.example.russianspeedcubing.view.screens.events.EventsViewModel
import com.example.russianspeedcubing.view.screens.home.HomeScreen
import com.example.russianspeedcubing.view.screens.home.HomeViewModel
import com.example.russianspeedcubing.view.screens.preferences.PreferencesScreen
import com.example.russianspeedcubing.view.screens.select_fc_id.SelectFcIdScreen

@Composable
fun NavGraph(
    hasDefaultSplash: Boolean,
    homeViewModel: HomeViewModel,
    competitionViewModel: CompetitionViewModel = hiltViewModel(),
    eventsViewModel: EventsViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val colorScheme = MaterialTheme.colorScheme

    NavHost(navController = navController, startDestination = Screens.Home) {
        composable(Screens.Home) {
            HomeScreen(navController, homeViewModel, competitionViewModel, colorScheme)
        }
        composable(Screens.Competition) {
            CompetitionScreen(navController, colorScheme, competitionViewModel, eventsViewModel)
        }
        composable(Screens.Events) {
            EventsScreen(navController, colorScheme, eventsViewModel)
        }
        composable(Screens.Preferences) {
            PreferencesScreen(navController, colorScheme)
        }
        composable(Screens.SelectFcId) {
            SelectFcIdScreen(navController, colorScheme)
        }
    }
}