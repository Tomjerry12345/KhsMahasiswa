package com.khsmahasiswa.utils.widget

fun checkEmpty(value: String?, msg: String): String {
    return value ?: throw Throwable(msg)
}