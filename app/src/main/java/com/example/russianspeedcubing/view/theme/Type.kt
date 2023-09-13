package com.example.russianspeedcubing.view.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.russianspeedcubing.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val robotoCondensed = FontFamily(
    Font(R.font.roboto_condensed_regular, FontWeight.Normal),
    Font(R.font.roboto_condensed_medium, FontWeight.Medium),
    Font(R.font.roboto_condensed_bold, FontWeight.Bold),
)

val robotoSlab = FontFamily(
    Font(R.font.roboto_slab_bold, FontWeight.Bold),
    Font(R.font.roboto_slab_regular, FontWeight.Normal),

)

val spaceMono = FontFamily(
    Font(R.font.space_mono_bold, FontWeight.Bold)
)