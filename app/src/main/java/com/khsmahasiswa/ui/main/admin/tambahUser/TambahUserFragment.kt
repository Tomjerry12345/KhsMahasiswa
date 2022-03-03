package com.khsmahasiswa.ui.main.admin.tambahUser

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.TambahUserFragmentBinding
import com.khsmahasiswa.ui.main.admin.AdminActivity
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.system.moveIntentTo
import com.khsmahasiswa.utils.system.moveIntentToFinish

class TambahUserFragment : Fragment(R.layout.tambah_user_fragment) {

    private val viewModel: TambahUserViewModel by viewModels {
        TambahUserViewModel.Factory(FirebaseDatabase())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding =TambahUserFragmentBinding.bind(view)
        binding.viewModel = viewModel

        viewModel.response.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Response.Changed -> TODO()
                is Response.Error -> showToast(requireContext(), response.error)
                is Response.Success -> moveIntentToFinish(requireActivity(), AdminActivity())
            }
        }
    }

}