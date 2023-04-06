package com.richardbeletatti.vizu

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val savedValue = intent.getStringExtra("MY_STRING")
        Log.d("MyBroadcastReceiver", "Valor recebido: $savedValue")
    }
}