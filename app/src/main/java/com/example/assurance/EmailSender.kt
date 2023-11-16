package com.example.assurance

import android.content.Context
import android.content.Intent
import android.widget.Toast


class EmailSender {
    fun sendEmail(context: Context, recipient: String, subject: String, body: String, dispatcher: String = "") {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            if (dispatcher.isNotEmpty()) {
                putExtra(Intent.EXTRA_CC, arrayOf(dispatcher))
            }
        }
        val emailApp = context.packageManager.resolveActivity(intent, 0)
        if (emailApp != null) {
            // Start "activity_show_contracts" after sending email
            val accueilAdminIntent = Intent(context, AccueilAdmin::class.java)
            context.startActivity(accueilAdminIntent)
            context.startActivity(Intent.createChooser(intent, "Send email using"))
        } else {
            Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
        }

    }
}


