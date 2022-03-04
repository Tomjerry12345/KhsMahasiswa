package com.khsmahasiswa.ui.main.mahasiswa.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.khsmahasiswa.R
import com.khsmahasiswa.databinding.HomeMahasiswaFragmentBinding
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.ui.adapter.MatakuliahAdapter

class HomeMahasiswaFragment : Fragment(R.layout.home_mahasiswa_fragment) {

    private val viewModel: HomeMahasiswaViewModel by viewModels {
        HomeMahasiswaViewModel.Factory()
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

        val matakuliahAdapter = MatakuliahAdapter(testData)
        binding.rcMatakuliah.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = matakuliahAdapter
        }


    }

}