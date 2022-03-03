package com.khsmahasiswa.ui.main.admin.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.HomeAdminFragmentBinding
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.ui.adapter.UserAdapter
import com.khsmahasiswa.ui.main.admin.AdminActivity
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.system.moveIntentTo
import com.khsmahasiswa.utils.system.moveIntentToFinish

class HomeAdminFragment : Fragment(R.layout.home_admin_fragment) {

    private val  viewModel: HomeAdminViewModel by viewModels {
        HomeAdminViewModel.Factory(FirebaseDatabase())
    }

    private val testData = listOf<ModelUser>(
        ModelUser(
            nama = "Monkey D Luffy"
        ),
        ModelUser(
            nama = "Monkey D Garp"
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SavedData.init(requireActivity())

        val binding = HomeAdminFragmentBinding.bind(view)
        binding.viewModel = viewModel

        viewModel.data.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Response.Changed -> {
                    val list = ArrayList<ModelUser>()
                    val querySnapshot = response.data as QuerySnapshot
                    val data = querySnapshot.toObjects(ModelUser::class.java)
                    data.forEach {
                        if (it.nim != "001") list.add(it)
                    }
                    val userAdapter = UserAdapter(list, viewModel, Constant.KEY_VIEW_ADMIN)
                    binding.rcUser.apply {
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
                is Response.Error -> showToast(requireContext(), it.error)
                is Response.Success -> moveIntentToFinish(requireActivity(), AdminActivity())
            }
        }


    }

}