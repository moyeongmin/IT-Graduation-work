package com.example.bsn.presentation

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import com.example.bsn.R
import com.example.bsn.presentation.Bus.Bus_main
import com.example.bsn.presentation.ui.CustomImageButton
import com.example.bsn.presentation.ui.theme.BsnTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Route_Edit : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = intent

        setContent {
            BsnTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Route_navigation(i.getIntExtra("출퇴근",0))
                }
            }
        }
    }
}
@Composable
fun Route_navigation(Type: Int) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "route_page")
    {
        composable("route_page"){ Route_mainpage(navController = navController, type = Type)}
        composable("route_detail"){Route_detail(navController)}
    }
}

@Composable
fun Route_mainpage(navController: NavController , type : Int){//0이면 출근,1이면 퇴근
    val context = LocalContext.current
    //서버와의 통신
    //get_route(type)
    val data = arrayListOf<List<String>>()
    data.add(listOf("1008","24","33"))
    data.add(listOf("구서","장전","1008"))
    data.add(listOf("만덕","연산","부산대"))
    Scaffold(
        containerColor = Color.Black,
        content = {
            paddingValues ->
            TimeText()
            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 10.dp)
            ) {
                item {
                    var tmp = ""
                    when(type)
                    {
                        0 -> tmp = "출근"
                        1 -> tmp = "퇴근"
                    }
                    Text(text = "${tmp} 설정",
                        color = Color(0xFFBDC1C6),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Spacer(modifier = Modifier.padding(bottom = 30.dp))
                }
                itemsIndexed(data) { index , route->
                    val imgid = when (index) {
                        0 -> R.drawable.btn_route_1
                        1 -> R.drawable.btn_route_2
                        2 -> R.drawable.btn_route_3
                        3 -> R.drawable.btn_route_4
                        4 -> R.drawable.btn_route_5
                        else -> R.drawable.btn_route_1 // 기본 이미지 설정
                    }
                    var text = ""
                    for (i in 0 until data[index].size){
                        text +=data[index][i]
                        text+=" "
                    }

                    CustomImageButton(
                        widthratio = 1f,
                        heightratio = 2.5f,
                        defaultImagePainter = imgid,
                        contentDescription = null,
                        onClick = { navController.navigate("route_detail") },
                        text = text,
                        textsize = 12.sp,
                        titletext = "경로${index+1}",
                        titletextsize = 14.sp,
                        align = Alignment.TopStart
                    )

                }
                item{
                    IconButton(
                        onClick = {//추가 버튼 클릭 시 버스 지하철 선택으로 이동
                            val i = Intent(context,Bus_Subway_Choose::class.java)
                            context.startActivity(i)
                        },
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFAECBFA))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.plus_mark),
                            contentDescription = "Plus_mark",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

        }
    )
}

@Composable
fun Route_detail(navController: NavController){
    val context = LocalContext.current
    //루트 메인 페이지에서 선택한 루트의 디테일 표시
    var routes by remember { mutableStateOf(listOf("1008번", "24번", "33번","21번")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "루트 1",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        for (route in routes) {
            Route_Box(
                route = route,
                onDelete = { routes = routes.filter { it != route } }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        IconButton(
            onClick = {//추가 버튼 클릭 시 버스 지하철 선택으로 이동
                val i = Intent(context,Bus_Subway_Choose::class.java)
                context.startActivity(i)
            },
            modifier = Modifier
                .padding(top = 15.dp)
                .clip(CircleShape)
                .background(Color(0xFFAECBFA))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.plus_mark),
                contentDescription = "Plus_mark",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

fun get_route(Type : Int){
    when(Type){
        0 ->{}//출근 경로 데이터 받아오기
        1 ->{}//퇴근 경로 데이터 받아오기
    }

}
@Composable
fun Greeting4(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview6() {
    BsnTheme {
        Greeting4("Android")
    }
}

@SuppressLint("Range")
@Composable
fun Route_Box(route: String, onDelete: () -> Unit) {
    val context = LocalContext.current
    var offsetX by remember { mutableStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .aspectRatio(4f)
            .clip(RoundedCornerShape(100.dp))
            .background(Color(0xFF202124)),

        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Text(
                text = route,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 16.dp).align(Alignment.CenterStart)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd)
                    .background(
                        color = Color(0xFFFF9E96),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 50.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 50.dp
                        )
                    )
            ){
                Icon(
                painter = painterResource(id = R.drawable.icon_delete),
                contentDescription = "Delete",
                tint = Color(0xFFEE675C),
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF9E96))
                    .align(Alignment.Center)
            )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd)

                    .offset(x = offsetX.dp)
                    .background(
                        color = Color(0xFF2B2C2F),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 50.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 50.dp
                        )
                    )
            ){
                Icon(
                    painter = painterResource(id = R.drawable.icon_settings),
                    contentDescription = "Settings",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2B2C2F))
                        .align(Alignment.Center)
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures(
                                onHorizontalDrag = { change, dragAmount ->
                                    coroutineScope.launch {
                                        offsetX = (offsetX + dragAmount).coerceIn(-50f, 0f)
                                        Log.d("왜", dragAmount.toString())
                                    }
                                },
                                onDragEnd = {
                                    if (offsetX <= -40f) {
                                        onDelete()
                                        coroutineScope.launch {
                                            delay(100) // 잠깐의 지연을 주어 삭제가 완료되도록 함
                                            offsetX = 0f
                                        }
                                    } else {
                                        coroutineScope.launch {
                                            offsetX = 0f
                                        }
                                    }
                                }
                            )
                        }
                        .clickable {
                            //버스라면 버스 페이지로, 지하철이라면 지하철 페이지로
                            val i = Intent(context,Bus_main::class.java)
                            i.putExtra("edit",true)
                            context.startActivity(i)
                        }
                )
            }



        }
    }
}