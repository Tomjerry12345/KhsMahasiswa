package com.khsmahasiswa.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.khsmahasiswa.R
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.utils.other.showLogAssert

class ViewNilaiMatkulAdapter(val matkul: MutableList<ModelMatakuliah>) : RecyclerView.Adapter<ViewNilaiMatkulHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewNilaiMatkulHolder {
        return ViewNilaiMatkulHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_view_nilai, viewGroup, false)
        )
    }

    override fun getItemCount(): Int = matkul.size + 1

    override fun onBindViewHolder(holder: ViewNilaiMatkulHolder, position: Int) {
        showLogAssert("position", "$position")
        showLogAssert("size", "${matkul.size}")
        if (position == matkul.size) {
            holder.bindProduk(position, null, matkul.size)
        } else {
            holder.bindProduk(position, matkul[position], matkul.size)
        }

    }
}

class ViewNilaiMatkulHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val mtvNama = view.findViewById<MaterialTextView>(R.id.mtvNama)
    private val mtvMatakuliah = view.findViewById<MaterialTextView>(R.id.mtvMatakuliah)
    private val mtvSks = view.findViewById<MaterialTextView>(R.id.mtvSks)
    private val mtvNilai = view.findViewById<MaterialTextView>(R.id.mtvNilai)

    @SuppressLint("SetTextI18n")
    fun bindProduk(position: Int, matkul: ModelMatakuliah?, size: Int) {
        if (position > 0)
            mtvNama.visibility = View.GONE

        if (matkul == null) {
            mtvMatakuliah.text = "Total sks"
            mtvSks.text = "100"
            mtvNilai.text = ""
        } else {
            mtvMatakuliah.text = matkul.matakuliah
            mtvSks.text = matkul.sks.toString()
            mtvNilai.text = matkul.nilai
        }


    }
}