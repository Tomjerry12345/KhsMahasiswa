package com.khsmahasiswa.ui.autentikasi.login

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.showDialog
import com.khsmahasiswa.utils.other.showLogAssert
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.widget.checkEmpty
import kotlinx.coroutines.launch

class LoginViewModel(private val firebaseDatabase: FirebaseDatabase) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val response = MutableLiveData<Response>()

    fun onLogin(view: View) {
        try {
            val email = checkEmpty(email.value, "Email tidak boleh kosong")
            val password = checkEmpty(password.value, "Password tidak boleh kosong")
            val dialog = showDialog(view.context, "Sedang di proses")
            viewModelScope.launch {
                response.value = firebaseDatabase.login("users", email, password)
                dialog.dismiss()
            }
        } catch (t: Throwable) {
            showToast(view.context, t.message.toString())
        }
    }

    class Factory(private val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(firebaseDatabase) as T
        }
    }
}