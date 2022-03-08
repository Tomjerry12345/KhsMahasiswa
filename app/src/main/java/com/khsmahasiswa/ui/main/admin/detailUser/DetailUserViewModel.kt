package com.khsmahasiswa.ui.main.admin.detailUser

import android.os.Environment
import android.view.View
import androidx.lifecycle.*
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.system.moveNavigationTo
import java.io.File

class DetailUserViewModel(val savedData: SavedData, val firebaseDatabase: FirebaseDatabase) : ViewModel() {

    val user = savedData.getObject(Constant.KEY_USER, ModelUser()) as ModelUser

    val nama = MutableLiveData<String>(user.nama)
    val nim = MutableLiveData<String>(user.nim)
    val noTelepon = MutableLiveData<String>(user.noTelepon)
    val namaAyah = MutableLiveData<String>(user.namaAyah)
    val namaIbu = MutableLiveData<String>(user.namaIbu)
    val noTeleponOrangTua = MutableLiveData<String>(user.noTeleponOrangtua)

    val data: LiveData<Response> = liveData {
        val response = firebaseDatabase.getDataSpecific("usersMatkul", "idUser", user.id.toString())
        emit(response)
    }

    fun onDetailMatkul(view: View) {
        moveNavigationTo(view, R.id.action_detailUserFragment_to_detailMatkulFragment)
    }

    fun onLaporkan(view: View) {

    }

    class Factory(private val savedData: SavedData, val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailUserViewModel(savedData, firebaseDatabase) as T
        }
    }
}