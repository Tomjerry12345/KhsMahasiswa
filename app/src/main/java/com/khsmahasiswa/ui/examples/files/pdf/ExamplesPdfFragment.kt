package com.khsmahasiswa.ui.examples.files.pdf

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.khsmahasiswa.R
import com.khsmahasiswa.databinding.ExamplesPdfFragmentBinding
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.system.DocumentUtils

class ExamplesPdfFragment : Fragment(R.layout.examples_pdf_fragment) {

    private lateinit var viewModel: ExamplesPdfViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = ExamplesPdfFragmentBinding.bind(view)

        val linear = binding.linearId

        binding.mbExportPdf.setOnClickListener {
            val documentUtils = DocumentUtils(requireActivity())

            val bitmap = documentUtils.createBitmapFromLayout(
                linear,
                linear.width,
                linear.height
            )
            if (bitmap != null) {
                documentUtils.createPdfFromBitmap(bitmap)
            } else {
                showToast(requireContext(), "Bitmap not found")
            }
        }
    }


}