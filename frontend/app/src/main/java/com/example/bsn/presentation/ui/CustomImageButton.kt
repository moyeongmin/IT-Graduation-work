package com.example.bsn.presentation.ui

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun CustomImageButton(
    defaultImagePainter: Int,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    widthratio: Float = 1f,
    heightratio: Float = 1.5f,
    text : String?,
    textsize : TextUnit,
    key : Int
    ) {
    var isPressed by remember(key) { mutableStateOf(false) }
    val valpha = if (isPressed) 0.8f else 1.0f

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth(widthratio)
            .aspectRatio(heightratio)
            .clip(RoundedCornerShape(100.dp))
            .graphicsLayer {
                scaleX = if (isPressed) 0.85f else 0.9f
                scaleY = if (isPressed) 0.85f else 0.9f
                alpha = valpha
            }
    ) {
        Image(
            painter = painterResource(id = defaultImagePainter),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize() // 이미지를 Box 크기에 맞춥니다.
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            // 눌렸을 때의 상태를 true로 변경
                            isPressed = true
                            tryAwaitRelease()
                            // 뗐을 때의 상태를 false로 변경
                            isPressed = false
                        },
                        onTap = {
                            onClick()
                        }
                    )
                }
        )
        // Text가 null이 아닐 때만 Text 표시
        text?.let {
            BasicText(
                text = it,
                color = { Color.White },
                style = TextStyle(
                    fontSize = textsize,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.align(Alignment.CenterStart)
                    .padding(start = 5.dp)
            )
        }
    }
}
