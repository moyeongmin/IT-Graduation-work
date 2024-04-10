package com.example.bsn.presentation

import android.os.Bundle
import android.text.style.BackgroundColorSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*

import com.example.bsn.presentation.theme.BsnTheme


import androidx.compose.material3.TabRowDefaults.Indicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight


import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.MaterialTheme.colors
import androidx.wear.compose.material.PositionIndicator

import com.example.bsn.R
import com.example.bsn.presentation.ui.theme.MainpageTheme

class mainpage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainpageTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = CircleShape,
                    color = Color.Black
                ) {
                    MainPage()
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
       MainPage()
}

@Composable
fun MainPage() {
    Scaffold(
        content = { paddingValues ->
            ScalingLazyColumn(
                modifier = Modifier
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 4.dp)
            ) {
                item {
                   Timestamp()
                }
                item {
                    WelcomeMessage()
                }
//                item {
//                    PermissionRequest()
//                }
//                item {
//                    LocationAccess()
//                }
            }
        }
    )
}

@Composable
fun WelcomeMessage() {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
       // contentColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Replace with your actual image resource
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Welcome"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "환영합니다! 갤럭시 워치!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "초록색 천체",
                fontSize = 12.sp
            )
            Text(
                text = "푸른 천체",
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun PermissionRequest() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "알람을 보내기 위해 더 많지 않은 센서를 활용해주세요!",
            color = Color.White,
            fontSize = 14.sp
        )
        Button(
            onClick = { /* Handle next action */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Next", color = Color.White)
        }
    }
}

@Composable
fun LocationAccess() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Change location access for My applications",
                fontSize = 14.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                ToggleButton(
                    checked = true,
                    onCheckedChange = { /* Handle allow access change */ }
                ) {
                    Text(text = "Allow access")
                }
                ToggleButton(
                    checked = false,
                    onCheckedChange = { /* Handle keep while app is in use change */ }
                ) {
                    Text(text = "Keep \"while app is in use\"")
                }
            }
        }
    }
}

@Composable
fun ToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(4.dp),
        color = if (checked) MaterialTheme.colorScheme.primary else Color.DarkGray
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = { onCheckedChange(!checked) })
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = null // We handle the change in the Row's clickable
            )
            Spacer(Modifier.width(4.dp))
            content()
        }
    }
}
