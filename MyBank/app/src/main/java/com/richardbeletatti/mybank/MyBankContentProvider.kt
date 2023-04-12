package com.richardbeletatti.mybank

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MyBankContentProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor = MatrixCursor(arrayOf("mystring"))
        cursor.addRow(arrayOf("my default value"))
        Log.d("QUERY","PAROU NA FUN QUERY")
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d("INSERT", "veio ate aqui e escrito: $values")

        val myValue = values?.getAsString("mystring") ?: ""

        val file = File(context?.filesDir, "meuArquivo.txt")
        val outputStream = FileOutputStream(file)

        outputStream.write(myValue.toByteArray())
        outputStream.close()

        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
