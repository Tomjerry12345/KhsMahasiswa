package com.khsmahasiswa.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.khsmahasiswa.R
import com.khsmahasiswa.model.ModelUser

class UserAdapter(private val list: List<ModelUser>) : RecyclerView.Adapter<UserHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): UserHolder {
        return UserHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_user, viewGroup, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bindProduk(list[position])
    }
}

class UserHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val mtvNama = view.findViewById<MaterialTextView>(R.id.mtvNama)
    private val btnEdit = view.findViewById<ImageButton>(R.id.btnEdit)
    private val btnHapus = view.findViewById<ImageButton>(R.id.btnHapus)
//    val dataUsers = SavedData.getDataUsers()

    @SuppressLint("SetTextI18n")
    fun bindProduk(user: ModelUser) {
        mtvNama.text = user.nama
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