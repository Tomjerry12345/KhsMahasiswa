package com.khsmahasiswa.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.khsmahasiswa.R
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.utils.other.showLogAssert

class ViewNilaiMatkulAdapter(
    val matkul: MutableList<ModelMatakuliah>,
    val user: ModelUser,
    val jumlahSks: Int,
    val ipk: String
) :
    RecyclerView.Adapter<ViewNilaiMatkulHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewNilaiMatkulHolder {
        return ViewNilaiMatkulHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_view_nilai, viewGroup, false)
        )
    }

    override fun getItemCount(): Int = matkul.size

    override fun onBindViewHolder(holder: ViewNilaiMatkulHolder, position: Int) {
        showLogAssert("position", "$position")
        showLogAssert("size", "${matkul.size}")
        if (position == matkul.size) {
            holder.bindProduk(position, null, matkul.size, user, jumlahSks, ipk)
        } else {
            holder.bindProduk(position, matkul[position], matkul.size, user, jumlahSks, ipk)
        }

    }
}

class ViewNilaiMatkulHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val mtvNama = view.findViewById<MaterialTextView>(R.id.mtvNama)
    private val mtvMatakuliah = view.findViewById<MaterialTextView>(R.id.mtvMatakuliah)
    private val mtvSks = view.findViewById<MaterialTextView>(R.id.mtvSks)
    private val mtvNilai = view.findViewById<MaterialTextView>(R.id.mtvNilai)
    private val mtvKet = view.findViewById<MaterialCardView>(R.id.keterangan)

    @SuppressLint("SetTextI18n")
    fun bindProduk(
        position: Int,
        matkul: ModelMatakuliah?,
        size: Int,
        user: ModelUser,
        jumlahSks: Int,
        ipk: String
    ) {
        mtvNama.text = "Nama : ${user.nama} \n" +
                "NIM: ${user.nim}"
        if (position > 0) {
            mtvNama.visibility = View.GONE
            mtvKet.visibility = View.GONE
        }

        mtvMatakuliah.text = matkul?.matakuliah
        mtvSks.text = matkul?.sks.toString()
        mtvNilai.text = matkul?.nilai

//        if (matkul == null) {
//            mtvMatakuliah.text = "Total sks"
//            mtvSks.text = "$jumlahSks"
//            mtvNilai.text = ipk
//        } else {
//            mtvMatakuliah.text = matkul.matakuliah
//            mtvSks.text = matkul.sks.toString()
//            mtvNilai.text = matkul.nilai
//        }
    }

}