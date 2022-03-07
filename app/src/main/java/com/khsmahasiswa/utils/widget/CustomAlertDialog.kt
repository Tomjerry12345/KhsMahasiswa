package com.khsmahasiswa.utils.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.khsmahasiswa.R
import com.khsmahasiswa.utils.other.Constant

object CustomAlertDialog {

    var getNilai = ""

    fun getView(context: Context): View? {
        val customAlertDialogView = LayoutInflater.from(context)
            .inflate(R.layout.custom_alert_dialog, null, false)

        val dropdownNilai = customAlertDialogView.findViewById<TextInputLayout>(R.id.dropdownNilai)
        val dropdownNilai1 = (dropdownNilai.editText as? AutoCompleteTextView)

        val adapter = ArrayAdapter(context, R.layout.dropdown_custom_layout, Constant.listNilai)

        dropdownNilai1?.setAdapter(adapter)

        dropdownNilai1?.setOnItemClickListener { adapterView, view, i, l ->
            val getItem = adapterView.getItemAtPosition(i)
            getNilai = (getItem as String?).toString()
        }
        return customAlertDialogView
    }
}