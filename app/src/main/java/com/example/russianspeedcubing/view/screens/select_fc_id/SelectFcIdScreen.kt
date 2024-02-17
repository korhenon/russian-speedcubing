package com.example.russianspeedcubing.view.screens.select_fc_id

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.russianspeedcubing.model.network.Competitor
import com.example.russianspeedcubing.view.items.BackButton
import com.example.russianspeedcubing.view.navigation.Screens
import com.example.russianspeedcubing.view.theme.robotoCondensed


@Composable
fun SelectFcIdScreen(
    navController: NavHostController,
    colorScheme: ColorScheme,
    viewModel: SelectFcIdViewModel = hiltViewModel()
) {
    if (viewModel.isFirstLoad) {
        viewModel.load()
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Добавление\nFC ID",
                fontSize = 32.sp,
                fontFamily = robotoCondensed,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.7f),
                style = TextStyle(background = colorScheme.primary)
            )
            BackButton(navController, Screens.Preferences)
        }
        Spacer(modifier = Modifier.height(64.dp))
        TextField(
            value = viewModel.search,
            onValueChange = { viewModel.search = it },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorScheme.background,
                cursorColor = colorScheme.surface,
                focusedIndicatorColor = colorScheme.secondary
            ),
            placeholder = { Text(text = "Начните вводить...") }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            for (competitor in viewModel.filteredCompetitors) {
                CompetitorCard(competitor, colorScheme, viewModel, navController)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
    if (viewModel.isLoad) Box(
        Modifier
            .fillMaxSize()
            .background(colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            CircularProgressIndicator(color = colorScheme.primary)
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Идёт загрузка всех участников соревнований, это может занять некоторое время.",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CompetitorCard(
    competitor: Competitor,
    colorScheme: ColorScheme,
    viewModel: SelectFcIdViewModel,
    navController: NavHostController
) {
    var isOpen by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Max)
    ) {
        Text(
            text = competitor.name,
            style = TextStyle(
                background = if (isOpen) colorScheme.secondary
                else colorScheme.onSecondary
            ),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable(interactionSource, null) {
                isOpen = !isOpen
            }
        )
        if (isOpen) {
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("FC ID:\n")
                        }
                        append(competitor.fc_id)
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(48.dp))
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("WCA ID:\n")
                        }
                        append(
                            if (competitor.wca_id == null || competitor.wca_id == "false") "Нет"
                            else competitor.wca_id
                        )
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.save(competitor)
                    navController.navigate(Screens.Preferences)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorScheme.secondaryContainer),
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Выбрать",
                    color = colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colorScheme.surface, CircleShape)
            )
        }
    }


}