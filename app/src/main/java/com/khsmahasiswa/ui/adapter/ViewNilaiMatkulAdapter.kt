package com.khsmahasiswa.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.khsmahasiswa.R
import com.khsmahasiswa.model.ModelMatakuliah

class ViewNilaiMatkulAdapter(val matkul: MutableList<ModelMatakuliah>) : RecyclerView.Adapter<ViewNilaiMatkulHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewNilaiMatkulHolder {
        return ViewNilaiMatkulHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_view_nilai, viewGroup, false)
        )
    }

    override fun getItemCount(): Int = matkul.size

    override fun onBindViewHolder(holder: ViewNilaiMatkulHolder, position: Int) {
        holder.bindProduk(position, matkul[position])
    }
}

class ViewNilaiMatkulHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val mtvNama = view.findViewById<MaterialTextView>(R.id.mtvNama)
    private val mtvMatakuliah = view.findViewById<MaterialTextView>(R.id.mtvMatakuliah)
    private val mtvSks = view.findViewById<MaterialTextView>(R.id.mtvSks)
    private val mtvNilai = view.findViewById<MaterialTextView>(R.id.mtvNilai)

    @SuppressLint("SetTextI18n")
    fun bindProduk(position: Int, matkul: ModelMatakuliah) {
        if (position > 0)
            mtvNama.visibility = View.GONE

        mtvMatakuliah.text = matkul.matakuliah
        mtvSks.text = matkul.sks.toString()
        mtvNilai.text = matkul.nilai
    }
}