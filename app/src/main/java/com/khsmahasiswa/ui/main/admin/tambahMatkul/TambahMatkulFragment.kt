package com.khsmahasiswa.ui.main.admin.tambahMatkul

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.TambahMatkulFragmentBinding

class TambahMatkulFragment : Fragment(R.layout.tambah_matkul_fragment) {

    private val viewModel: TambahMatkulViewModel by viewModels {
        TambahMatkulViewModel.Factory(FirebaseDatabase())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = TambahMatkulFragmentBinding.bind(view)
    }

}