package com.khsmahasiswa.ui.main.mahasiswa.home

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.HomeMahasiswaFragmentBinding
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.model.UserMatkul
import com.khsmahasiswa.ui.adapter.NilaiMatkulAdapter
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.other.showToast


class HomeMahasiswaFragment : Fragment(R.layout.home_mahasiswa_fragment) {

    private val viewModel: HomeMahasiswaViewModel by viewModels {
        HomeMahasiswaViewModel.Factory(FirebaseDatabase(), requireActivity())
    }

    val user = SavedData.getObject(Constant.KEY_USER, ModelUser()) as ModelUser

    private lateinit var binding: HomeMahasiswaFragmentBinding

    private lateinit var matkul: MutableList<ModelMatakuliah>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setHasOptionsMenu(true)
        binding = HomeMahasiswaFragmentBinding.bind(view)
        binding.viewModel = viewModel

        binding.mtvNamaUser.text = user.nama

        viewModel.data.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Changed -> {
                    val querySnapshot = response.data as QuerySnapshot
                    val data = querySnapshot.toObjects(UserMatkul::class.java)
//                    showLogAssert("data", "${data[0].matkul}")
                    if (data.isNotEmpty()) {
                        matkul = data[0].matkul as MutableList<ModelMatakuliah>
                        setData(matkul, "")
                    }

                }
                is Response.Error -> showToast(requireContext(), response.error)
                is Response.Success -> TODO()
            }
        }

        dropdownNilai()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    private fun dropdownNilai() {
        val dropdownNilai1 = (binding.dropdownSemester.editText as? AutoCompleteTextView)
        val nilaiAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_custom_layout,
                Constant.listSemesterUser
            )
        binding.dropdownSemester.editText?.setText(Constant.listSemesterUser[0])
        dropdownNilai1?.setAdapter(nilaiAdapter)
        dropdownNilai1?.setOnItemClickListener { adapterView, _, i, _ ->
            val getItem = adapterView.getItemAtPosition(i)
            var semester = ""
            when (getItem) {
                Constant.listSemesterUser[0] -> semester = ""
                Constant.listSemesterUser[1] -> semester = "1"
                Constant.listSemesterUser[2] -> semester = "2"
                Constant.listSemesterUser[3] -> semester = "3"
            }

            if (::matkul.isInitialized) {
                setData(matkul, semester)
            }

        }
    }

    private fun setData(matkul: MutableList<ModelMatakuliah>, semester: String) {
        var sksLulus = 0
        var jumlahSks = 0
        var jumlahNilai = 0
        var sksXPoin = 0F
        var matkulSemester: List<ModelMatakuliah> = if (semester == "") {
            binding.mtvIpSemester.text = "IP Komulatif"
            matkul
        } else {
            binding.mtvIpSemester.text = "IP Semester"
            matkul.filter {
                it.semester == semester
            }
        }


        matkulSemester.forEach {
            when (it.nilai) {
                "A" -> {
                    jumlahNilai += 4
                    sksXPoin += it.sks?.times(4) ?: 0
                    sksLulus += it.sks!!
                }
                "A-" -> {
                    jumlahNilai += 4
                    sksXPoin += (it.sks?.times(3.7F) ?:  0) as Float
                    sksLulus += it.sks!!
                }
                "B+" -> {
                    jumlahNilai += 4
                    sksXPoin += (it.sks?.times(3.3F) ?:  0) as Float
                    sksLulus += it.sks!!
                }
                "B" -> {
                    jumlahNilai += 3
                    sksXPoin += it.sks?.times(3) ?: 0
                    sksLulus += it.sks!!
                }
                "B-" -> {
                    jumlahNilai += 4
                    sksXPoin += (it.sks?.times(2.7F) ?:  0) as Float
                    sksLulus += it.sks!!
                }
                "C+" -> {
                    jumlahNilai += 4
                    sksXPoin += (it.sks?.times(2.3F) ?:  0) as Float
                    sksLulus += it.sks!!
                }
                "C" -> {
                    jumlahNilai += 2
                    sksXPoin += it.sks?.times(2) ?: 0
                    sksLulus += it.sks!!
                }
                "C-" -> {
                    jumlahNilai += 4
                    sksXPoin += (it.sks?.times(1.7F) ?:  0) as Float
                    sksLulus += it.sks!!
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

            jumlahSks += it.sks!!
        }


        binding.jumlahSks.text = sksLulus.toString()
        binding.ipSemester.text = String.format("%.2f", (sksXPoin.toFloat() / jumlahSks.toFloat()))

        val homeMahasiswaAdapter = NilaiMatkulAdapter(
            matkulSemester as MutableList<ModelMatakuliah>,
            viewModel,
            Constant.KEY_VIEW_USER
        )

        binding.rcMatakuliah.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeMahasiswaAdapter
        }

    }

}