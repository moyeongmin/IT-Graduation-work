package com.example.bsn.presentation.Bus

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bsn.R
import com.example.bsn.presentation.Bus.ui.theme.BsnTheme
import com.example.bsn.presentation.TimeText



class Bus_main : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BsnTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Bus_mainpage()
                }
            }
        }
    }
}

@Composable
fun Bus_mainpage(modifier: Modifier = Modifier) {
    val navController = rememberNavController() // navcontroller생성
    NavHost(navController = navController, startDestination = "station"){
        composable("station") { Bus_station(navController)}
        composable("select") { Bus_select(navController) }
        composable("ride") { Bus_ride(navController) }
        composable("drop") { Bus_drop(navController)}
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Bus_station(navController: NavController){
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Scaffold (
        containerColor = Color.Black,
        content = {
            TimeText()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "정류장",
                    color = Color(0xFFBDC1C6),
                    fontSize = 10.sp,
                    fontWeight = FontWeight(800),
                    textAlign = TextAlign.Center)
                SearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = { /* 검색 실행 로직 */ },
                    active = active,
                    onActiveChange = { active = it },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .aspectRatio(3.4f),
                    placeholder = { Text("검색어 입력") },
                    leadingIcon = { Icon(Icons.Filled.MoreVert, contentDescription = "검색") },
                    trailingIcon = { Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.mic_svg),
                        contentDescription = "음성 녹음", Modifier.clickable { /*녹음 로직*/}) },
                    content = {

                    }
//                    text = "정류장",
//                    color = Color(0xFFBDC1C6),
//                    textAlign = TextAlign.Center
                    )


            }

        }
    )
}
@Composable
fun Bus_select(navController: NavController){

}
@Composable
fun Bus_ride(navController: NavController)
{

}
@Composable
fun Bus_drop(navController: NavController)
{

}

@Preview(showBackground = true, device = "spec:width=384px,height=500px,dpi=320,isRound=true")
@Composable
fun GreetingPreview3() {
    BsnTheme {
        Bus_mainpage()
    }
}