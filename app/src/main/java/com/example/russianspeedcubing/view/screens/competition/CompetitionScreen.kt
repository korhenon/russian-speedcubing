package com.example.russianspeedcubing.view.screens.competition

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.russianspeedcubing.view.items.BackButton
import com.example.russianspeedcubing.view.items.DescriptionClickableItem
import com.example.russianspeedcubing.view.items.DescriptionLink
import com.example.russianspeedcubing.view.items.DescriptionText
import com.example.russianspeedcubing.view.items.html.Html
import com.example.russianspeedcubing.view.navigation.Screens
import com.example.russianspeedcubing.view.screens.events.EventsViewModel
import com.example.russianspeedcubing.view.theme.robotoCondensed

@Composable
fun CompetitionScreen(
    navController: NavHostController,
    colorScheme: ColorScheme,
    viewModel: CompetitionViewModel,
    eventsViewModel: EventsViewModel
) {
    viewModel.loadDescription()
    val competition = viewModel.competition
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
                text = competition.name,
                fontSize = 32.sp,
                fontFamily = robotoCondensed,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.7f),
                style = TextStyle(background = colorScheme.primary)
            )
            BackButton(navController, Screens.Home)
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (competition.logo != "") {
            Image(
                painter = rememberAsyncImagePainter(competition.logo),
                contentDescription = "logo",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(152.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        DescriptionText(label = "Город", text = competition.city)
        Spacer(modifier = Modifier.height(12.dp))

        DescriptionText(
            label = "Дата",
            text = "${competition.dates} ${competition.month.lowercase()}, ${competition.year} г."
        )
        Spacer(modifier = Modifier.height(12.dp))


        DescriptionLink(label = "Сайт", url = competition.site)
        Spacer(modifier = Modifier.height(12.dp))

        DescriptionText(label = "Организатор", text = competition.organizers)
        Spacer(modifier = Modifier.height(12.dp))

        DescriptionClickableItem(label = "Участники", text = competition.competitorsCount) {

        }
        Spacer(modifier = Modifier.height(12.dp))

        DescriptionText(label = "Делегат", text = competition.delegates)
        Spacer(modifier = Modifier.height(12.dp))

        DescriptionClickableItem(label = "Дисциплины") {
            eventsViewModel.setup(competition.id)
            navController.navigate(Screens.Events)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Описание:",
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = robotoCondensed
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (viewModel.isLoad) {
            Text(text = "Загрузка...")
        }
        else {
            Html(text = viewModel.descriptionHtml, colorScheme)
        }
    }
}

