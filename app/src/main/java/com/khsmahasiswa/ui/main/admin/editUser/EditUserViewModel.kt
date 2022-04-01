package com.khsmahasiswa.ui.main.admin.editUser

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.other.showDialog
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.widget.checkEmpty
import kotlinx.coroutines.launch

class EditUserViewModel(val firebaseDatabase: FirebaseDatabase) : ViewModel() {

    var id = ""
    val nama = MutableLiveData<String>()
    val nim = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val angkatan = MutableLiveData<String>()
    val noTelepon = MutableLiveData<String>()
    val alamat = MutableLiveData<String>()
    val namaAyah = MutableLiveData<String>()
    val namaIbu = MutableLiveData<String>()
    val noTeleponOrangtua = MutableLiveData<String>()

    val response = MutableLiveData<Response>()

    val savedData = SavedData.getObject(Constant.KEY_USER, ModelUser()) as ModelUser

    fun onEditUser(view: View) {
        val dialog = showDialog(view.context, "Silahkan tunggu...")
        try {
            val nama = checkEmpty(nama.value, "Nama tidak boleh kosong")
            val nim = checkEmpty(nim.value, "Nim tidak boleh kosong")
            val password = checkEmpty(password.value, "Password tidak boleh kosong")
            val angkatan = checkEmpty(angkatan.value, "Angkatan tidak boleh kosong")
            val noTelepon = checkEmpty(noTelepon.value, "No telepon tidak boleh kosong")
            val alamat = checkEmpty(alamat.value, "Alamat tidak boleh kosong")
            val namaAyah = checkEmpty(namaAyah.value, "Nama ayah tidak boleh kosong")
            val namaIbu = checkEmpty(namaIbu.value, "Nama ibu tidak boleh kosong")
            val noTeleponOrangtua =
                checkEmpty(noTeleponOrangtua.value, "No telepon orang tua tidak boleh kosong")

            viewModelScope.launch {
                val modelUser = ModelUser(
                    nim = nim,
                    password = password,
                    angkatan = angkatan,
                    nama = nama,
                    noTelepon = noTelepon,
                    alamat = alamat,
                    namaAyah = namaAyah,
                    namaIbu = namaIbu,
                    noTeleponOrangtua = noTeleponOrangtua
                )
                response.value = firebaseDatabase.update("users", id, null, modelUser, "Berhasil di ubah")
                dialog.dismiss()
            }
        } catch (t: Throwable) {
            showToast(view.context, t.message.toString())
        }
    }

    class Factory(private val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditUserViewModel(firebaseDatabase) as T
        }
    }
}