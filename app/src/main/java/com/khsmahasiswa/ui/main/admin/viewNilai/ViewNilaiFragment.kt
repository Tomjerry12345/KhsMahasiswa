package com.khsmahasiswa.ui.main.admin.viewNilai

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.khsmahasiswa.R
import com.khsmahasiswa.database.firebase.FirebaseDatabase
import com.khsmahasiswa.databinding.ViewNilaiFragmentBinding
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.model.UserMatkul
import com.khsmahasiswa.ui.adapter.ViewNilaiMatkulAdapter
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.other.showLogAssert
import com.khsmahasiswa.utils.system.DocumentUtils

class ViewNilaiFragment : Fragment(R.layout.view_nilai_fragment) {

    private val viewModel: ViewNilaiViewModel by viewModels {
        ViewNilaiViewModel.Factory(FirebaseDatabase())
    }

    private lateinit var binding: ViewNilaiFragmentBinding

    val user = SavedData.getObject(Constant.KEY_USER, ModelUser()) as ModelUser

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ViewNilaiFragmentBinding.bind(view)

        viewModel.data.observe(viewLifecycleOwner) {
            when(it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot
                    val data = querySnapshot.toObjects(UserMatkul::class.java)
                    val matkul = data[0].matkul as MutableList<ModelMatakuliah>
                    showLogAssert("data", "$matkul")

                    val viewNilaiMatkulAdapter = ViewNilaiMatkulAdapter(matkul)
                    binding.rcNilai.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = viewNilaiMatkulAdapter
                    }


                }
                is Response.Error -> showLogAssert("error", it.error)
                is Response.Success -> TODO()
            }
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
        val bitmap = documentUtils.createBitmapRecyclerView(binding.rcNilai)
//        if (bitmap != null) {
//            documentUtils.createPdfFromBitmap(bitmap)
//            binding.mbShare.visibility = View.VISIBLE
//        } else {
//            showToast(requireContext(), "Bitmap not found")
//        }

        documentUtils.createPdfFromBitmap(bitmap)
        documentUtils.shareFile(requireActivity(), user.noTeleponOrangtua, Constant.WHATSAPP_KEY)
    }

}