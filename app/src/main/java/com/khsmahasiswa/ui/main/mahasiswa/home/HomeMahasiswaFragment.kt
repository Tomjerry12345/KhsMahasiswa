package com.khsmahasiswa.ui.main.mahasiswa.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.khsmahasiswa.R

class HomeMahasiswaFragment : Fragment(R.layout.home_mahasiswa_fragment) {

    private val viewModel: HomeMahasiswaViewModel by viewModels {
        HomeMahasiswaViewModel.Factory()
    }

}