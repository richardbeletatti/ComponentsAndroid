package com.richardbeletatti.mybank

import android.content.ComponentName
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.richardbeletatti.mybank.ui.theme.MyBankTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBankTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyBankApp()
                }
            }
        }
    }
}


@Composable
fun MyBankApp() {
    var textFieldState by remember { mutableStateOf(TextFieldValue()) }
    val savedValue = remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title),
            fontSize = 24.sp,
            modifier = Modifier.padding(50.dp, 10.dp)
        )
        Text(
            text = stringResource(R.string.subtitle),
            fontSize = 14.sp,
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = textFieldState,
            onValueChange = { textFieldState = it },
            label = { Text(stringResource(R.string.deposit)) },
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                savedValue.value = textFieldState.text

                // BROADCAST
                val intent = Intent().apply {
                    action = "com.richardbeletatti.vizu.SEND_MESSAGE"
                    putExtra("mystring", savedValue.value)
                    component = ComponentName("com.richardbeletatti.vizu", "com.richardbeletatti.vizu.MyBroadcastReceiver")
                }
                context.sendBroadcast(intent)

                // CONTENT PROVIDER
                val values = ContentValues().apply {
                    put("mystring", savedValue.value)
                }
                val uri = Uri.parse("content://com.richardbeletatti.provider")
                context.contentResolver.insert(uri, values)

                val sharedPrefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                sharedPrefs.edit().putBoolean("is_value_saved", true).apply()

            },

            modifier = Modifier
                .padding(8.dp)
                .width(200.dp)
                .height(48.dp),
            content = {
                Text(
                    text = stringResource(R.string.save),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = savedValue.value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyBankTheme {
        MyBankApp()
    }
}