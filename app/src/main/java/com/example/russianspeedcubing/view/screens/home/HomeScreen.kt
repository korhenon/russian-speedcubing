package com.example.russianspeedcubing.view.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.russianspeedcubing.R
import com.example.russianspeedcubing.model.application.Competition
import com.example.russianspeedcubing.view.items.ContainerShadow
import com.example.russianspeedcubing.view.navigation.Screens
import com.example.russianspeedcubing.view.screens.competition.CompetitionViewModel
import com.example.russianspeedcubing.view.theme.robotoCondensed
import com.example.russianspeedcubing.view.theme.robotoSlab
import com.example.russianspeedcubing.view.theme.spaceMono

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel,
    competitionViewModel: CompetitionViewModel,
    colorScheme: ColorScheme
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isLoad,
        onRefresh = { viewModel.loadCompetitions() })
    val menuInteractionSource = remember { MutableInteractionSource() }
    Box(
        Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Spacer(Modifier.height(36.dp))
            Row(
                Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Соревнования",
                    fontFamily = robotoSlab,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground
                )

                Image(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = "menu",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable(menuInteractionSource, null) {
                            navController.navigate(Screens.Preferences)
                        })
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Сейчас проходят
            if (viewModel.competitions.current.isNotEmpty()) {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "Сейчас проходят",
                        fontFamily = robotoSlab,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    for ((i, competition) in viewModel.competitions.current.withIndex()) {
                        CurrentCompetition(
                            competition,
                            i,
                            navController,
                            colorScheme,
                            competitionViewModel
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }

            // Предстоящие соревнования
            if (viewModel.competitions.upcoming.isNotEmpty()) {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "Предстоящие",
                        fontFamily = robotoSlab,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Row(
                    Modifier
                        .horizontalScroll(rememberScrollState())
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth(),
                ) {
                    Spacer(Modifier.width(20.dp))
                    for ((i, competition) in viewModel.competitions.upcoming.withIndex()) {
                        UpcomingCompetition(
                            competition,
                            i,
                            navController,
                            colorScheme,
                            competitionViewModel
                        )
                        Spacer(Modifier.width(20.dp))
                    }
                }
            }

            // Прошедшие соревнования
            Column(Modifier.padding(horizontal = 20.dp)) {
                Spacer(Modifier.height(32.dp))
                Text(
                    text = "Прошедшие",
                    fontFamily = robotoSlab,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground
                )
                Spacer(Modifier.height(16.dp))
                for (competition in viewModel.competitions.past) {
                    PastCompetition(competition, navController, colorScheme, competitionViewModel)
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }
        }
        PullRefreshIndicator(
            refreshing = viewModel.isLoad,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun CurrentCompetition(
    competition: Competition,
    number: Int,
    navController: NavHostController,
    colorScheme: ColorScheme,
    competitionViewModel: CompetitionViewModel
) {
    ContainerShadow(
        competition,
        navController,
        competitionViewModel,
        colorScheme,
        true
    ) {
        Column(
            modifier = Modifier
                .background(colorScheme.primaryContainer, RoundedCornerShape(40.dp))
                .padding(vertical = 20.dp, horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "#0${competition.dayNumber}",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = spaceMono
                )
                Text(
                    text = competition.name,
                    fontSize = 20.sp,
                    fontFamily = robotoSlab,
                    style = TextStyle(
                        background = when (number.mod(3)) {
                            0 -> colorScheme.primary
                            1 -> colorScheme.tertiary
                            else -> colorScheme.secondary
                        }
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colorScheme.outline, RoundedCornerShape(0.5.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Результаты",
                    fontFamily = robotoCondensed,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.light_arrow_right),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun UpcomingCompetition(
    competition: Competition,
    number: Int,
    navController: NavHostController,
    colorScheme: ColorScheme,
    competitionViewModel: CompetitionViewModel
) {
    ContainerShadow(
        competition,
        navController,
        competitionViewModel,
        colorScheme,
        false
    ) {
        Column(
            modifier = Modifier
                .background(colorScheme.primaryContainer, RoundedCornerShape(40.dp))
                .padding(vertical = 20.dp, horizontal = 32.dp)
                .widthIn(min = 250.dp)
        ) {
            Text(
                text = competition.name,
                fontSize = 20.sp,
                fontFamily = robotoCondensed,
                fontWeight = FontWeight.Medium,
                style = TextStyle(
                    background = when (number.mod(3)) {
                        0 -> colorScheme.primary
                        1 -> colorScheme.tertiary
                        else -> colorScheme.secondary
                    }
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.top_right_arrow),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(32.dp))
                Text(
                    text = buildAnnotatedString {
                        append(competition.dates + "\n")
                        append(competition.month + "\n")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(competition.year)
                        }
                    },
                    fontSize = 20.sp,
                    fontFamily = robotoCondensed,
                )
            }
        }
    }
}

@Composable
fun PastCompetition(
    competition: Competition,
    navController: NavHostController,
    colorScheme: ColorScheme,
    competitionViewModel: CompetitionViewModel
) {
    ContainerShadow(
        competition,
        navController,
        competitionViewModel,
        colorScheme,
        true
    ) {
        Row(
            modifier = Modifier
                .background(colorScheme.primaryContainer, RoundedCornerShape(40.dp))
                .padding(24.dp, 20.dp, 16.dp, 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = competition.name,
                fontSize = 20.sp,
                fontFamily = robotoCondensed,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(0.6f)
            )
            Image(
                painter = painterResource(id = R.drawable.top_right_arrow),
                contentDescription = ""
            )
        }
    }
}

