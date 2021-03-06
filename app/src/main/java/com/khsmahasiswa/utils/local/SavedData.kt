package com.khsmahasiswa.utils.local

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.utils.other.Constant

object SavedData {
    private lateinit var sharedPref: SharedPreferences
    private val gson = Gson()

    fun init(activity: FragmentActivity) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
    }

    fun setString(params: String) {
        with (sharedPref.edit()) {
            putString(Constant.examplesKeySavedDataString, params)
            commit()
        }
    }

    fun getString(): String? {
        return sharedPref.getString(Constant.examplesKeySavedDataString, "")
    }

    fun setInt(params: Int) {
        with (sharedPref.edit()) {
            putInt(Constant.examplesKeySavedDataString, params)
            commit()
        }
    }

    fun getInt(): Int {
        return sharedPref.getInt(Constant.examplesKeySavedDataString, 0)
    }

    fun setObject(key: String, params: Any?) {
        val json = gson.toJson(params)
        with (sharedPref.edit()) {
            putString(key, json)
            commit()
        }

    }

    fun getObject(key: String, model: Any): Any? {
        val json: String? = sharedPref.getString(key, "")
        return gson.fromJson(json, model::class.java)
    }
}