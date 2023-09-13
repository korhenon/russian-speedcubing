package com.example.russianspeedcubing.view.items.html

import android.widget.TextView
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

@Composable
fun Html(text: String, colorScheme: ColorScheme) {
    val uriHandler = LocalUriHandler.current
    AndroidView(factory = { context ->
        TextView(context).apply {
            linksClickable = true
            movementMethod = DefaultLinkMovementMethod() {
                uriHandler.openUri(it)
                true
            }
            setLinkTextColor(colorScheme.outlineVariant.toArgb())
            textSize = 16f
            setTextColor(colorScheme.onBackground.toArgb())
            setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT))
        }
    })
}