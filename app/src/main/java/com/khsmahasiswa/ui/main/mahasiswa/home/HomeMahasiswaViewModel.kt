package com.khsmahasiswa.ui.main.mahasiswa.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeMahasiswaViewModel : ViewModel() {

    class Factory() : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeMahasiswaViewModel() as T
        }
    }

}