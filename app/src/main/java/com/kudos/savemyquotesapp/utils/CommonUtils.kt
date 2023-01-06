package com.kudos.savemyquotesapp.utils

import android.content.Context
import android.content.Intent


object CommonUtils {

    fun Context.shareText(textToShare: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, textToShare)
        startActivity(Intent.createChooser(sharingIntent, "Share using"))
    }

}