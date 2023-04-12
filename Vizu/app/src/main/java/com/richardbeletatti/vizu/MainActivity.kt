package com.richardbeletatti.vizu

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.richardbeletatti.vizu.ui.theme.VizuTheme
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VizuApp()
        }
    }
}

@Composable
fun VizuApp() {
    val savedValue = remember { mutableStateOf("") }
    val context = LocalContext.current

    val contentUri = Uri.parse("content://com.richardbeletatti.provider/mystring")
    val projection = arrayOf("mystring")
    val cursor = context.contentResolver.query(contentUri, projection, null, null, null)

    Log.d("CURSOR", "CHEGOU NO CURSOR ")
    if (cursor != null && cursor.moveToFirst()) {
        val value = cursor.getColumnIndex("mystring")
        val myString = cursor.getString(value)
        savedValue.value = myString
        Log.d("CURSOR", "ENTROU !")
        Log.d("VALOR", "${savedValue.value}")
    }
    Log.d("CURSOR", "NÃO ENTROU =( ")
    cursor?.close()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp,
            modifier = Modifier.padding(50.dp, 10.dp)
        )
//        Text(text = content)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VizuTheme {
        VizuApp()
    }
}