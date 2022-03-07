package com.khsmahasiswa.ui.main.admin.detailMatkul

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.DetailMatkulFragmentBinding
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.model.UserMatkul
import com.khsmahasiswa.ui.adapter.NilaiMatkulAdapter
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.other.showLogAssert
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.system.moveNavigationTo

class DetailMatkulFragment : Fragment(R.layout.detail_matkul_fragment) {

    private val viewModel: DetailMatkulViewModel by viewModels {
        DetailMatkulViewModel.Factory(FirebaseDatabase())
    }

    private var jumlahNilai = 0
    private var jumlahSks = 0
    private var sksXPoin = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DetailMatkulFragmentBinding.bind(view)
        binding.viewModel = viewModel

        viewModel.data.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Changed -> {
                    val querySnapshot = response.data as QuerySnapshot
                    val data = querySnapshot.toObjects(UserMatkul::class.java)
                    showLogAssert("data", "${data[0].matkul}")
                    if (data.isNotEmpty()) {
                        val matkul = data[0].matkul

                        matkul?.forEach {
                            if (it.nilai == "A") {
                                jumlahNilai += 4
                                sksXPoin += it.sks?.toInt()?.times(4) ?: 0
                            }
                            else if (it.nilai == "B") {
                                jumlahNilai += 3
                                sksXPoin += it.sks?.toInt()?.times(3) ?: 0
                            }
                            else if (it.nilai == "C") {
                                jumlahNilai += 2
                                sksXPoin += it.sks?.toInt()?.times(2) ?: 0
                            }
                            else if (it.nilai == "D") {
                                jumlahNilai += 1
                                sksXPoin += it.sks?.toInt()?.times(4) ?: 0
                            }
                            else {
                                jumlahNilai += 0
                                sksXPoin += it.sks?.toInt()?.times(0) ?: 0
                            }

                            jumlahSks += it.sks!!
                        }

                        showLogAssert("jumlahNilai", jumlahNilai.toString())
                        showLogAssert("jumlahSks", jumlahSks.toString())
                        showLogAssert("sksXPoin", sksXPoin.toString())

                        binding.jumlahSks.text = jumlahSks.toString()
                        binding.ipSemester.text = (sksXPoin.toFloat() / jumlahSks.toFloat()).toString()

                        val userAdapter = NilaiMatkulAdapter(
                            matkul as MutableList<ModelMatakuliah>,
                            viewModel,
                            Constant.KEY_VIEW_ADMIN
                        )

                        binding.rcMatakuliah.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = userAdapter
                        }

                        SavedData.setObject(
                            Constant.KEY_USER_MATKUL, data[0]
                        )

                    } else {
                        SavedData.setObject(Constant.KEY_USER_MATKUL, UserMatkul())
                    }

                }
                is Response.Error -> showToast(requireContext(), response.error)
                is Response.Success -> TODO()
            }
        }

        viewModel.response.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> TODO()
                is Response.Error -> showToast(requireContext(), it.error)
                is Response.Success -> moveNavigationTo(view, R.id.action_detailMatkulFragment_self)
            }
        }

    }

}