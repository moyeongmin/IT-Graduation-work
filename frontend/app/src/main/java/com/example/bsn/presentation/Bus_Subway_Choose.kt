package com.example.bsn.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.example.bsn.R
import com.example.bsn.presentation.Bus.Bus_main
import com.example.bsn.presentation.Subway.Subway_main
import com.example.bsn.presentation.ui.CustomImageButton
import com.example.bsn.presentation.ui.theme.BS_Choose_Theme
import com.example.bsn.presentation.ui.theme.BsnTheme

class Bus_Subway_Choose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BS_Choose_Theme {
                // A surface container using the 'background' color from the theme
                Surface(

                    modifier = Modifier.fillMaxSize(),
                    shape = CircleShape,
                    color = Color.Black
                ) {
                    BS_Choose_mainpage("Android")
                }
            }
        }
    }
}

@Composable
fun BS_Choose_mainpage(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Scaffold(
        containerColor = Color.Black,
        content = { paddingValues ->
            TimeText()
            ScalingLazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(top = 20.dp)
                    .fillMaxHeight()
                    .fillMaxWidth(1f),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 10.dp)
            ) {
                item{
                    Text(text = "사용하시려는 대중 교통을\n 선택해주세요",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.padding(bottom = 18.dp))
                }
                item{
                    CustomImageButton(//버스
                        defaultImagePainter = R.drawable.subway_btn_frame,
                        contentDescription = "optionsbtn",
                        onClick = {
                            val I = Intent(context,Bus_main::class.java)
                            context.startActivity(I)
                        },
                        widthratio = 0.9f,
                        heightratio = 4f,
                        text = "버스",
                        textsize = 14.sp,
                        modifier = Modifier,
                        align = Alignment.Center
                    )
                }

                item {
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                item{
                    CustomImageButton(//지하철
                        defaultImagePainter = R.drawable.subway_btn_frame,
                        contentDescription = "optionsbtn",
                        onClick = {
                            val i = Intent(context,Subway_main::class.java)
                            context.startActivity(i)
                             },
                        widthratio = 0.9f,
                        heightratio = 4f,
                        text = "지하철",
                        textsize = 14.sp,
                        modifier = Modifier,
                        align = Alignment.Center
                    )
                }
            }
        })

}

@Preview(showBackground = true, device = "spec:width=384px,height=600px,dpi=320,isRound=true")
@Composable
fun GreetingPreview2() {
    BsnTheme {
        BS_Choose_mainpage("Android")
    }
}