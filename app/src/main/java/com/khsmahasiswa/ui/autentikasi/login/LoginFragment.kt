package com.khsmahasiswa.ui.autentikasi.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.LoginFragmentBinding
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.showDialog
import com.khsmahasiswa.utils.other.showLogAssert
import com.khsmahasiswa.utils.other.showToast

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
                is Response.Changed -> TODO()
                is Response.Error -> {
                    showToast(requireContext(), response.error)
                }
                is Response.Success -> {
                    showToast(requireContext(), response.succes)
                }

            }
        }
    }

}