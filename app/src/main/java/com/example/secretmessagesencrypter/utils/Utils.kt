package com.example.secretmessagesencrypter.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.secretmessagesencrypter.R

object Utils {

    fun copyToClipboard(context: Context, text: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Copied Text", text)
        clipboardManager.setPrimaryClip(clipData)
        val toastText = ContextCompat.getString(context, R.string.copy_to_clipboard_toast_message)
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
    }

    fun openWhatsApp(context: Context, phoneNumber: String) {
        val url = "https://wa.me/$phoneNumber"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        // Check if there's an app to handle this intent
//        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
//        } else {
//            Toast.makeText(context, "WhatsApp is not installed on this device", Toast.LENGTH_SHORT).show()
//        }
    }

}