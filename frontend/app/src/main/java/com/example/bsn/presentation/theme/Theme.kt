package com.example.bsn.presentation.theme

import android.widget.Button
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp



@Composable
fun BsnTheme(
    content: @Composable () -> Unit
) {
    /**
     * Empty theme to customize for your app.
     * See: https://developer.android.com/jetpack/compose/designsystems/custom
     */


    MaterialTheme(
        typography  = Typography,
        shapes = Corner,
        content = content
        )
}

val Typography = Typography(

)

val Corner = Shapes(
    small = RoundedCornerShape(4.dp),
    medium =  RoundedCornerShape(8.dp),
    large =  RoundedCornerShape(12.dp)
)



