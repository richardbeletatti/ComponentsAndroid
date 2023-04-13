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
        cursor.addRow(arrayOf("A"))
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    fun getSavedValue(): String {
        val file = File(context?.filesDir, "meuArquivo.txt")
        val inputStream = FileInputStream(file)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        bufferedReader.close()
        inputStream.close()

        return stringBuilder.toString()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d("INSERT", "INSERT O VALOR: $values")

        // Extrai o valor do ContentValues
        val myValue = values?.getAsString("mystring") ?: ""

        // Cria um arquivo na memória interna do dispositivo
        val file = File(context?.filesDir, "meuArquivo.txt")

        // Abre um FileOutputStream para escrever os valores no arquivo
        val outputStream = FileOutputStream(file)

        // Escreve o valor no arquivo
        outputStream.write(myValue.toByteArray())

        // Fecha o FileOutputStream
        outputStream.close()

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
