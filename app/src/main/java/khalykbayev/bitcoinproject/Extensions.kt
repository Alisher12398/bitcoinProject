package khalykbayev.bitcoinproject

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.content.Context.MODE_PRIVATE
import android.util.Log
import java.io.*


@SuppressLint("SimpleDateFormat")
fun getDate(milliSeconds: Long, dateFormat: String): String {
    // Create a DateFormatter object for displaying date in specified format.
    val formatter = SimpleDateFormat(dateFormat)

    // Create a calendar object that will convert the date and time value in milliseconds to date.
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}
//
//fun writeToFile(data: String) {
//    try {
//        val fos = FileOutputStream(data)
//        val outputStreamWriter = OutputStreamWriter(fos)
//        outputStreamWriter.write(data)
//        Log.d("Exception read", "SUCCESS")
//        outputStreamWriter.close()
//    } catch (e: IOException) {
//        Log.d("Exception read", "File write failed: " + e.toString())
//    }
//
//}
//
//
//fun readFromFile(context: Context): String {
//    val filename = "myfile"
//    val numbers = arrayOf("1, 2, 3")
//    val outputStream: FileOutputStream
//
//    try {
//        outputStream = Context.
//        outputStream = openFileOutput(filename, Context.MODE_APPEND)
//        for (s in numbers) {
//            outputStream.write(s.toByteArray())
//        }
//        outputStream.close()
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//
//}