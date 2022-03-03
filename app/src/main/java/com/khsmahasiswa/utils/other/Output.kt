package com.khsmahasiswa.utils.other

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dmax.dialog.SpotsDialog

fun showLogAssert(tag: String, msg: String) {
    Log.println(Log.ASSERT, tag, msg)
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

fun showDialog(context: Context, text: String): AlertDialog {
    return SpotsDialog
        .Builder().setContext(context)
        .setMessage(text)
        .build().also {
            it.show()
        }
}

fun showMaterialDialog(context: Context, title: String): MaterialAlertDialogBuilder {
    return MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setNegativeButton("Tidak") { dialog, which ->
            // Respond to negative button press
        }
}