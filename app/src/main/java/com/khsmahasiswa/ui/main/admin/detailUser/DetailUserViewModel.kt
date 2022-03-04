package com.khsmahasiswa.ui.main.admin.detailUser

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khsmahasiswa.R
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.system.moveNavigationTo

class DetailUserViewModel(val savedData: SavedData) : ViewModel() {

    val user = savedData.getObject(Constant.KEY_USER, ModelUser()) as ModelUser

    val nama = MutableLiveData<String>(user.nama)
    val nim = MutableLiveData<String>(user.nim)
    val noTelepon = MutableLiveData<String>(user.noTelepon)
    val namaAyah = MutableLiveData<String>(user.namaAyah)
    val namaIbu = MutableLiveData<String>(user.namaIbu)
    val noTeleponOrangTua = MutableLiveData<String>(user.noTeleponOrangtua)

    fun onDetailMatkul(view: View) {
        moveNavigationTo(view, R.id.action_detailUserFragment_to_detailMatkulFragment)
    }

    class Factory(private val savedData: SavedData) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailUserViewModel(savedData) as T
        }
    }
}