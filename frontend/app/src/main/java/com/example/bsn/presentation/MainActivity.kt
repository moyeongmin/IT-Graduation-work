/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.bsn.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.bsn.R
import com.example.bsn.presentation.theme.BsnTheme
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)

        //로그인 시도 혹은 로그인이 되어 있다면 메인 페이지로
        gotomainpage(this)

        setContent {
           WearApp(greetingName = "김세훈!")
        }
    }
}
@Composable
fun WearApp(greetingName: String) {
    BsnTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                shape = MaterialTheme.shapes.large
            )
            {
                Text(text = "버튼이에용")
            }
            TimeText()
            Greeting(greetingName = greetingName)
        }
    }
}
@Composable
fun Greeting(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),

        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.hello_world, greetingName)
    )

}
@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp(greetingName = "김세훈!")
}
@Composable
fun LoginScreen(onLoginClicked: (String) -> Unit) {
    // 사용자가 입력한 이름을 저장할 State
    var userName by remember { mutableStateOf("") }

    // 로그인 버튼을 클릭했을 때 호출되는 함수
    val onLoginButtonClick: () -> Unit = {
        // 사용자가 입력한 이름을 넘겨줌
        onLoginClicked(userName)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 사용자 이름 입력 필드
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("이름을 입력하세요") },
            modifier = Modifier.padding(16.dp)
        )

        // 로그인 버튼
        Button(onClick = onLoginButtonClick) {
            Text("로그인")
        }
    }
}

fun gotomainpage(context : Context){
    val intent = Intent(context, mainpage::class.java)
    context.startActivity(intent)
}


@Composable
fun welcomepage()
{
    // 사용자가 로그인되어 있는지 여부를 저장하는 State
    var isLoggedIn by remember { mutableStateOf(false) }

    // 사용자 이름을 저장하는 State
    var userName by remember { mutableStateOf("") }

    // 사용자가 로그인 버튼을 클릭했을 때 호출되는 함수
    val onLoginClicked: (String) -> Unit = { name ->
        isLoggedIn = true
        userName = name
    }

    // 환영 메시지를 표시하는 Composable 함수
    @Composable
    fun WelcomeMessage(name: String) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            text = stringResource(R.string.hello_world, name)
        )
    }

    // 로그인되어 있지 않으면 로그인 화면을 표시하고, 로그인되어 있으면 환영 메시지를 표시함
    if (!isLoggedIn) {
        LoginScreen(onLoginClicked)
    } else {
        BsnTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {
                TimeText()
                WelcomeMessage(userName)
            }
        }
    }
}