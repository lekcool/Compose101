package com.lkdev.compose101.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = TextBody
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp,
        color = TextTitle
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val TextPrice = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    color = RedColor
)

val TextNew = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    color = RedColor
)

val TextNew2 = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    color = RedColor
)