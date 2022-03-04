package com.khsmahasiswa.ui.main.admin.tambahMatkul

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.model.UserMatkul
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.other.showDialog
import com.khsmahasiswa.utils.other.showLogAssert
import com.khsmahasiswa.utils.other.showToast
import kotlinx.coroutines.launch

class TambahMatkulViewModel(val firebaseDatabase: FirebaseDatabase) : ViewModel() {

    val data: LiveData<Response> = liveData {
        val response = firebaseDatabase.getAllData("matkul")
        emit(response)
    }

    val response = MutableLiveData<Response>()

    val listMatkul = ArrayList<ModelMatakuliah>()

    val user = SavedData.getObject(Constant.KEY_USER, ModelUser()) as ModelUser
    var dataUserMatkul: UserMatkul? = null

    @RequiresApi(Build.VERSION_CODES.N)
    fun tambahMatkul(matakuliah: ModelMatakuliah) {
        listMatkul.add(matakuliah)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun sendMatkulToDb(view: View) {
        val list = listMatkul.distinctBy {
            it.matakuliah
        }
        showLogAssert("listMatkul", "$list")

        val dialog = showDialog(view.context, "Silahkan tunggu...")

        viewModelScope.launch {
            val userMatkul = UserMatkul(
                idUser = user.id,
                matkul = list
            )



            when (val checkUser = firebaseDatabase.checkTheSameData("usersMatkul", "id", user.id.toString())) {
                is Response.Changed -> {
                    val check = checkUser.data as Boolean
                    if (check) {
                        when(val response1 = firebaseDatabase.addData("usersMatkul", null, userMatkul)) {
                            is Response.Changed -> {
                                val id = response1.data as String
                                showLogAssert("responseId", id)
                                response.value = firebaseDatabase.update("usersMatkul", id, "id", id, "berhasil")
                            }
                            is Response.Error -> showToast(view.context, response1.error)
                            is Response.Success -> {}
                        }
                    } else {
                        val list1 = ArrayList<ModelMatakuliah>()

                        dataUserMatkul?.matkul?.forEach {
                            list1.add(it)
                        }

                        userMatkul.matkul?.forEach {
                            list1.add(it)
                        }

                        response.value = userMatkul.id?.let {
                            firebaseDatabase.update(
                                "usersMatkul",
                                it, "matkul", list1, "berhasil"
                            )
                        }

                        showToast(view.context, "data sudah ada")
                    }
                }
                is Response.Error -> showToast(view.context, checkUser.error)
                is Response.Success -> TODO()
            }
            dialog.dismiss()
        }
    }

    class Factory(private val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TambahMatkulViewModel(firebaseDatabase) as T
        }
    }

}