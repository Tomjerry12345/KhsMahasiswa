package com.khsmahasiswa.ui.main.admin.viewNilai

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.ViewNilaiFragmentBinding
import com.khsmahasiswa.ui.adapter.ViewNilaiMatkulAdapter
import com.khsmahasiswa.utils.other.showToast
import com.khsmahasiswa.utils.system.DocumentUtils

class ViewNilaiFragment : Fragment(R.layout.view_nilai_fragment) {

    private val viewModel: ViewNilaiViewModel by viewModels {
        ViewNilaiViewModel.Factory(FirebaseDatabase())
    }

    private lateinit var binding: ViewNilaiFragmentBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ViewNilaiFragmentBinding.bind(view)

        val viewNilaiMatkulAdapter = ViewNilaiMatkulAdapter()
        binding.rcNilai.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewNilaiMatkulAdapter
        }

        binding.mbShare.setOnClickListener {
            testExportPdf()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun testExportPdf() {
        binding.mbShare.visibility = View.GONE
        val documentUtils = DocumentUtils(requireActivity())
//        val bitmap = documentUtils.createBitmapFromLayout(
//            binding.rootLayout,
//            binding.rootLayout.width,
//            binding.rootLayout.height
//        )
//        val bitmap = documentUtils.createBitmapRecyclerView(binding.rcNilai)
//        if (bitmap != null) {
//            documentUtils.createPdfFromBitmap(bitmap)
//            binding.mbShare.visibility = View.VISIBLE
//        } else {
//            showToast(requireContext(), "Bitmap not found")
//        }

        documentUtils.createPdf()
    }

}