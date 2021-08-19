package com.onedev.dicoding.myreadwritefile

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.OutputStreamWriter

internal object FileHelper {

    private const val TAG = "FileHelper"

    fun writeToFile(fileModel: FileModel, context: Context) {
        try {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput(fileModel.filename.toString(), Context.MODE_PRIVATE))
            outputStreamWriter.write(fileModel.data.toString())
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e(TAG, "writeToFile: File Write Failed $e", )
        }
    }

    fun readFromFile(context: Context, fileName: String) : FileModel {
        val fileModel = FileModel()
        try {
            val inputStream = context.openFileInput(fileName)

            if (inputStream != null) {
                val receiveString = inputStream.bufferedReader().use(BufferedReader::readText)
                inputStream.close()
                fileModel.data = receiveString
                fileModel.filename = fileName
            }
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "readFromFile: File Not Found $e", )
        } catch (e: IOException) {
            Log.e(TAG, "readFromFile: Can Not Read File $e", )
        }
        return fileModel
    }
}