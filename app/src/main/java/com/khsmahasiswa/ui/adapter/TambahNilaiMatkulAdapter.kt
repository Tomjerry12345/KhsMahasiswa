package com.khsmahasiswa.ui.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.khsmahasiswa.R
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.ui.main.admin.tambahMatkul.TambahMatkulViewModel
import com.khsmahasiswa.utils.other.Constant

class TambahNilaiMatkulAdapter(
    private val list: List<ModelMatakuliah>,
    val viewModel: TambahMatkulViewModel? = null
) :
    RecyclerView.Adapter<TambahNilaiMatkulHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): TambahNilaiMatkulHolder {
        return TambahNilaiMatkulHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_tambah_nilai_matkul, viewGroup, false)
        )
    }

    override fun getItemCount(): Int = list.size

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: TambahNilaiMatkulHolder, position: Int) {
        holder.bindProduk(list[position], viewModel)
    }
}

class TambahNilaiMatkulHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val mtvNamaMatkul = view.findViewById<MaterialTextView>(R.id.mtvNamaMatkul)
    private val dropdownNilai = view.findViewById<TextInputLayout>(R.id.dropdownNilai)
    private val mtvSemester = view.findViewById<MaterialTextView>(R.id.mtvSemester)
//    val dataUsers = SavedData.getDataUsers()

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    fun bindProduk(matakuliah: ModelMatakuliah, viewModel: TambahMatkulViewModel?) {
        mtvNamaMatkul.text = matakuliah.matakuliah
        mtvSemester.text = "semester: ${matakuliah.semester}"

        if (viewModel == null) {
            dropdownNilai.visibility = View.GONE
        } else {
            val dropdownNilai1 =  (dropdownNilai.editText as? AutoCompleteTextView)
            val nilaiAdapter = ArrayAdapter(view.context, R.layout.dropdown_custom_layout, Constant.listNilai)
            dropdownNilai1?.setAdapter(nilaiAdapter)
            dropdownNilai1?.setOnItemClickListener { adapterView, _, i, _ ->
                val getItem = adapterView.getItemAtPosition(i)
                matakuliah.nilai = getItem as String?
                viewModel.tambahMatkul(matakuliah)
            }
        }

//        mtvNamaDosen.text = matakuliah.namaDosen
//        mtvSks.text = "${matakuliah.sks} SKS"
//        mtvNilai.text = matakuliah.nilai
//        Glide
//            .with(view.context)
//            .load(produk.image)
//            .centerCrop()
//            .into(imgProduk);
//
//        mbDetail.setOnClickListener {
//            if (dataUsers?.jenisAkun == "Nelayan")
//                it.findNavController().navigate(R.id.action_homeNelayanFragment_to_detailProdukFragment)
//            else
//                it.findNavController().navigate(R.id.action_homePembeliFragment_to_detailProdukFragment2)
//
//            SavedData.saveDataProduk(produk)
//        }
    }

}