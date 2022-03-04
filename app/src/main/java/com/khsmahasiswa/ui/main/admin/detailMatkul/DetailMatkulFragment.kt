package com.khsmahasiswa.ui.main.admin.detailMatkul

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.DetailMatkulFragmentBinding

class DetailMatkulFragment : Fragment(R.layout.detail_matkul_fragment) {

    private val viewModel: DetailMatkulViewModel by viewModels {
        DetailMatkulViewModel.Factory(FirebaseDatabase())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DetailMatkulFragmentBinding.bind(view)
        binding.viewModel = viewModel
    }

}