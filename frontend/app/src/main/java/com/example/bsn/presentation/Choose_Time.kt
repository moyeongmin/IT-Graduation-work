package com.example.bsn.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bsn.presentation.ui.theme.BsnTheme


class Choose_Time : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BsnTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChooseTime_mainpage()
                }
            }
        }
    }
}

@Composable
fun ChooseTime_mainpage(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "text"){
        composable("text"){ Announce_page(navController = navController)}
        composable("timepickers"){ TimePicker(navController)}
    }

}
@Composable
fun Announce_page(navController : NavController ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "출근 시간을 선택해주세요!",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = { navController.navigate("timepickers") },
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(Color(0xFF8EBBFF))
        ) {
            Text(text = "다음", color = Color.White)
        }

    }
}
@Composable
fun TimePicker(navController: NavController) {
    var hour by remember { mutableStateOf(0) }
    var minute by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hour",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            NumberPicker(
                value = hour,
                onValueChange = { hour = it },
                range = 0..23
            )
            Text(
                text = ":",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            NumberPicker(
                value = minute,
                onValueChange = { minute = it },
                range = 0..59
            )
        }
        Button(
            onClick = { navController.navigate("nextScreen") },
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(Color(0xFF8EBBFF))
        ) {
            Text(text = "완료", color = Color.White)
        }
    }
}

@Composable
fun NumberPicker(value: Int, onValueChange: (Int) -> Unit, range: IntRange) {
    var selectedValue by remember { mutableStateOf(value) }
    Box(
        modifier = Modifier
            .size(60.dp, 120.dp)
            .background(Color.Gray, CircleShape)
            .clickable {
                selectedValue = (selectedValue + 1) % (range.last + 1)
                onValueChange(selectedValue)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = selectedValue.toString().padStart(2, '0'),
            color = Color.White,
            fontSize = 24.sp
        )
    }
}