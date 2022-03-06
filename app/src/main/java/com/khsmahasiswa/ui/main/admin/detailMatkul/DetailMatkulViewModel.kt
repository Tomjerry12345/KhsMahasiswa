package com.khsmahasiswa.ui.main.admin.detailMatkul

import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.model.UserMatkul
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.other.showLogAssert
import com.khsmahasiswa.utils.other.showMaterialDialog
import com.khsmahasiswa.utils.system.moveNavigationTo
import kotlinx.coroutines.launch

class DetailMatkulViewModel(val firebaseDatabase: FirebaseDatabase) : ViewModel() {

    val user = SavedData.getObject(Constant.KEY_USER, ModelUser()) as ModelUser

    val data: LiveData<Response> = liveData {
        val response = firebaseDatabase.getDataSpecific("usersMatkul", "idUser", user.id.toString())
        emit(response)
    }

    val response = MutableLiveData<Response>()

    fun onTambahMatkul(view: View) {
        moveNavigationTo(view, R.id.action_detailMatkulFragment_to_tambahMatkulFragment)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun onHapusUser(matakuliah: String?, context: Context) {
        val dataUserMatkul = SavedData.getObject(Constant.KEY_USER_MATKUL, UserMatkul()) as UserMatkul
        showMaterialDialog(context, "Apakah anda yakin ingin menghapus mahasiswa ini ?")
            .setPositiveButton("Iya") { dialog, witch ->
                viewModelScope.launch {
//                    response.value = firebaseDatabase.delete("users", id.toString(), "")
                    val id = dataUserMatkul.id
                    val listMatkul = dataUserMatkul.matkul as MutableList<ModelMatakuliah>
                    showLogAssert("beforelistMatkul", "$dataUserMatkul")
                    listMatkul.removeIf {
                        matakuliah == it.matakuliah
                    }

                    showLogAssert("matakuliah", "$matakuliah")
                    showLogAssert("listMatkul", "$listMatkul")

                    response.value = id?.let {
                        listMatkul.let { it1 ->
                            firebaseDatabase.update(
                                "usersMatkul",
                                it, "matkul", it1, "berhasil"
                            )
                        }
                    }
                }
            }
            .show()
    }

    class Factory(private val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailMatkulViewModel(firebaseDatabase) as T
        }
    }

}