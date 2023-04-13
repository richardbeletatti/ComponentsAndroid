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
        // Recuperar a informação salva, como do banco de dados
        val savedValue = "Valor recuperado"
        cursor.addRow(arrayOf(savedValue))
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
        Log.d("INSERT", "INSERT URI: $uri")
        Log.d("INSERT", "INSERT VALUES: $values")

        if (values == null || !values.containsKey("mystring")) {
            Log.e("INSERT", "ContentValues nulo ou não contém a chave 'mystring'")
            return null
        }

        // Extrai o valor do ContentValues
        val myValue = values?.getAsString("mystring") ?: ""

        if (myValue.isNullOrEmpty()) {
            Log.e("INSERT", "Valor passado está vazio ou nulo")
            return null
        }

        // Cria um arquivo na memória interna do dispositivo
        val file = File(context?.filesDir, "meuArquivo.txt")

        // Abre um FileOutputStream para escrever os valores no arquivo
        val outputStream = FileOutputStream(file)

        // Escreve o valor no arquivo
        outputStream.write(myValue.toByteArray())

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
