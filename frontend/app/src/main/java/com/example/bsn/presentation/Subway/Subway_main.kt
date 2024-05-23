package com.example.bsn.presentation.Subway

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.example.bsn.R
import com.example.bsn.presentation.Subway.ui.theme.BsnTheme
import com.example.bsn.presentation.TimeText
import com.example.bsn.presentation.ui.CustomImageButton

class Subway_main : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.example.bsn.presentation.Bus.ui.theme.BsnTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Subway_mainpage()
                }
            }
        }
    }
}
@Composable
fun Subway_mainpage(){
    val navController = rememberNavController() // navcontroller생성
    NavHost(navController = navController, startDestination = "line"){
        composable("line"){ Subway_Line() }


    }
}
@Composable
fun Subway_Line(){
    val context = LocalContext.current
    Scaffold(
        containerColor = Color.Black,
        content = { paddingValues ->
            TimeText()
            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.95f)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 10.dp)
            ) {
                item {

                    Text(text = "호선 선택",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Spacer(modifier = Modifier.padding(bottom = 30.dp))
                }
                item {
                    CustomImageButton(//1호선 버튼
                        defaultImagePainter = R.drawable.subway_line_1,
                        contentDescription = "goroworkbtn",
                        onClick = {

                        },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "1호선",
                        textsize = 12.sp,
                        key = 1
                    )
                }
                item {
                    CustomImageButton(//2호선
                        defaultImagePainter = R.drawable.subway_line_2,
                        contentDescription = "comebackhomebtn",
                        onClick = { /*TODO*/ },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "2호선",
                        textsize = 12.sp,
                        key = 2
                    )
                }
                item {
                    CustomImageButton(//3호선
                        defaultImagePainter = R.drawable.subway_line_3,
                        contentDescription = "optionsbtn",
                        onClick = { /*TODO*/ },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "3호선",
                        textsize = 12.sp,
                        key = 3
                    )
                }
                item {
                    CustomImageButton(//3호선
                        defaultImagePainter = R.drawable.subway_line_4,
                        contentDescription = "optionsbtn",
                        onClick = { /*TODO*/ },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "4호선",
                        textsize = 12.sp,
                        key = 3
                    )
                }
                item {
                    CustomImageButton(//3호선
                        defaultImagePainter = R.drawable.subway_line_5,
                        contentDescription = "optionsbtn",
                        onClick = { /*TODO*/ },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "부산김해경전철",
                        textsize = 12.sp,
                        key = 3
                    )
                }
                item {
                    CustomImageButton(//3호선
                        defaultImagePainter = R.drawable.subway_line_6,
                        contentDescription = "optionsbtn",
                        onClick = { /*TODO*/ },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "동해선",
                        textsize = 12.sp,
                        key = 3
                    )
                }
            }
        }
    )
}
@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    BsnTheme {
        Greeting3("Android")
    }
}