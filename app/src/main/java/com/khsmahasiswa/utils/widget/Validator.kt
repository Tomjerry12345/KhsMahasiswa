package com.khsmahasiswa.utils.widget

fun checkEmpty(value: String?): String {
    return value ?: throw Throwable("Email tidak boleh kosong")
}