package com.khsmahasiswa.ui.main.admin.viewNilai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khsmahasiswa.database.firebase.FirebaseDatabase

class ViewNilaiViewModel(val firebaseDatabase: FirebaseDatabase) : ViewModel() {

    class Factory(private val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ViewNilaiViewModel(firebaseDatabase) as T
        }
    }

}