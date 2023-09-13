package com.example.russianspeedcubing.view.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russianspeedcubing.R
import com.example.russianspeedcubing.view.theme.LinkColor

@Composable
fun DescriptionText(label: String, text: String) {
    Text(text = buildAnnotatedString {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$label: ")
        }
        append(text)
    }, fontSize = 16.sp)
}

@Composable
fun DescriptionLink(label: String, url: String) {
    val uriHandler = LocalUriHandler.current
    val interactionSource = remember { MutableInteractionSource() }
    Row {
        Text(text = "$label: ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(
            text = url.replace("http://", "").replace("https://", ""),
            fontSize = 16.sp,
            color = LinkColor,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(interactionSource, null) {
                uriHandler.openUri(url)
            }
        )
    }
}

@Composable
fun DescriptionClickableItem(label: String, text: String = "", onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(if (text == "") label else "$label: ")
            }
            append(text)
        }, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Image(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = null,
            modifier = Modifier.clickable(interactionSource, null) { onClick() }
        )
    }
}