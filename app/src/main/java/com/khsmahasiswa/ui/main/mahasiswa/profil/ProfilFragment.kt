package com.khsmahasiswa.ui.main.mahasiswa.profil

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.QuerySnapshot
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.ProfilFragmentBinding
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.model.UserMatkul
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.showLogAssert

class ProfilFragment : Fragment(R.layout.profil_fragment) {

    private val viewModel: ProfilViewModel by viewModels {
        ProfilViewModel.Factory(FirebaseDatabase())
    }

    private lateinit var binding: ProfilFragmentBinding

    private var jumlahNilai = 0
    private var jumlahSks = 0
    private var sksXPoin = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ProfilFragmentBinding.bind(view)

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
        matkul?.forEach {
            if (it.nilai == "A") {
                jumlahNilai += 4
                sksXPoin += it.sks?.times(4) ?: 0
                jumlahSks += it.sks!!
            } else if (it.nilai == "B") {
                jumlahNilai += 3
                sksXPoin += it.sks?.times(3) ?: 0
                jumlahSks += it.sks!!
            } else if (it.nilai == "C") {
                jumlahNilai += 2
                sksXPoin += it.sks?.times(2) ?: 0
                jumlahSks += it.sks!!
            } else if (it.nilai == "D") {
                jumlahNilai += 1
                sksXPoin += it.sks?.times(1) ?: 0
            } else {
                jumlahNilai += 0
                sksXPoin += it.sks?.times(0) ?: 0
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