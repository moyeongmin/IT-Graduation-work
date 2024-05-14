package com.example.bsn.presentation.Bus

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData.Item
import android.content.Intent
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
    NavHost(navController = navController, startDestination = "station"){
        composable("station") { Bus_station(navController,speechResultLauncher,recordTextState)}
        composable("select") { Bus_select(navController) }
        composable("ride") { Bus_ride(navController) }
        composable("drop") { Bus_drop(navController)}
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Bus_station(navController: NavController,speechResultLauncher: ActivityResultLauncher<Intent>, recordTextState: State<String>){
    val context = LocalContext.current
    var query by rememberSaveable { mutableStateOf(recordTextState.value) }


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

            DockedSearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { /* 검색 실행 로직 */ },
                active = true,
                onActiveChange = {/*SearchBar 기능 꺼졌을때*/},
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(2.8f)
                    .padding(top = 15.dp),
                placeholder = {
                    Text(
                        text = "검색어 입력//////",
                        fontSize = 8.sp
                    )
                },
                trailingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "검색",
                        Modifier
                            .size(16.dp)
                            .align(Alignment.End)
                            .clickable {
                                //검색 로직
                            })
                },
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.mic_svg),
                        contentDescription = "음성 녹음",
                        Modifier
                            .align(Alignment.Start)
                            .clickable {
                                val I = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                    putExtra(
                                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                                    )
                                }
                                speechResultLauncher.launch(I)
                                Log.d("query", query)
                            })
                },
                content = {

                }
//                    text = "정류장",
//                    color = Color(0xFFBDC1C6),
//                    textAlign = TextAlign.Center
            )


        }

    }
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
fun displaySpeechRecognizer(){

}
@Preview(showBackground = true, device = "spec:width=384px,height=500px,dpi=320,isRound=true")
@Composable
fun GreetingPreview3() {
    BsnTheme {
        //
    }
}