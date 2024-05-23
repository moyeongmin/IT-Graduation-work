package com.example.bsn.presentation.Bus

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bsn.presentation.Bus.ui.theme.BsnTheme
import com.example.bsn.presentation.Is_Transfer
import com.example.bsn.presentation.TimeText
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Bus_main : ComponentActivity() {

    private lateinit var speechResultLauncher: ActivityResultLauncher<Intent>
    private var record_text = mutableStateOf("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        speechResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    ?.let { results ->
                        record_text.value = results[0]  // 가장 높은 확률로 인식된 결과를 가져옵니다.
                        Log.d("record",results[0])

                        // 여기에서 spokenText를 처리합니다.
                        // 예를 들어, 상태를 업데이트하거나, Compose UI에 결과를 표시할 수 있습니다.
                    }
            }
        }
        setContent {
            BsnTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Bus_mainpage(speechResultLauncher,record_text)
                }
            }
        }
    }
}

@Composable
fun Bus_mainpage(speechResultLauncher: ActivityResultLauncher<Intent>, recordTextState: State<String>) {
    val navController = rememberNavController() // navcontroller생성
    val result = mutableListOf<String>("탑승역","버스들","탑승역들?","하차역")
    NavHost(navController = navController, startDestination = "station"){
        composable("station") { Bus_station(navController,speechResultLauncher,recordTextState)}
        composable("select/{station}") { backStackEntry ->
            val station = backStackEntry.arguments?.getString("station") ?: ""
            result[0] = station
            Bus_select(navController, station)
        }
        composable("ride/{buses}") { backStackEntry ->
            val buses = backStackEntry.arguments?.getString("buses") ?: ""
            result[1] = buses
            when(buses){
                "" -> Bus_ride(navController, buses.split(""))
                else -> Bus_ride(navController, buses.split(","))
            }

        }
        composable("drop/{rides}") { backStackEntry ->
            val rides = backStackEntry.arguments?.getString("rides") ?:""
            result[2] = rides
            Bus_drop(navController, result)}
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Bus_station(navController: NavController,speechResultLauncher: ActivityResultLauncher<Intent>, recordTextState: State<String>){

    val keyboardController = LocalSoftwareKeyboardController.current
    var query by rememberSaveable { mutableStateOf(recordTextState.value) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var searchResults by remember { mutableStateOf<List<String>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(recordTextState.value) {
        query = recordTextState.value
    }

    Scaffold (
        containerColor = Color.Black
    ) {
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
                textAlign = TextAlign.Center
            )
            Column(
                    Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                DockedSearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = {
                        keyboardController?.hide()
                        expanded = false
                        coroutineScope.launch {
                            searchResults = performSearch(query) // 서버 요청 함수 호출
                        }
                    },
                    active = expanded,
                    onActiveChange = { expanded = it },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .aspectRatio(2.8f)
                        .padding(top = 8.dp),
                    placeholder = { Text("정류장 검색",color = Color(0xFFFFFFFF), fontSize = 12.sp) },
                    trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    colors = SearchBarDefaults.colors(
                        containerColor = Color(0xFF202124),
                        inputFieldColors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = Color.White, // 입력된 텍스트의 색상 변경
                            unfocusedTextColor = Color.White // 입력된 텍스트의 색상 변경
                        )
                    )
                ) {
                    if (query.isNotEmpty()) {
                        repeat(4) { idx ->
                            val resultText = "Suggestion $idx"
                            ListItem(
                                headlineContent = { Text(text = resultText, color = Color(0x80FFFFFF)) },
                                supportingContent = { },
                                leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                                colors = ListItemDefaults.colors(containerColor = Color(0xFF202124)),
                                modifier = Modifier
                                    .clickable {
                                        query = resultText
                                        expanded = false
                                    }
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(searchResults.size) { index ->
                        Text(
                            text = searchResults[index],
                            color = Color(0xFF535556),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    Log.d("tagee", "111111111111")
                                    Log.d("station", searchResults[index])
                                    navController.navigate("select/${searchResults[index]}")
                                },
                        )
                    }
                }

            }
//
//            DockedSearchBar(
//                query = query,
//                onQueryChange = { query = it },
//                onSearch = { /* 검색 실행 로직 */ },
//                active = true,
//                colors = SearchBarDefaults.colors(
//                    containerColor = Color(0xFF202124),
//                    inputFieldColors = TextFieldDefaults.colors(
//                        focusedTextColor = Color.White, // 입력된 텍스트의 색상 변경
//                        unfocusedTextColor = Color.White // 입력된 텍스트의 색상 변경
//                    )
//                ),
//                onActiveChange = {/*SearchBar 기능 꺼졌을때*/},
//                modifier = Modifier
//                    .fillMaxWidth(0.8f)
//                    .aspectRatio(2.8f)
//                    .padding(top = 15.dp),
//                placeholder = {
//                    Text(
//                        text = "검색어 입력//////",
//                        fontSize = 10.sp,
//                        color = Color(0xFFFFFFFF)
//                    )
//                },
//                trailingIcon = {
//                    Icon(Icons.Filled.Search, contentDescription = "검색",
//                        Modifier
//                            .size(16.dp)
//                            .align(Alignment.End)
//                            .clickable {
//                                //검색 로직
//                            })
//                },
////                leadingIcon = {
////                    Icon(
////                        imageVector = ImageVector.vectorResource(id = R.drawable.mic_svg),
////                        contentDescription = "음성 녹음",
////                        Modifier
////                            .align(Alignment.Start)
////                            .clickable {
////                                val I = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
////                                    putExtra(
////                                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
////                                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
////                                    )
////                                }
////                                speechResultLauncher.launch(I)
////                                Log.d("query", query)
////                            })
////                },
//                content = {
//
//                }
////                    text = "정류장",
////                    color = Color(0xFFBDC1C6),
////                    textAlign = TextAlign.Center
//            )
//
//
        }

    }
}
@Composable
fun Bus_select(navController: NavController, station: String){

    //station으로 서버와 통신해 버스 리스트 받아오는 로직 추가
    //val bus_list = bus_list_search(station)
    var selectedBus by remember { mutableStateOf<List<String>>(emptyList()) }
    val bus_list = listOf("16번", "29번", "40번", "51번")
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
                text = "버스 선택",
                color = Color(0xFFBDC1C6),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp , top = 18.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(bus_list) { bus ->
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .fillMaxWidth(0.8f)
                            .aspectRatio(4f)
                            .clickable {
                                selectedBus =
                                    if (selectedBus.contains(bus)) {
                                        selectedBus - bus
                                    } else {
                                        selectedBus + bus
                                    }
                            },
                        color = if (selectedBus.contains(bus)) Color.Gray else Color(0xFF202124),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = bus, color = Color.White, fontSize = 16.sp)
                            Checkbox(
                                checked = selectedBus.contains(bus),
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        selectedBus += bus
                                    } else {
                                        selectedBus -= bus
                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color.White,
                                    uncheckedColor = Color.White,
                                    checkmarkColor = Color.Black
                                )
                            )
                        }
                    }
                }
            }
            Button(
                onClick = {
                    val buses = selectedBus.joinToString(",")
                    navController.navigate("ride/$buses")
                },
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.3f)
                    .aspectRatio(2f)
            ) {
                Text(text = "완료")
            }
        }
    }
}
@Composable
fun Bus_ride(navController: NavController, bus_nums: List<String>)
{
    val bus_list = listOf("부산대학교 정문", "부산대학교 후문", "부산대옆부산은행", "현대아파트")
    var selectedBus by remember { mutableStateOf<List<String>>(emptyList()) }
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
                .fillMaxHeight(0.7f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(bus_list) { bus ->
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .fillMaxWidth(0.8f)
                        .aspectRatio(4f)
                        .clickable {
                            selectedBus =
                                if (selectedBus.contains(bus)) {
                                    selectedBus - bus
                                } else {
                                    selectedBus + bus
                                }
                        },
                    color = if (selectedBus.contains(bus)) Color.Gray else Color(0xFF202124),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = bus, color = Color.White, fontSize = 16.sp)
                        Checkbox(
                            checked = selectedBus.contains(bus),
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedBus += bus
                                } else {
                                    selectedBus -= bus
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.White,
                                uncheckedColor = Color.White,
                                checkmarkColor = Color.Black
                            )
                        )
                    }
                }
            }
        }
        Button(
            onClick = {
                val buses = selectedBus.joinToString(",")
                navController.navigate("drop/$buses")
            },
            modifier = Modifier
                .padding(top = 8.dp, bottom = 10.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.3f)
                .aspectRatio(2f)
        ) {
            Text(text = "완료")
        }
    }
}
}
@Composable
fun Bus_drop(navController: NavController, result: MutableList<String>)
{
    val context = LocalContext.current
    val bus_list = listOf("구서1치안센터", "구서역 구서시장", "금정산쌍용예가", "현대아파트")
    var selectedBus by remember { mutableStateOf<List<String>>(emptyList()) }
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
                    .fillMaxHeight(0.7f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(bus_list) { bus ->
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .fillMaxWidth(0.8f)
                            .aspectRatio(4f)
                            .clickable {
                                selectedBus =
                                    if (selectedBus.contains(bus)) {
                                        selectedBus - bus
                                    } else {
                                        selectedBus + bus
                                    }
                            },
                        color = if (selectedBus.contains(bus)) Color.Gray else Color(0xFF202124),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = bus, color = Color.White, fontSize = 16.sp)
                            Checkbox(
                                checked = selectedBus.contains(bus),
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        selectedBus += bus
                                    } else {
                                        selectedBus -= bus
                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color.White,
                                    uncheckedColor = Color.White,
                                    checkmarkColor = Color.Black
                                )
                            )
                        }
                    }
                }
            }
            Button(
                onClick = {
                    val buses = selectedBus.joinToString(",")
                    result[3] = buses
                    //result[0] -> 검색해서 찾은 탑승 역
                    //result[1] -> 탑승 할 버스 번호들 ,로 구분
                    //result[2] -> 탑승할 역들?(빼도 될듯?) ,로 구분
                    //result[3] -> 하차할 역들 ,로 구분
                    //통신해서 result 혹은 처리해서 넘겨주기
                    Log.d("checkforresult", result.toString())
                    val i = Intent(context,Is_Transfer::class.java)
                    context.startActivity(i)
                          },
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.3f)
                    .aspectRatio(2f)
            ) {
                Text(text = "완료")
            }
        }
    }
}
fun displaySpeechRecognizer(){

}
@Preview(showBackground = true, device = "id:wearos_large_round")
@Composable
fun GreetingPreview3() {

}
suspend fun performSearch(query: String): List<String> {
    // query로 서버에 검색 요청, 받은 데이터를 반환
    return listOf("Result 1 for $query", "Result 2 for $query", "Result 3 for $query", "Result 4 for $query")
}
fun bus_list_search(station: String): List<String>{
    //정류장에 정차하는 버스 리스트 요청, 받은 데이터 반환
    return listOf("16번", "29번", "40번", "51번")
}
fun station_list_search(bus_num : String): List<String>{
    //선택된 버스 리스트에 맞는
    return listOf("구서1치안센터", "구서역 구서시장", "금정산쌍용예가", "현대아파트")
}