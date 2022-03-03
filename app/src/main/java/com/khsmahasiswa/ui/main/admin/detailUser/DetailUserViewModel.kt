package com.khsmahasiswa.ui.main.admin.detailUser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khsmahasiswa.database.firebase.FirebaseDatabase

class DetailUserViewModel(val firebaseDatabase: FirebaseDatabase) : ViewModel() {
    class Factory(private val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailUserViewModel(firebaseDatabase) as T
        }
    }
}