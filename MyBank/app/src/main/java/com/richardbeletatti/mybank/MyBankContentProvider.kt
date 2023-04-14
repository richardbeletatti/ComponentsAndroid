package com.richardbeletatti.mybank

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import java.io.*

class MyBankContentProvider : ContentProvider() {
    companion object {
        private const val TAG = "MyBankContentProvider"
        private const val uri = "com.richardbeletatti.mybank"
        private const val path = "mystring"
        private const val code = 1
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private var myValue: String? = null

    override fun onCreate(): Boolean {
        uriMatcher.addURI(uri, path, code)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            code -> {
                Log.d(TAG, "query - match = 1")
                val cursor = MatrixCursor(arrayOf("column1"))
                cursor.newRow()
                    .add("column1", myValue)
                cursor
            }
            else -> {
                Log.w(TAG, "query - match = NO MATCH")
                null
            }
        }
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.item/com.richardbeletatti.mybank.${uri.lastPathSegment}_${System.currentTimeMillis()}"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d("INSERT", "INSERT URI: $uri")
        Log.d("INSERT", "INSERT VALUES: $values")

        if (values == null || !values.containsKey("mystring")) {
            Log.e("INSERT", "ContentValues nulo ou não contém a chave 'mystring'")
            return null
        }

        // Extrai o valor do ContentValues
        myValue = values?.getAsString("mystring") ?: ""

        if (myValue.isNullOrEmpty()) {
            Log.e("INSERT", "Valor passado está vazio ou nulo")
            return null
        }

        // Cria um arquivo na memória interna do dispositivo
        val file = File(context?.filesDir, "meuArquivo.txt")

        // Abre um FileOutputStream para escrever os valor's no arquivo
        val outputStream = FileOutputStream(file)

        // Escreve o valor no arquivo
        outputStream.write(myValue!!.toByteArray())

        // Fecha o FileOutputStream
        outputStream.close()

        Log.d("INSERT", "Valor salvo no arquivo com sucesso")

        // Retorna a URI do novo registro inserido (neste caso, não estamos retornando uma URI)
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
