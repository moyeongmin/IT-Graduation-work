package com.example.bsn.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.example.bsn.presentation.ui.CustomImageButton
import com.example.bsn.presentation.ui.theme.BS_Choose_Theme
import com.example.bsn.presentation.ui.theme.BsnTheme
import java.time.format.TextStyle

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
    Scaffold(
        containerColor = Color.Black,
        content = { paddingValues ->
            TimeText()
            ScalingLazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(top = 30.dp)
                    .fillMaxHeight(0.9f)
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
                    Button(

                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .aspectRatio(3.2f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF87CEEB),
                            disabledContainerColor = Color(0xCC000000),
                            disabledContentColor =Color.Transparent
                        ),
                        content = {
                                  Text(text = "버스",
                                      textAlign = TextAlign.Center,
                                      fontSize = 14.sp,
                                      color = Color.Black
                                  )
                        },
                        onClick = { /*TODO*/ })

                }
                item {
                    Spacer(modifier = Modifier.padding(10.dp))
                }
                item{
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .aspectRatio(3.1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF87CEEB),
                            disabledContainerColor = Color(0xCC000000),
                            disabledContentColor =Color.Transparent
                        ),
                        content = {
                            Text(text = "지하철",
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        },
                        onClick = { /*TODO*/ })

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