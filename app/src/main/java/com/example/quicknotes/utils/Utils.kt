package com.example.quicknotes.utils

import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Utils {
    companion object{
        fun isOnline(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }
}

object TimeFormatterUtil{
    fun formatTimestamp(
        timestamp: Long,
        pattern: String = "yyyy-MM-dd HH:mm a",
        locale: Locale = Locale.getDefault()
    ): String {
        val date = Date(timestamp)
        val formatter = SimpleDateFormat(pattern, locale)
        return formatter.format(date)
    }
}

