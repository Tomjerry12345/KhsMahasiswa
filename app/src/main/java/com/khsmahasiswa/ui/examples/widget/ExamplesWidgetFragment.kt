package com.khsmahasiswa.ui.examples.widget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.khsmahasiswa.R
import com.khsmahasiswa.databinding.ExamplesWidgetFragmentBinding

class ExamplesWidgetFragment : Fragment(R.layout.examples_widget_fragment) {

    private val viewModel: ExamplesWidgetViewModel by viewModels {
        ExamplesWidgetViewModel.Factory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = ExamplesWidgetFragmentBinding.bind(view)
        binding.viewModel = viewModel
    }

}