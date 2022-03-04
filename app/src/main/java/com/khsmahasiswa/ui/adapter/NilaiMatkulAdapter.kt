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
import com.khsmahasiswa.model.UserMatkul
import com.khsmahasiswa.ui.main.admin.tambahMatkul.TambahMatkulViewModel
import com.khsmahasiswa.utils.other.Constant

class NilaiMatkulAdapter(
    private val list: MutableList<ModelMatakuliah>
) :
    RecyclerView.Adapter<NilaiMatkulHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): NilaiMatkulHolder {
        return NilaiMatkulHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_nilai_matkul, viewGroup, false)
        )
    }

    override fun getItemCount(): Int = list.size

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: NilaiMatkulHolder, position: Int) {
        holder.bindProduk(list[position])
    }
}

class NilaiMatkulHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val mtvNamaMatkul = view.findViewById<MaterialTextView>(R.id.mtvNamaMatkul)
    private val mtvSemester = view.findViewById<MaterialTextView>(R.id.mtvSemester)
    private val mtvNilai = view.findViewById<MaterialTextView>(R.id.mtvNilai)
//    val dataUsers = SavedData.getDataUsers()

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    fun bindProduk(matakuliah: ModelMatakuliah) {
        mtvNamaMatkul.text = matakuliah.matakuliah
        mtvSemester.text = "semester: ${matakuliah.semester}"
        mtvNilai.text = matakuliah.nilai

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