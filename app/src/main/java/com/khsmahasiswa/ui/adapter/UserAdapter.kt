package com.khsmahasiswa.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
//    private val imgProduk = view.findViewById<AppCompatImageView>(R.id.imgProduk)

//    val dataUsers = SavedData.getDataUsers()

    @SuppressLint("SetTextI18n")
    fun bindProduk(produk: ModelUser) {
//        Glide
//            .with(view.context)
//            .load(produk.image)
//            .centerCrop()
//            .into(imgProduk);
//        mtvRating.text = "(${produk.rating!!})"
//        ratingProduk.rating = produk.rating!!
//        mtvNama.text = produk.kategori
//        mtvHargaPerEkor.text = "Harga / ekor : Rp. ${produk.hargaPerEkor}"
//        mtvHargaPerGompo.text = "Harga / gompo : Rp. ${produk.hargaPerGompo}"
//        mtvHargaPerKg.text = "Harga / kg : Rp. ${produk.hargaPerKg}"
//        mtvKecamatan.text = "${produk.kecamatan}"
//        mtvKelurahan.text = "${produk.kelurahan}"
//        mtvAlamat.text = "${produk.alamat}"
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