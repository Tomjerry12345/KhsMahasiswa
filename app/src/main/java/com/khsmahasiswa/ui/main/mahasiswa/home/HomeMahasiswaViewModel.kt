package com.khsmahasiswa.ui.main.mahasiswa.home

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.system.moveNavigationTo


class HomeMahasiswaViewModel(val firebaseDatabase: FirebaseDatabase) : ViewModel() {

    val user = SavedData.getObject(Constant.KEY_USER, ModelUser()) as ModelUser

    val data: LiveData<Response> = liveData {
        val response = firebaseDatabase.getDataSpecific("usersMatkul", "idUser", user.id.toString())
        emit(response)
    }

    fun showPopup(v: View) {
        val popup = PopupMenu(v.context, v)
        popup.menuInflater.inflate(R.menu.menu_mahasiswa, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            // Respond to menu item click.

            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    fun onMoveToProfil(view: View) {
        moveNavigationTo(view, R.id.action_homeMahasiswaFragment_to_profilFragment)
    }

    class Factory(val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeMahasiswaViewModel(firebaseDatabase) as T
        }
    }

}