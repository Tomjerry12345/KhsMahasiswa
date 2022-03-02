package com.khsmahasiswa.ui.main.admin.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.khsmahasiswa.R
import com.khsmahasiswa.databinding.HomeAdminFragmentBinding
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.ui.adapter.UserAdapter

class HomeAdminFragment : Fragment(R.layout.home_admin_fragment) {

    private val  viewModel: HomeAdminViewModel by viewModels {
        HomeAdminViewModel.Factory()
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

        val binding = HomeAdminFragmentBinding.bind(view)
        binding.viewModel = viewModel

        val userAdapter = UserAdapter(testData)
        binding.rcUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }
    }

}