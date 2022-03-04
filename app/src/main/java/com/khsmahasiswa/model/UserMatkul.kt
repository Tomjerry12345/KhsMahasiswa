package com.khsmahasiswa.model

data class UserMatkul(
    val id: String? = null,
    val idUser: String? = null,
    val matkul: List<ModelMatakuliah>? = null,
)
