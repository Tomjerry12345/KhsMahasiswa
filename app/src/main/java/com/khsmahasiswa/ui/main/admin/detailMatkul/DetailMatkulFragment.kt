package com.khsmahasiswa.ui.main.admin.detailMatkul

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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

    private lateinit var matkul: MutableList<ModelMatakuliah>
    private lateinit var binding: DetailMatkulFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DetailMatkulFragmentBinding.bind(view)
        binding.viewModel = viewModel

        viewModel.data.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Changed -> {
                    val querySnapshot = response.data as QuerySnapshot
                    val data = querySnapshot.toObjects(UserMatkul::class.java)
//                    showLogAssert("data", "${data[0].matkul}")
                    if (data.isNotEmpty()) {
                        matkul = data[0].matkul as MutableList<ModelMatakuliah>
//                        binding.dropdownSemester.editText?.setText("Semester 1")

                        setData(matkul, "1")

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

        dropdownNilai()

    }

    fun dropdownNilai() {
        val dropdownNilai1 = (binding.dropdownSemester.editText as? AutoCompleteTextView)
        val nilaiAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_custom_layout, Constant.listSemester)
        binding.dropdownSemester.editText?.setText(Constant.listSemester[0])
        dropdownNilai1?.setAdapter(nilaiAdapter)
        dropdownNilai1?.setOnItemClickListener { adapterView, _, i, _ ->
            val getItem = adapterView.getItemAtPosition(i)
            var semester = ""
            when(getItem) {
                Constant.listSemester[0] -> semester = "1"
                Constant.listSemester[1] -> semester = "2"
                Constant.listSemester[2] -> semester = "3"
            }

            setData(matkul, semester)

//            moveNavigationTo(requireView(), R.id.action_detailMatkulFragment_self)
//            matakuliah.nilai = getItem as String?
//            viewModel.tambahMatkul(matakuliah)
        }
    }

    fun setData(matkul: MutableList<ModelMatakuliah>, semester: String) {
        jumlahSks = 0
        jumlahNilai = 0
        sksXPoin = 0
        val matkulSemester = matkul.filter {
            it.semester == semester
        }

        matkulSemester.forEach {
            if (it.nilai == "A") {
                jumlahNilai += 4
                sksXPoin += it.sks?.toInt()?.times(4) ?: 0
            } else if (it.nilai == "B") {
                jumlahNilai += 3
                sksXPoin += it.sks?.toInt()?.times(3) ?: 0
            } else if (it.nilai == "C") {
                jumlahNilai += 2
                sksXPoin += it.sks?.toInt()?.times(2) ?: 0
            } else if (it.nilai == "D") {
                jumlahNilai += 1
                sksXPoin += it.sks?.toInt()?.times(4) ?: 0
            } else {
                jumlahNilai += 0
                sksXPoin += it.sks?.toInt()?.times(0) ?: 0
            }

            jumlahSks += it.sks!!
        }

        showLogAssert("jumlahNilai", jumlahNilai.toString())
        showLogAssert("jumlahSks", jumlahSks.toString())
        showLogAssert("sksXPoin", sksXPoin.toString())

        binding.jumlahSks.text = jumlahSks.toString()
        binding.ipSemester.text =
            (sksXPoin.toFloat() / jumlahSks.toFloat()).toString()

        val userAdapter = NilaiMatkulAdapter(
            matkulSemester as MutableList<ModelMatakuliah>,
            viewModel,
            Constant.KEY_VIEW_ADMIN
        )

        binding.rcMatakuliah.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }

    }

}