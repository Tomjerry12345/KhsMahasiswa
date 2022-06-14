package com.khsmahasiswa.ui.main.admin.viewNilai

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import com.khsmahasiswa.utils.system.moveNavigationTo

class ViewNilaiFragment : Fragment(R.layout.view_nilai_fragment) {

    private val viewModel: ViewNilaiViewModel by viewModels {
        ViewNilaiViewModel.Factory(FirebaseDatabase())
    }

    private lateinit var binding: ViewNilaiFragmentBinding

    val user = SavedData.getObject(Constant.KEY_USER, ModelUser()) as ModelUser

    var jumlahNilai = 0
    var jumlahSks = 0
    var sksXPoin = 0
    var ipk = ""

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
//                moveNavigationTo(requireView(), R.id.action_viewNilaiFragment_to_detailUserFragment)
            }
        }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ViewNilaiFragmentBinding.bind(view)

//        permission()

        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot
                    val data = querySnapshot.toObjects(UserMatkul::class.java)
                    if (data.isNotEmpty()) {
                        val matkul = data[0].matkul as MutableList<ModelMatakuliah>
                        hitungIpk(matkul)

                        val viewNilaiMatkulAdapter =
                            ViewNilaiMatkulAdapter(matkul, user, jumlahSks, ipk)
                        binding.rcNilai.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = viewNilaiMatkulAdapter
                        }
                    }

//                    showLogAssert("data", "$matkul")
                }
                is Response.Error -> showLogAssert("error", it.error)
                is Response.Success -> TODO()
            }
        }

        binding.mbShare.setOnClickListener {
            testExportPdf()
        }
    }

    private fun permission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                // You can use the API that requires the permission.
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
    //                requestPermissionLauncher.launch(
    //                    Manifest.permission.WRITE_EXTERNAL_STORAGE
    //                )
            }
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
//        binding.rcNilai.isDrawingCacheEnabled = true
        val bitmap = documentUtils.getScreenshotFromRecyclerView(binding.rcNilai)
//        val bitmap = documentUtils.getViewBitmap(binding.rcNilai)
//        if (bitmap != null) {
//            documentUtils.createPdfFromBitmap(bitmap)
//            binding.mbShare.visibility = View.VISIBLE
//        } else {
//            showToast(requireContext(), "Bitmap not found")
//        }

        bitmap?.let { documentUtils.createPdfFromBitmap(it) }
//        user.noTeleponOrangtua?.let { showLogAssert("noTeleponOrangtua", it) }
        documentUtils.shareFile(
            requireActivity(),
            user.noTeleponOrangtua, Constant.WHATSAPP_KEY
        )
    }

    fun hitungIpk(matkul: List<ModelMatakuliah>?) {
        matkul?.forEach {
            when (it.nilai) {
                "A" -> {
                    jumlahNilai += 4
                    sksXPoin += it.sks?.times(4) ?: 0
                    jumlahSks += it.sks!!
                }
                "B" -> {
                    jumlahNilai += 3
                    sksXPoin += it.sks?.times(3) ?: 0
                    jumlahSks += it.sks!!
                }
                "C" -> {
                    jumlahNilai += 2
                    sksXPoin += it.sks?.times(2) ?: 0
                    jumlahSks += it.sks!!
                }
                "D" -> {
                    jumlahNilai += 1
                    sksXPoin += it.sks?.times(1) ?: 0
                }
                else -> {
                    jumlahNilai += 0
                    sksXPoin += it.sks?.times(0) ?: 0
                }
            }
        }

        showLogAssert("jumlahNilai", jumlahNilai.toString())
        showLogAssert("jumlahSks", jumlahSks.toString())
        showLogAssert("sksXPoin", sksXPoin.toString())

//        binding.jumlahSks.text = jumlahSks.toString()
        ipk = String.format("%.2f", (sksXPoin.toFloat() / jumlahSks.toFloat()))
    }

}