package com.khsmahasiswa.utils.system

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

fun moveNavigationTo(view: View, destination: Int) {
    view.findNavController().navigate(destination)
}

fun moveIntentTo(start: AppCompatActivity, destination: AppCompatActivity) {
    val intent = Intent(start, destination::class.java)
    start.startActivity(intent)
}