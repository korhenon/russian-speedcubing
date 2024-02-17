package com.example.russianspeedcubing.view.screens.events

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.russianspeedcubing.R
import com.example.russianspeedcubing.data.constants.formatAsTime
import com.example.russianspeedcubing.data.constants.maxSize
import com.example.russianspeedcubing.view.items.BackButton
import com.example.russianspeedcubing.view.navigation.Screens
import com.example.russianspeedcubing.view.theme.robotoCondensed

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventsScreen(
    navController: NavHostController,
    colorScheme: ColorScheme,
    viewModel: EventsViewModel
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isLoadResults && viewModel.selectedRoundId >= 0,
        onRefresh = { viewModel.loadResults() }
    )
    viewModel.loadEvents()
    if (!viewModel.isLoadEvents) {
        if (!viewModel.isFirstResultLoaded) {
            viewModel.loadResults()
        }
        Row(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .width(72.dp)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = viewModel.selectedEvent.iconSelected),
                    contentDescription = "selected",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(1.dp)
                        .background(colorScheme.surface, RoundedCornerShape(0.5.dp))
                )
                Spacer(modifier = Modifier.height(12.dp))
                for ((i, event) in viewModel.notSelectedEvents.withIndex()) {
                    val interactionSource = remember { MutableInteractionSource() }
                    Image(
                        painter = painterResource(id = event.icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable(interactionSource, null) {
                                viewModel.selectedRoundId = 0
                                viewModel.selectedEventId =
                                    if (i >= viewModel.selectedEventId) i + 1 else i
                                viewModel.loadResults()
                            },
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .pullRefresh(pullRefreshState)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = viewModel.selectedEvent.rounds[0].name,
                            fontSize = 30.sp,
                            fontFamily = robotoCondensed,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(0.6f)
                        )
                        BackButton(
                            navController = navController,
                            screen = Screens.Competition,
                            40.dp
                        )
                    }
                    Spacer(modifier = Modifier.height(if (viewModel.selectedEvent.id == "333mbf") 12.dp else 4.dp))
                    Row(
                        Modifier.horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (viewModel.selectedEvent.id != "333mbf") {
                            val bgColor by animateColorAsState(
                                targetValue = if (-1 == viewModel.selectedRoundId) colorScheme.primary
                                else colorScheme.inversePrimary,
                                label = "",
                                animationSpec = tween(durationMillis = 250)
                            )
                            val interactionSource = remember { MutableInteractionSource() }
                            Image(
                                painter = painterResource(id = R.drawable.psychsheet),
                                contentDescription = "psychsheet",
                                modifier = Modifier
                                    .background(bgColor, RoundedCornerShape(2.dp))
                                    .padding(2.dp)
                                    .clickable(
                                        interactionSource,
                                        null
                                    ) {
                                        viewModel.selectedRoundId = -1
                                        viewModel.getPsychSheetForSelectedEvent()
                                    }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                        for ((i, round) in viewModel.selectedEvent.rounds.withIndex()) {
                            val bgColor by animateColorAsState(
                                targetValue = if (i == viewModel.selectedRoundId) colorScheme.primary
                                else colorScheme.inversePrimary,
                                label = "",
                                animationSpec = tween(durationMillis = 250)
                            )
                            val interactionSource = remember { MutableInteractionSource() }
                            Text(
                                text = if (round.round.`this` == round.round.total) "Финал"
                                else "Раунд ${round.round.`this`}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .background(bgColor, RoundedCornerShape(2.dp))
                                    .padding(horizontal = 2.dp)
                                    .clickable(interactionSource, null) {
                                        viewModel.selectedRoundId = i
                                        viewModel.loadResults()
                                    }
                            )
                            Spacer(modifier = Modifier.width(16.dp))

                        }
                    }
                    if (viewModel.selectedRoundId >= 0) {
                        Spacer(modifier = Modifier.height(16.dp))
                        if (viewModel.selectedRound.time_limit != null) {
                            Text(
                                text = "Лимит: ${viewModel.selectedRound.time_limit}",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        if (viewModel.selectedRound.time_limit_cumulative != null) {
                            Text(
                                text = "Лимит (Суммарно): ${viewModel.selectedRound.time_limit_cumulative}",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        if (viewModel.selectedRound.cutoff != null) {
                            Text(
                                text = "Катофф: ${viewModel.selectedRound.cutoff}",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        Text(
                            text = "Формат: ${viewModel.selectedRound.short_format()}",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (viewModel.selectedRound.advance_to_next_round != null) {
                            Text(
                                text = "Дальше: ${viewModel.selectedRound.advance_to_next_round!!.value}${if (viewModel.selectedRound.advance_to_next_round!!.is_percent) "%" else ""}",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        if (viewModel.selectedRoundResults.isNotEmpty()) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState())
                            ) {
                                Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                                    Text(
                                        text = "№",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = robotoCondensed,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    for ((i, result) in viewModel.selectedRoundResults.withIndex()) {
                                        Text(
                                            text = (i + 1).toString(),
                                            textAlign = TextAlign.Center,
                                            fontFamily = robotoCondensed,
                                            fontSize = 12.sp,
                                            modifier = Modifier
                                                .background(
                                                    if (viewModel.savedCompetitorId == result.fc_id) colorScheme.secondary
                                                    else Color.Transparent
                                                )
                                                .fillMaxWidth()
                                                .padding(top = 2.dp, bottom = 2.dp, end = 4.dp)
                                        )
                                    }
                                }
                                Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                                    Text(
                                        text = "Имя",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = robotoCondensed,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    for (result in viewModel.selectedRoundResults) {
                                        Text(
                                            text = result.name.maxSize(21),
                                            fontFamily = robotoCondensed,
                                            fontSize = 12.sp,
                                            modifier = Modifier
                                                .background(
                                                    if (viewModel.savedCompetitorId == result.fc_id) colorScheme.secondary
                                                    else Color.Transparent
                                                )
                                                .fillMaxWidth()
                                                .padding(top = 2.dp, bottom = 2.dp, end = 12.dp)
                                        )
                                    }
                                }
                                if (viewModel.selectedEvent.id != "333mbf") {
                                    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                                        Text(
                                            text = "Среднее",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium,
                                            fontFamily = robotoCondensed,
                                            textAlign = TextAlign.End,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(end = 12.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        for (result in viewModel.selectedRoundResults) {
                                            Text(
                                                text = result.average.formatAsTime(),
                                                textAlign = TextAlign.End,
                                                fontFamily = robotoCondensed,
                                                fontSize = 12.sp,
                                                modifier = Modifier
                                                    .background(
                                                        if (viewModel.savedCompetitorId == result.fc_id) colorScheme.secondary
                                                        else Color.Transparent
                                                    )
                                                    .fillMaxWidth()
                                                    .padding(top = 2.dp, bottom = 2.dp, end = 12.dp)
                                            )
                                        }
                                    }
                                }
                                Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                                    Text(
                                        text = "Лучшая",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = robotoCondensed,
                                        textAlign = TextAlign.End,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(end = 2.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    for (result in viewModel.selectedRoundResults) {
                                        Text(
                                            text = result.best.formatAsTime(),
                                            textAlign = TextAlign.End,
                                            fontFamily = robotoCondensed,
                                            fontSize = 12.sp,
                                            modifier = Modifier
                                                .background(
                                                    if (viewModel.savedCompetitorId == result.fc_id) colorScheme.secondary
                                                    else Color.Transparent
                                                )
                                                .fillMaxWidth()
                                                .padding(top = 2.dp, bottom = 2.dp, end = 2.dp)
                                        )
                                    }
                                }
                            }
                        }

                    } else {
                        Spacer(modifier = Modifier.height(16.dp))
                        if (viewModel.isLoadPsychsheet) {
                            Text(text = "Загрузка Psychsheet..")
                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                        ) {
                            Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                                Text(
                                    text = "№",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = robotoCondensed,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                for ((i, psych) in viewModel.selectedEvent.psychSheet.withIndex()) {
                                    Text(
                                        text = (i + 1).toString(),
                                        textAlign = TextAlign.Center,
                                        fontFamily = robotoCondensed,
                                        fontSize = 12.sp,
                                        modifier = Modifier
                                            .background(
                                                if (viewModel.savedCompetitorId == psych.fc_id) colorScheme.secondary
                                                else Color.Transparent
                                            )
                                            .fillMaxWidth()
                                            .padding(top = 2.dp, bottom = 2.dp, end = 4.dp)
                                    )
                                }
                            }
                            Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                                Text(
                                    text = "Имя",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = robotoCondensed,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                for (psych in viewModel.selectedEvent.psychSheet) {
                                    Text(
                                        text = psych.name.maxSize(21),
                                        fontFamily = robotoCondensed,
                                        fontSize = 12.sp,
                                        modifier = Modifier
                                            .background(
                                                if (viewModel.savedCompetitorId == psych.fc_id) colorScheme.secondary
                                                else Color.Transparent
                                            )
                                            .fillMaxWidth()
                                            .padding(top = 2.dp, bottom = 2.dp, end = 12.dp)
                                    )
                                }
                            }
                            Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                                Text(
                                    text = "Среднее",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = robotoCondensed,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 12.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                for (psych in viewModel.selectedEvent.psychSheet) {
                                    Text(
                                        text = psych.average.formatAsTime(),
                                        textAlign = TextAlign.End,
                                        fontFamily = robotoCondensed,
                                        fontSize = 12.sp,
                                        modifier = Modifier
                                            .background(
                                                if (viewModel.savedCompetitorId == psych.fc_id) colorScheme.secondary
                                                else Color.Transparent
                                            )
                                            .fillMaxWidth()
                                            .padding(top = 2.dp, bottom = 2.dp, end = 12.dp)
                                    )
                                }
                            }
                            Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                                Text(
                                    text = "Лучшая",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = robotoCondensed,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 2.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                for (psych in viewModel.selectedEvent.psychSheet) {
                                    Text(
                                        text = psych.single.formatAsTime(),
                                        textAlign = TextAlign.End,
                                        fontFamily = robotoCondensed,
                                        fontSize = 12.sp,
                                        modifier = Modifier
                                            .background(
                                                if (viewModel.savedCompetitorId == psych.fc_id) colorScheme.secondary
                                                else Color.Transparent
                                            )
                                            .fillMaxWidth()
                                            .padding(top = 2.dp, bottom = 2.dp, end = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                PullRefreshIndicator(
                    refreshing = viewModel.isLoadResults && viewModel.selectedRoundId >= 0,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}
