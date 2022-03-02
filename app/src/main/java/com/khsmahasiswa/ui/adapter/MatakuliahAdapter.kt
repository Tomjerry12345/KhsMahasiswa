package com.khsmahasiswa.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.khsmahasiswa.R
import com.khsmahasiswa.model.ModelMatakuliah

class MatakuliahAdapter(private val list: List<ModelMatakuliah>) : RecyclerView.Adapter<MatakuliahHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MatakuliahHolder {
        return MatakuliahHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_matakuliah, viewGroup, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MatakuliahHolder, position: Int) {
        holder.bindProduk(list[position])
    }
}

class MatakuliahHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val mtvMatakuliah = view.findViewById<MaterialTextView>(R.id.mtvMatakuliah)
    private val mtvNamaDosen = view.findViewById<MaterialTextView>(R.id.mtvNamadosen)
    private val mtvSks = view.findViewById<MaterialTextView>(R.id.mtvSks)
    private val mtvNilai = view.findViewById<MaterialTextView>(R.id.mtvNilai)
//    val dataUsers = SavedData.getDataUsers()

    @SuppressLint("SetTextI18n")
    fun bindProduk(matakuliah: ModelMatakuliah) {
        mtvMatakuliah.text = matakuliah.matakuliah
        mtvNamaDosen.text = matakuliah.namaDosen
        mtvSks.text = "${matakuliah.sks} SKS"
        mtvNilai.text = matakuliah.nilai
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