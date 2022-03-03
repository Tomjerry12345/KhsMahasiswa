package com.khsmahasiswa.ui.main.admin.tambahUser

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.showDialog
import com.khsmahasiswa.utils.other.showLogAssert
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.widget.checkEmpty
import kotlinx.coroutines.launch

class TambahUserViewModel(private val firebaseDatabase: FirebaseDatabase) : ViewModel() {

    val nama = MutableLiveData<String>()
    val nim = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val noTelepon = MutableLiveData<String>()
    val namaAyah = MutableLiveData<String>()
    val namaIbu = MutableLiveData<String>()
    val noTeleponOrangtua = MutableLiveData<String>()

    val response = MutableLiveData<Response>()

    fun onTambahUser(view: View) {
        try {
            val nama = checkEmpty(nama.value, "Nama tidak boleh kosong")
            val nim = checkEmpty(nim.value, "Nim tidak boleh kosong")
            val password = checkEmpty(password.value, "Password tidak boleh kosong")
            val noTelepon = checkEmpty(noTelepon.value, "No telepon tidak boleh kosong")
            val namaAyah = checkEmpty(namaAyah.value, "Nama ayah tidak boleh kosong")
            val namaIbu = checkEmpty(namaIbu.value, "Nama ibu tidak boleh kosong")
            val noTeleponOrangtua =
                checkEmpty(noTeleponOrangtua.value, "No telepon orang tua tidak boleh kosong")

            val dialog = showDialog(view.context, "Silahkan tunggu...")

            viewModelScope.launch {
                val modelUser = ModelUser(
                    nim = nim,
                    password = password,
                    nama = nama,
                    noTelepon = noTelepon,
                    namaAyah = namaAyah,
                    namaIbu = namaIbu,
                    noTeleponOrangtua = noTeleponOrangtua
                )
                when (val checkUser = firebaseDatabase.checkTheSameData("users", "nim", nim)) {
                    is Response.Changed -> {
                        val check = checkUser.data as Boolean
                        if (check) {
                            when(val response1 = firebaseDatabase.addData("users", null, modelUser)) {
                                is Response.Changed -> {
                                    val id = response1.data as String
                                    showLogAssert("responseId", id)
                                    response.value = firebaseDatabase.update("users", id, "id", id, "berhasil")
                                }
                                is Response.Error -> showToast(view.context, response1.error)
                                is Response.Success -> {}
                            }
                        } else {
                            showToast(view.context, "nim sudah terdaftar")
                        }
                    }
                    is Response.Error -> showToast(view.context, checkUser.error)
                    is Response.Success -> TODO()
                }


                dialog.dismiss()
            }
        } catch (t: Throwable) {
            showToast(view.context, t.message.toString())
        }
    }

    class Factory(private val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TambahUserViewModel(firebaseDatabase) as T
        }
    }

}