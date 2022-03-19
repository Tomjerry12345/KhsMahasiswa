package com.khsmahasiswa.ui.main.mahasiswa.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.HomeMahasiswaFragmentBinding
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.model.UserMatkul
import com.khsmahasiswa.ui.adapter.NilaiMatkulAdapter
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.other.showLogAssert
import com.khsmahasiswa.utils.other.showToast

class HomeMahasiswaFragment : Fragment(R.layout.home_mahasiswa_fragment) {

    private val viewModel: HomeMahasiswaViewModel by viewModels {
        HomeMahasiswaViewModel.Factory(FirebaseDatabase())
    }

    private val testData = listOf(
        ModelMatakuliah(
            matakuliah = "Algoritma pemrograman",
            namaDosen = "Pak syafar",
            nilai = "A+",
            sks = 4
        ),
        ModelMatakuliah(
            matakuliah = "Pemrograman testruktur",
            namaDosen = "Pak ridwan",
            nilai = "B+",
            sks = 3
        ),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = HomeMahasiswaFragmentBinding.bind(view)
        binding.viewModel = viewModel

        viewModel.data.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Changed -> {
                    val querySnapshot = response.data as QuerySnapshot
                    val data = querySnapshot.toObjects(UserMatkul::class.java)
                    showLogAssert("data", "${data[0].matkul}")
                    if (data.isNotEmpty()) {
                        val matkul = data[0].matkul as MutableList<ModelMatakuliah>
//                        binding.dropdownSemester.editText?.setText("Semester 1")

//                        setData(matkul, "1")

                        val homeMahasiswaAdapter = NilaiMatkulAdapter(
                            matkul,
                            viewModel,
                            Constant.KEY_VIEW_ADMIN
                        )

                        binding.rcMatakuliah.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = homeMahasiswaAdapter
                        }

//                        SavedData.setObject(
//                            Constant.KEY_USER_MATKUL, data[0]
//                        )

                    }
//                    else {
//                        SavedData.setObject(Constant.KEY_USER_MATKUL, UserMatkul())
//                    }

                }
                is Response.Error -> showToast(requireContext(), response.error)
                is Response.Success -> TODO()
            }

//        val matakuliahAdapter = MatakuliahAdapter(testData)

        }

    }

}