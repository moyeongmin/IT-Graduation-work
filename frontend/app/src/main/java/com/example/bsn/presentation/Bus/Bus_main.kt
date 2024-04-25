package com.example.bsn.presentation.Bus

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    NavHost(navController = navController, startDestination = "bus_station"){
        composable("station") { Bus_station(navController)}
        composable("select") { Bus_select(navController) }
        composable("ride") { Bus_ride(navController) }
        composable("drop") { Bus_drop(navController)}
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Bus_station(navController: NavController){
    Scaffold (
        containerColor = Color.Black,
        content = {
            TimeText()
            Text(
                text = "정류장",
                color = Color(0xFFBDC1C6),

                )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    BsnTheme {
        Bus_mainpage()
    }
}