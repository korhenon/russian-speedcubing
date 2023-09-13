package com.example.russianspeedcubing.view

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.russianspeedcubing.view.navigation.NavGraph
import com.example.russianspeedcubing.view.screens.home.HomeViewModel
import com.example.russianspeedcubing.view.theme.RussianSpeedcubingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hasDefaultSplash = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
        if (hasDefaultSplash) {
            viewModel.loadCompetitions()
        }
        setContent {
            RussianSpeedcubingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(hasDefaultSplash, viewModel)
                }
            }
        }
    }
}