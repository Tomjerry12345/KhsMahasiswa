package com.khsmahasiswa.ui.main.admin.editUser

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.EditUserFragmentBinding
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.ui.main.admin.AdminActivity
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.system.moveIntentToFinish

class EditUserFragment : Fragment(R.layout.edit_user_fragment) {

    private val viewModel: EditUserViewModel by viewModels {
        EditUserViewModel.Factory(FirebaseDatabase())
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = SavedData.getObject(Constant.KEY_USER, ModelUser()) as ModelUser
        val binding = EditUserFragmentBinding.bind(view)
        binding.viewModel = viewModel

        viewModel.id = user.id.toString()
        viewModel.nama.value = user.nama
        viewModel.nim.value = user.nim
        viewModel.password.value = user.password
        viewModel.noTelepon.value = user.noTelepon
        viewModel.namaAyah.value = user.namaAyah
        viewModel.namaIbu.value = user.namaIbu
        viewModel.noTeleponOrangtua.value = user.noTeleponOrangtua

        viewModel.response.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Response.Changed -> TODO()
                is Response.Error -> showToast(requireContext(), response.error)
                is Response.Success -> moveIntentToFinish(requireActivity(), AdminActivity())
            }
        }
    }

}