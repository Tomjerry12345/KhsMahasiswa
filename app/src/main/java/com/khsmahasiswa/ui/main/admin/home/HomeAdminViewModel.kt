package com.khsmahasiswa.ui.main.admin.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeAdminViewModel : ViewModel() {

    class Factory() : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeAdminViewModel() as T
        }
    }
}