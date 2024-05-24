package com.example.bsn.presentation.Subway

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.example.bsn.R
import com.example.bsn.presentation.Is_Transfer
import com.example.bsn.presentation.Subway.ui.theme.BsnTheme
import com.example.bsn.presentation.TimeText
import com.example.bsn.presentation.ui.CustomImageButton

class Subway_main : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.example.bsn.presentation.Subway.ui.theme.BsnTheme {
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
    var ride_station : String = ""
    var drop_station : String = ""
    NavHost(navController = navController, startDestination = "line/{ride}"){
        composable("line/{ride}"){backstackEntry ->
            val ride = backstackEntry.arguments?.getString("ride") ?: ""
            if (ride != "") {ride_station = ride}
            Subway_Line(navController,ride) }
        composable("ride/{line}"){ backstackEntry ->
            val line = backstackEntry.arguments?.getString("line") ?: "1"
            Log.d("in main","line")
            Ride_select(navController = navController, line = line)
        }
        composable("drop/{line}/{ride}"){backstackEntry ->
            val line = backstackEntry.arguments?.getString("line") ?: "1"
            val ride = backstackEntry.arguments?.getString("ride") ?: ""
            Drop_select(navController = navController, line = line, ride)

        }


    }
}
@Composable
fun Subway_Line(navController: NavController, ride: String){
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
                            val line = "1"
                            when(ride){
                                "" ->navController.navigate("ride/$line")
                                else -> navController.navigate("drop/$line/$ride")
                            }
                        },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "1호선",
                        textsize = 12.sp
                    )
                }
                item {
                    CustomImageButton(//2호선
                        defaultImagePainter = R.drawable.subway_line_2,
                        contentDescription = "comebackhomebtn",
                        onClick = {
                            val line = "2"
                            when(ride){
                                "" ->navController.navigate("ride/$line")
                                else -> navController.navigate("drop/$line/$ride")
                            }},
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "2호선",
                        textsize = 12.sp
                    )
                }
                item {
                    CustomImageButton(//3호선
                        defaultImagePainter = R.drawable.subway_line_3,
                        contentDescription = "optionsbtn",
                        onClick = {
                            val line = "3"
                            when(ride){
                                "" ->navController.navigate("ride/$line")
                                else -> navController.navigate("drop/$line/$ride")
                            } },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "3호선",
                        textsize = 12.sp
                    )
                }
                item {
                    CustomImageButton(//4호선
                        defaultImagePainter = R.drawable.subway_line_4,
                        contentDescription = "optionsbtn",
                        onClick = { val line = "4"
                            when(ride){
                                "" ->navController.navigate("ride/$line")
                                else -> navController.navigate("drop/$line/$ride")
                            } },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "4호선",
                        textsize = 12.sp
                    )
                }
                item {
                    CustomImageButton(//경전철
                        defaultImagePainter = R.drawable.subway_line_5,
                        contentDescription = "optionsbtn",
                        onClick = {
                            val line = "부산김해경전철"
                            when(ride){
                                "" ->navController.navigate("ride/$line")
                                else -> navController.navigate("drop/$line/$ride")
                            } },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "부산김해경전철",
                        textsize = 12.sp
                    )
                }
                item {
                    CustomImageButton(//동해선
                        defaultImagePainter = R.drawable.subway_line_6,
                        contentDescription = "optionsbtn",
                        onClick = {
                            val line = "동해선"
                            when(ride){
                                "" ->navController.navigate("ride/$line")
                                else -> navController.navigate("drop/$line/$ride")
                            } },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "동해선",
                        textsize = 12.sp
                    )
                }
            }
        }
    )
}

@Composable
fun Ride_select(navController: NavController, line: String){

    //station으로 서버와 통신해 버스 리스트 받아오는 로직 추가
    //val subway_list = subway_list_search(station)
    var selectedsubway by remember { mutableStateOf<List<String>>(emptyList()) }
    Log.d("in Ride_select","$line")
    val subway_list = listOf("${line}호선","명륜역", "온천장역", "부산대역", "장전역")
    Box(
        modifier = Modifier
            .background(Color(0xFF000000))
    )
    {
        TimeText()
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "탑승 역 선택",
                color = Color(0xFFBDC1C6),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp , top = 18.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(subway_list) { subway ->
                    CustomImageButton(
                        defaultImagePainter = R.drawable.subway_btn_frame,
                        contentDescription = "subway_choose_btn",
                        widthratio = 0.8f,
                        heightratio = 4f,
                        onClick = {
                            val ride = subway
                            navController.navigate("line/$ride")
                                  },
                        text = subway ,
                        textsize = 14.sp
                    )

                }
            }
        }
    }
}


@Composable
fun Drop_select(navController: NavController, line: String, ride: String){

    //station으로 서버와 통신해 버스 리스트 받아오는 로직 추가
    //val subway_list = subway_list_search(station)
    val context = LocalContext.current
    var selectedsubway by remember { mutableStateOf<List<String>>(emptyList()) }
    Log.d("in Ride_select","$line")
    val subway_list = listOf("${line}호선","명륜역", "온천장역", "부산대역", "장전역")
    Box(
        modifier = Modifier
            .background(Color(0xFF000000))
    )
    {
        TimeText()
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "하차 역 선택",
                color = Color(0xFFBDC1C6),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp , top = 18.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(subway_list) { subway ->
                    CustomImageButton(
                        defaultImagePainter = R.drawable.subway_btn_frame,
                        contentDescription = "subway_choose_btn",
                        widthratio = 0.8f,
                        heightratio = 4f,
                        onClick = {
                            val drop = subway
                            //ride에 탑승역
                            //drop에 하차역
                            //서버에 탑승역과 하차역 전달
                            val i = Intent(context,Is_Transfer::class.java)
                            context.startActivity(i)
                        },
                        text = subway ,
                        textsize = 14.sp
                    )

                }
            }
        }
    }
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