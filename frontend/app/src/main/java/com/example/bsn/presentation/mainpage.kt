package com.example.bsn.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*


import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight


import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn

import com.example.bsn.R
import com.example.bsn.presentation.ui.CustomImageButton
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

@Preview(showBackground = true, device = "id:wearos_small_round")
@Composable
fun GreetingPreview() {
       MainPage()
}

@Composable
fun MainPage() {
    Scaffold(
        containerColor = Color.Black,
        content = { paddingValues ->
            ScalingLazyColumn(
                modifier = Modifier
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 10.dp)
            ) {
                item {
                   Timestamp()
                    Spacer(modifier = Modifier.padding(bottom = 5.dp))
                }
                item {
                    val username = "김세훈" // 유저 이름 받아오기
                    Text(text = "${username}님, 반갑습니다!",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Spacer(modifier = Modifier.padding(bottom = 30.dp))
                }
                item {
                    CustomImageButton(//출근 설정으로 이동
                        defaultImagePainter = R.drawable.mainpage_gotowork,
                        contentDescription = "goroworkbtn",
                        onClick = { /*TODO*/ },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "출근 설정",
                        textsize = 12.sp,
                        key = 1)
                }
                item {
                    CustomImageButton(//퇴근 설정으로 이동
                        defaultImagePainter = R.drawable.mainpage_comebackhome,
                        contentDescription = "comebackhomebtn",
                        onClick = { /*TODO*/ },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "퇴근 설정",
                        textsize = 12.sp,
                        key = 2)
                }
                item {
                    CustomImageButton(//옵션 페이지로 이동
                        defaultImagePainter = R.drawable.mainpage_options,
                        contentDescription = "optionsbtn",
                        onClick = { /*TODO*/ },
                        widthratio = 1f,
                        heightratio = 4f,
                        text = "옵션",
                        textsize = 12.sp,
                        key = 3)
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
