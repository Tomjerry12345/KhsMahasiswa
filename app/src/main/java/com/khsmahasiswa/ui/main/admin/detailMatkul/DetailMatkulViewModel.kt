package com.khsmahasiswa.ui.main.admin.detailMatkul

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.utils.system.moveNavigationTo

class DetailMatkulViewModel(val firebaseDatabase: FirebaseDatabase) : ViewModel() {

    fun onTambahMatkul(view: View) {
        moveNavigationTo(view, R.id.action_detailMatkulFragment_to_tambahMatkulFragment)
    }

    class Factory(private val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailMatkulViewModel(firebaseDatabase) as T
        }
    }

}