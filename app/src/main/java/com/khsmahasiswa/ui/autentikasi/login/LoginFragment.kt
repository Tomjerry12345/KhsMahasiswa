package com.khsmahasiswa.ui.autentikasi.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.LoginFragmentBinding
import com.khsmahasiswa.ui.main.admin.AdminActivity
import com.khsmahasiswa.ui.main.mahasiswa.MahasiswaActivity
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.system.moveIntentTo
import com.khsmahasiswa.utils.system.moveIntentToFinish

class LoginFragment : Fragment(R.layout.login_fragment) {

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModel.Factory(FirebaseDatabase())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = LoginFragmentBinding.bind(view)
        binding.viewModel = viewModel

        viewModel.response.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Response.Changed -> {
                    moveIntentToFinish(requireActivity(), AdminActivity())
                }
                is Response.Error -> {
                    showToast(requireContext(), response.error)
                }
                is Response.Success -> {
                    moveIntentToFinish(requireActivity(), MahasiswaActivity())
                }

            }
        }
    }

}