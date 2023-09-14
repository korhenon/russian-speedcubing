package com.example.russianspeedcubing.view.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.russianspeedcubing.R

@Composable
fun BackButton(navController: NavHostController, screen: String, size: Dp = 60.dp) {
    val interactionSource = remember { MutableInteractionSource() }
    Image(
        painter = painterResource(id = R.drawable.left_arrow),
        contentDescription = "back",
        modifier = Modifier
            .size(size)
            .clickable(interactionSource, null) {
                navController.navigate(screen)
            }
    )
}