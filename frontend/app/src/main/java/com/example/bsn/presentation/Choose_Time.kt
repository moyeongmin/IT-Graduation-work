package com.example.bsn.presentation

import android.content.Intent
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
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
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.rememberPickerState
import com.example.bsn.R
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
            text = "출근 시간을\n\n선택해주세요!",
            color = Color.White,
            fontWeight = FontWeight(700),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )
        Button(
            onClick = { navController.navigate("timepickers") },
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(Color(0xFF8EBBFF))
        ) {
            Text(text = "다음", color = Color.Black, fontWeight = FontWeight(600))
        }

    }
}
@Composable
fun TimePicker(navController: NavController) {
    var hour by remember { mutableStateOf(0) }
    var minute by remember { mutableStateOf(0) }
    val hourState = rememberPickerState(initialNumberOfOptions = 24, initiallySelectedOption = hour)
    val minuteState = rememberPickerState(initialNumberOfOptions = 60, initiallySelectedOption = minute)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "시간을 선택하세요",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 15.dp,bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Picker(
                state = hourState,
                contentDescription = "Hour Picker",
                modifier = Modifier.size(100.dp),
                option = { optionIndex ->
                    Text(
                        text = optionIndex.toString().padStart(2, '0'),
                        color = Color(0xFFFDE293),
                        fontSize = 50.sp
                    )
                }
            )
            Text(
                text = ":",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Picker(
                state = minuteState,
                contentDescription = "Minute Picker",
                modifier = Modifier.size(100.dp),
                option = { optionIndex ->
                    Text(
                        text = optionIndex.toString().padStart(2, '0'),
                        color = Color.White,
                        fontSize = 50.sp
                    )
                }
            )
        }
        IconButton(
            onClick = {
                //출퇴근 시간 저장 및 통신
                hour = hourState.selectedOption
                minute = minuteState.selectedOption

            },
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(0xFFAECBFA))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.yes_mark),
                contentDescription = "Yes",
                tint = Color.Black,
                modifier = Modifier.size(36.dp)
            )
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