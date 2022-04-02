package com.khsmahasiswa.ui.main.admin.detailUser

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.QuerySnapshot
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.DetailUserAdminFragmentBinding
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.model.UserMatkul
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.showLogAssert
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.system.DocumentUtils


class DetailUserFragment : Fragment(R.layout.detail_user_admin_fragment) {

    private val viewModel: DetailUserViewModel by viewModels {
        DetailUserViewModel.Factory(SavedData, FirebaseDatabase())
    }

    private lateinit var binding: DetailUserAdminFragmentBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailUserAdminFragmentBinding.bind(view)

        binding.viewModel = viewModel

        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot
                    val data = querySnapshot.toObjects(UserMatkul::class.java)
//                    showLogAssert("data", "${data[0].matkul}")
                    if (data.isNotEmpty()) {
                        val matkul = data[0].matkul
                        hitungIpk(matkul)

                    }

                }
                is Response.Error -> showLogAssert("error", it.error)
                is Response.Success -> TODO()
            }
        }

    }

    fun hitungIpk(matkul: List<ModelMatakuliah>?) {
        var jumlahNilai = 0
        var jumlahSks = 0
        var sksXPoin = 0
        matkul?.forEach {
            when (it.nilai) {
                "A" -> {
                    jumlahNilai += 4
                    sksXPoin += it.sks?.times(4) ?: 0
                    jumlahSks += it.sks!!
                }
                "B" -> {
                    jumlahNilai += 3
                    sksXPoin += it.sks?.times(3) ?: 0
                    jumlahSks += it.sks!!
                }
                "C" -> {
                    jumlahNilai += 2
                    sksXPoin += it.sks?.times(2) ?: 0
                    jumlahSks += it.sks!!
                }
                "D" -> {
                    jumlahNilai += 1
                    sksXPoin += it.sks?.times(1) ?: 0
                }
                else -> {
                    jumlahNilai += 0
                    sksXPoin += it.sks?.times(0) ?: 0
                }
            }
        }

        showLogAssert("jumlahNilai", jumlahNilai.toString())
        showLogAssert("jumlahSks", jumlahSks.toString())
        showLogAssert("sksXPoin", sksXPoin.toString())

        binding.jumlahSks.text = jumlahSks.toString()
        binding.ipKomulatif.text =
            (sksXPoin.toFloat() / jumlahSks.toFloat()).toString()
    }

}