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

class ViewNilaiMatkulAdapter() : RecyclerView.Adapter<ViewNilaiMatkulHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewNilaiMatkulHolder {
        return ViewNilaiMatkulHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_matakuliah, viewGroup, false)
        )
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ViewNilaiMatkulHolder, position: Int) {
        holder.bindProduk()
    }
}

class ViewNilaiMatkulHolder(val view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bindProduk() {
    }
}