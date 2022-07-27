package com.khsmahasiswa.ui.main.admin.detailUser

import android.content.Context
import android.os.Build
import android.telephony.SmsManager
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.other.showDialog
import com.khsmahasiswa.utils.other.showLogAssert
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.system.moveNavigationTo

class DetailUserViewModel(
    val savedData: SavedData,
    val firebaseDatabase: FirebaseDatabase,
    val activity: FragmentActivity
) : ViewModel() {

    val user = savedData.getObject(Constant.KEY_USER, ModelUser()) as ModelUser

    val nama = MutableLiveData<String>(user.nama)
    val nim = MutableLiveData<String>(user.nim)
    val noTelepon = MutableLiveData<String>(user.noTelepon)
    val namaAyah = MutableLiveData<String>(user.namaAyah)
    val namaIbu = MutableLiveData<String>(user.namaIbu)
    val noTeleponOrangTua = MutableLiveData<String>(user.noTeleponOrangtua)

    val data: LiveData<Response> = liveData {
        val response = firebaseDatabase.getDataSpecific("usersMatkul", "idUser", user.id.toString())
        emit(response)
    }

    fun onDetailMatkul(view: View) {
        moveNavigationTo(view, R.id.action_detailUserFragment_to_detailMatkulFragment)
    }

    fun onLaporkan(view: View) {
        showAlertDialog(view)
    }

    private fun showAlertDialog(view: View) {
        viaWhatsapp(view)
//        val items = arrayOf("Via sms", "Via whatsapp")
//
//        MaterialAlertDialogBuilder(view.context)
//            .setTitle("Pilih aksi")
//            .setItems(items) { dialog, which ->
//                when(which) {
//                    0 -> viaSms()
//                    1 -> viaWhatsapp(view)
//                }
//            }
//            .show()
    }

    private fun viaSms() {

        val dialog = showDialog(activity, "Sedang mengirim")

        try {
//            val phoneNumber = checkEmpty(noTeleponOrangTua.value, "Kosong")
            val phoneNumber = "085648644068"
            val message = "Hanya testing kodong....."

//            val smsManager: SmsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                activity.getSystemService(SmsManager::class.java)
//            } else {
//                SmsManager.getDefault()
//            }
            val smsManager: SmsManager = SmsManager.getDefault()

            smsManager.sendTextMessage(phoneNumber, null, message, null, null)

            dialog.dismiss()

            showToast(activity, "Pesan terkirim")

        } catch (e: Exception) {
            dialog.dismiss()
            showLogAssert("error", e.message.toString())
            showToast(activity, "Please enter all the data.." +e.message.toString())
        }
    }

    private fun viaWhatsapp(view: View) {
        moveNavigationTo(view, R.id.action_detailUserFragment_to_viewNilaiFragment)
    }

    class Factory(
        private val savedData: SavedData,
        val firebaseDatabase: FirebaseDatabase,
        val activity: FragmentActivity
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailUserViewModel(savedData, firebaseDatabase, activity) as T
        }
    }
}