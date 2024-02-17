package com.example.russianspeedcubing.view.screens.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.russianspeedcubing.view.items.BackButton
import com.example.russianspeedcubing.view.navigation.Screens
import com.example.russianspeedcubing.view.theme.robotoCondensed

@Composable
fun PreferencesScreen(
    navController: NavHostController,
    colorScheme: ColorScheme,
    viewModel: PreferencesViewModel = hiltViewModel()
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Настройки",
                fontSize = 32.sp,
                fontFamily = robotoCondensed,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.7f),
                style = TextStyle(background = colorScheme.primary)
            )
            BackButton(navController, Screens.Home)
        }
        Spacer(modifier = Modifier.height(28.dp))
        Text(text = "Аккаунт", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Box(
                Modifier
                    .size(64.dp)
                    .background(colorScheme.primaryContainer, CircleShape)
                    .border(1.dp, colorScheme.outline, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.hasCompetitor) {
                    Text(
                        text = viewModel.fcId.take(2),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "?",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = if (!viewModel.hasCompetitor) "Давайте познакомимся!"
                    else viewModel.name,
                    fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (!viewModel.hasCompetitor) "Добавьте свой FC ID, чтобы улучшить свой опыт использования данного приложения."
                    else "Ваш FC ID: ${viewModel.fcId}",
                    fontSize = 12.sp,
                    color = colorScheme.surface,
                    lineHeight = 12.sp
                )
            }
        }
        Button(
            onClick = { navController.navigate(Screens.SelectFcId) },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorScheme.secondaryContainer),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = if (!viewModel.hasCompetitor) "Добавить FC ID" else "Изменить FC ID",
                color = colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Medium
            )
        }
    }
}