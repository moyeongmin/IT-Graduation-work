package com.example.bsn.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.example.bsn.presentation.ui.theme.BS_Choose_Theme
import com.example.bsn.presentation.ui.theme.BsnTheme

class Bus_Subway_Choose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BS_Choose_Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = CircleShape,
                    color = Color.Black
                ) {
                    BS_Choose_mainpage("Android")
                }
            }
        }
    }
}

@Composable
fun BS_Choose_mainpage(name: String, modifier: Modifier = Modifier) {
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
            }})

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    BsnTheme {
        BS_Choose_mainpage("Android")
    }
}