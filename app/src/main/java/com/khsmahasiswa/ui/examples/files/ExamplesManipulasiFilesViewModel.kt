package com.khsmahasiswa.ui.examples.files

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khsmahasiswa.R
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.system.moveNavigationTo

class ExamplesManipulasiFilesViewModel : ViewModel() {

    fun onMoveToExportPdf(view: View) {
        moveNavigationTo(view, R.id.action_examplesManipulasiFilesFragment_to_examplesPdfFragment)
    }

    fun onMoveToExportExcel(view: View) {
        showToast(view.context, "Belum tersedia")
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExamplesManipulasiFilesViewModel() as T
        }
    }

}