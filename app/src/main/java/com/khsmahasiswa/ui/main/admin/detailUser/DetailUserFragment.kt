package com.khsmahasiswa.ui.main.admin.detailUser

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
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


class DetailUserFragment : Fragment(R.layout.detail_user_admin_fragment) {

    private val viewModel: DetailUserViewModel by viewModels {
        DetailUserViewModel.Factory(SavedData, FirebaseDatabase(), requireActivity())
    }

    private lateinit var binding: DetailUserAdminFragmentBinding

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                binding.mbLapor.isEnabled = true
            } else {
                binding.mbLapor.isEnabled = false
            }
        }




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailUserAdminFragmentBinding.bind(view)

        binding.viewModel = viewModel

        requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)

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

    private fun hitungIpk(matkul: List<ModelMatakuliah>?) {
        var jumlahNilai = 0
        var jumlahSks = 0
        var sksXPoin = 0

        var isNilaiError = false

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
                    isNilaiError = true
                }
            }
        }

        binding.jumlahSks.text = jumlahSks.toString()
        binding.ipKomulatif.text = String.format("%.2f", sksXPoin.toFloat() / jumlahSks.toFloat())

        binding.mbLapor.isEnabled = isNilaiError
    }

}