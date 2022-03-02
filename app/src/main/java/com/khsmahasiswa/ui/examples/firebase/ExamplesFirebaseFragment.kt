package com.khsmahasiswa.ui.examples.firebase

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.khsmahasiswa.R
import com.khsmahasiswa.databinding.ExamplesFirebaseFragmentBinding

class ExamplesFirebaseFragment : Fragment(R.layout.examples_firebase_fragment) {

    private val viewModel: ExamplesFirebaseViewModel by viewModels {
        ExamplesFirebaseViewModel.Factory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = ExamplesFirebaseFragmentBinding.bind(view)
        binding.viewModel = viewModel
    }

}