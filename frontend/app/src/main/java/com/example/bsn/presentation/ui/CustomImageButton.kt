package com.example.bsn.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomImageButton(
    defaultImagePainter: Int,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    widthratio: Float = 1f,
    heightratio: Float = 1.5f,
    text: String?,
    textsize: TextUnit,
    key: Int = 0,
    titletext: String? = null,
    titletextsize: TextUnit = 14.sp,
    align: Alignment = Alignment.CenterStart
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

            if(titletext == null) {
                BasicText(
                    text = it,
                    color = { Color.White },
                    style = TextStyle(
                        fontSize = textsize,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(align)
                        .padding(start = 5.dp)
                )
            }
            else {
                Column(
                    modifier = Modifier.align(align).padding(top = 15.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                        )
                {
                    BasicText(
                        text = titletext,
                        color = {Color.White},
                        style = TextStyle(
                            fontSize = titletextsize,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(start = 5.dp, bottom = 10.dp)
                    )
                    BasicText(
                        text = text,
                        color = {Color.White},
                        style = TextStyle(
                            fontSize = textsize,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(start = 5.dp)
                    )

                }
            }
        }
    }
}
