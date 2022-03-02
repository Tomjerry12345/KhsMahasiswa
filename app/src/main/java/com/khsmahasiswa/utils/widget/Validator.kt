package com.khsmahasiswa.utils.widget

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.khsmahasiswa.utils.other.showLogAssert


@BindingAdapter("checkEmpty")
fun checkEmpty(view: TextInputEditText, text: String) {
    showLogAssert("test", view.text.toString())
    showLogAssert("test 1", text)
}