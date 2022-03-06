package com.khsmahasiswa.ui.main.admin.tambahMatkul

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.TambahMatkulFragmentBinding
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.model.UserMatkul
import com.khsmahasiswa.ui.adapter.NilaiMatkulAdapter
import com.khsmahasiswa.ui.adapter.TambahNilaiMatkulAdapter
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.other.showLogAssert
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.system.moveIntentTo
import com.khsmahasiswa.utils.system.moveNavigationTo

class TambahMatkulFragment : Fragment(R.layout.tambah_matkul_fragment) {

    private val viewModel: TambahMatkulViewModel by viewModels {
        TambahMatkulViewModel.Factory(FirebaseDatabase())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataUserMatkul = SavedData.getObject(Constant.KEY_USER_MATKUL, UserMatkul()) as UserMatkul

        showLogAssert("dataUserMatkul", "$dataUserMatkul")

        val binding = TambahMatkulFragmentBinding.bind(view)
        binding.viewModel = viewModel

        viewModel.dataUserMatkul = dataUserMatkul

        val dropdownNilai1 =  (binding.pilihSemester.editText as? AutoCompleteTextView)
        val nilaiAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_custom_layout, Constant.listSemester)
        dropdownNilai1?.setAdapter(nilaiAdapter)
        dropdownNilai1?.setOnItemClickListener { adapterView, _, i, _ ->
//            val getItem = adapterView.getItemAtPosition(i)
//            matakuliah.nilai = getItem as String?
//            viewModel.tambahMatkul(matakuliah)
        }

        viewModel.data.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Response.Changed -> {
                    val querySnapshot = response.data as QuerySnapshot
                    val data = querySnapshot.toObjects(ModelMatakuliah::class.java)
                    val matkul = dataUserMatkul.matkul
                    if (matkul != null) {
                        for (i in matkul) {
                            data.removeIf {
                                i.matakuliah == it.matakuliah
                            }
                        }
                    }
                    val userAdapter = TambahNilaiMatkulAdapter(data, viewModel)
                    binding.rcMatakuliah.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = userAdapter
                    }
                }
                is Response.Error -> showToast(requireContext(), response.error)
                is Response.Success -> TODO()
            }
        }

        viewModel.response.observe(viewLifecycleOwner) {
            when(it) {
                is Response.Changed -> TODO()
                is Response.Error -> TODO()
                is Response.Success ->
                    moveNavigationTo(view, R.id.action_tambahMatkulFragment_to_detailMatkulFragment)
            }
        }
    }

}