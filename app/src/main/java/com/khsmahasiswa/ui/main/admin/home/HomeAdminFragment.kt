package com.khsmahasiswa.ui.main.admin.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.khsmahasiswa.R

class HomeAdminFragment : Fragment(R.layout.home_admin_fragment) {

    private val  viewModel: HomeAdminViewModel by viewModels {
        HomeAdminViewModel.Factory()
    }

}