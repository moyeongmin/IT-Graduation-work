package com.example.bsn.presentation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.*
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
@Composable
fun TimeText() {
    val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    CurvedLayout(modifier = Modifier.height(20.dp)) {
        basicCurvedText(
            text = currentTime,
            style = CurvedTextStyle(
                fontSize = 12.sp,
                color = Color.LightGray,
                background = Color.Black
            )
        )
    }
}
@Composable
fun Timestamp(modifier: Modifier = Modifier) {
    var timeString by remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        while (true) {
            timeString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            delay(1000)
        }
    }

    Text(
        text = timeString,
        fontSize = 12.sp,
        color = Color.LightGray,
        modifier = modifier,
        textAlign = TextAlign.Center // 텍스트 정렬을 중앙으로 설정
    )
}

@Preview
@Composable
fun TimestampPreview() {
    Timestamp()
}

