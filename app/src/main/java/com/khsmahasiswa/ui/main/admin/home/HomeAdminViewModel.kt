package com.khsmahasiswa.ui.main.admin.home

import android.content.Context
import android.view.View
import androidx.lifecycle.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.showMaterialDialog
import com.khsmahasiswa.utils.system.moveNavigationTo
import kotlinx.coroutines.launch

class HomeAdminViewModel(val firebaseDatabase: FirebaseDatabase) : ViewModel() {

    val data: LiveData<Response> = liveData {
        val response = firebaseDatabase.getAllData("users")
        emit(response)
    }

    val response = MutableLiveData<Response>()

    fun onTambahUser(view: View) {
        moveNavigationTo(view, R.id.action_homeAdminFragment_to_tambahUserFragment)
    }

    fun onEditUser(view: View, data: Any) {
        moveNavigationTo(view, R.id.action_homeAdminFragment_to_editUserFragment)
    }

    fun onHapusUser(id: String?, context: Context) {
        showMaterialDialog(context, "Apakah anda yakin ingin menghapus mahasiswa ini ?")
            .setPositiveButton("Iya") { dialog, witch ->
                viewModelScope.launch {
                    response.value = firebaseDatabase.delete("users", id.toString(), "")
                }
            }
            .show()
    }

    class Factory(val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeAdminViewModel(firebaseDatabase) as T
        }
    }
}