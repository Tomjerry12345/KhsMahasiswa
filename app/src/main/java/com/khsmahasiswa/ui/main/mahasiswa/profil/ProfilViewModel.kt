package com.khsmahasiswa.ui.main.mahasiswa.profil

import androidx.lifecycle.*
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant

class ProfilViewModel(val firebaseDatabase: FirebaseDatabase) : ViewModel() {

    val user = SavedData.getObject(Constant.KEY_USER, ModelUser()) as ModelUser

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

    class Factory(val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfilViewModel(firebaseDatabase) as T
        }
    }

}