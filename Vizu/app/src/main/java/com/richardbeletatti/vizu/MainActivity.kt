package com.richardbeletatti.vizu

import android.database.ContentObserver
import android.database.Cursor
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.richardbeletatti.vizu.ui.theme.VizuTheme

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

    val contentUri = Uri.parse("content://com.richardbeletatti.mybank/mystring")
    val projection = arrayOf("mystring")
    var cursor by remember { mutableStateOf<Cursor?>(null) }

    LaunchedEffect(Unit) {
        val observer = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                val newCursor = context.contentResolver.query(
                    contentUri,
                    projection,
                    null,
                    null,
                    null
                )
                cursor?.close()
                cursor = newCursor
            }
        }

        context.contentResolver.registerContentObserver(
            contentUri,
            true,
            observer
        )
        val newCursor = context.contentResolver.query(
            contentUri,
            projection,
            null,
            null,
            null
        )
        cursor = newCursor
    }

    Log.d("CURSOR", "CHEGOU NO CURSOR: ${cursor}")
    if (cursor != null && cursor!!.moveToFirst()) {
        val value = cursor!!.getColumnIndex("column1")
        val myString = cursor!!.getString(value)
        savedValue.value = myString
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 32.sp,
            modifier = Modifier.padding(50.dp, 10.dp)
        )
        Text(
            text = "Valor salvo: ${savedValue.value}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VizuTheme {
        VizuApp()
    }
}