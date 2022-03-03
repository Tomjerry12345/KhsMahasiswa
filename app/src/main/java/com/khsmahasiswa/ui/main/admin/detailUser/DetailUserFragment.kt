package com.khsmahasiswa.ui.main.admin.detailUser

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase

class DetailUserFragment : Fragment(R.layout.detail_user_admin_fragment) {

    private val viewModel: DetailUserViewModel by viewModels {
        DetailUserViewModel.Factory(FirebaseDatabase())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}