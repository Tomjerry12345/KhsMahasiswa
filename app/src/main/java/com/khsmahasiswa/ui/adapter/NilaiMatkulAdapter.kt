package com.khsmahasiswa.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.khsmahasiswa.R
import com.khsmahasiswa.model.ModelMatakuliah
import com.khsmahasiswa.ui.main.admin.detailMatkul.DetailMatkulViewModel
import com.khsmahasiswa.utils.other.Constant

class NilaiMatkulAdapter(
    private val list: MutableList<ModelMatakuliah>,
    val viewModel: ViewModel,
    val key: String
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
        holder.bindProduk(list[position], viewModel, key, position)
    }
}

class NilaiMatkulHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val mtvNamaMatkul = view.findViewById<MaterialTextView>(R.id.mtvNamaMatkul)
    private val mtvSemester = view.findViewById<MaterialTextView>(R.id.mtvSemester)
    private val mtvNilai = view.findViewById<MaterialTextView>(R.id.mtvNilai)
    private val mbHapus = view.findViewById<MaterialButton>(R.id.mbHapus)
    private val mbEdit = view.findViewById<MaterialButton>(R.id.mbEdit)
//    val dataUsers = SavedData.getDataUsers()

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    fun bindProduk(matakuliah: ModelMatakuliah, viewModel: ViewModel, key: String, position: Int) {
        mtvNamaMatkul.text = matakuliah.matakuliah
        mtvSemester.text = "semester: ${matakuliah.semester}"
        mtvNilai.text = matakuliah.nilai

        if (matakuliah.nilai == "D" || matakuliah.nilai == "E") {
            view.setBackgroundColor(Color.RED)
            mtvNamaMatkul.setTextColor(Color.WHITE)
            mtvSemester.setTextColor(Color.WHITE)
            mtvNilai.setTextColor(Color.WHITE)
        }

        if (key == Constant.KEY_VIEW_USER) {
            mbEdit.visibility = View.GONE
            mbHapus.visibility = View.GONE
        }

        mbEdit.setOnClickListener {
            if (key == Constant.KEY_VIEW_ADMIN) {
                val viewModel1 = viewModel as DetailMatkulViewModel
                viewModel1.onEditMatkul(matakuliah.matakuliah, it.context)
            }
        }

        mbHapus.setOnClickListener {
            if (key == Constant.KEY_VIEW_ADMIN) {
                val viewModel1 = viewModel as DetailMatkulViewModel
                viewModel1.onHapusMatkul(matakuliah.matakuliah, it.context)
            }
        }
    }

}