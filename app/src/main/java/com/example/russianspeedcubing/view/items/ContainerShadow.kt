package com.example.russianspeedcubing.view.items

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.russianspeedcubing.model.application.Competition
import com.example.russianspeedcubing.view.navigation.Screens
import com.example.russianspeedcubing.view.screens.competition.CompetitionViewModel

@Composable
fun ContainerShadow(
    competition: Competition,
    navController: NavHostController,
    competitionViewModel: CompetitionViewModel,
    colorScheme: ColorScheme,
    hasSpacer: Boolean,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val padding by animateDpAsState(
        targetValue = if (isPressed) 0.dp else 3.dp, label = "",
        animationSpec = tween(durationMillis = 100)
    )
    Box(
        Modifier
            .background(colorScheme.outline, RoundedCornerShape(40.dp))
            .padding(bottom = padding)
            .border(1.dp, colorScheme.outline, RoundedCornerShape(40.dp))
            .clickable(interactionSource = interactionSource, null) {
                competitionViewModel.competition = competition
                competitionViewModel.isLoad = true
                navController.navigate(Screens.Competition)
            }
    ) {
        content()
    }
    if (hasSpacer) {
        Spacer(modifier = Modifier.height(3.dp - padding))
    }
}