package com.khsmahasiswa.database.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khsmahasiswa.model.ModelUser
import com.khsmahasiswa.model.examples.ExamplesModel
import com.khsmahasiswa.utils.network.Response
import com.khsmahasiswa.utils.local.SavedData
import com.khsmahasiswa.utils.other.Constant
import com.khsmahasiswa.utils.other.showLogAssert
import kotlinx.coroutines.tasks.await

class FirebaseDatabase {

    private val db = Firebase.firestore

    suspend fun addData(path: String, colection: String? = null, data: Any): Response {
        return try {
            if (colection.isNullOrEmpty()) {
                val response = db.collection(path).add(data).await()
                Response.Changed(response.id)
            } else {
                db.collection(path).document(colection).set(data)
                Response.Success("Berhasil tambah data")
            }

        } catch (e: Exception) {
            Response.Error("${e.message}")
        }

    }

    suspend fun getAllData(
        reference: String
    ): Response {
        return try {
            val data = db
                .collection(reference)
                .get()
                .await()

            Response.Changed(data)

        } catch (e: Exception) {
            showLogAssert("error", "${e.message}")
            Response.Error("${e.message}")
        }
    }

    suspend fun getDataSpecific(
        reference: String,
        key: String,
        value: String
    ): Response {
        return try {
            val data = db
                .collection(reference)
                .whereEqualTo(key, value)
                .get()
                .await()
            Response.Changed(data)

        } catch (e: Exception) {
            showLogAssert("error", "${e.message}")
            Response.Error("${e.message}")
        }
    }

    suspend fun update(
        reference: String,
        colection: String,
        update: String? = null,
        data: Any,
        msg: String = ""
    ): Response {
        return try {
            if (update != null) {
                db.collection(reference)
                    .document(colection)
                    .update(update, data)
                    .await()
            } else {
                db.collection(reference)
                    .document(colection)
                    .set(data)
                    .await()
            }

            Response.Success(msg)

        } catch (e: Exception) {
            showLogAssert("error", "${e.message}")
            Response.Error("${e.message}")
        }
    }

    suspend fun delete(reference: String, colection: String, msg: String): Response {
        return try {
            db.collection(reference)
                .document(colection)
                .delete()
                .await()

            Response.Success(msg)

        } catch (e: Exception) {
            showLogAssert("error", "${e.message}")
            Response.Error("${e.message}")
        }
    }

    suspend fun deleteSpecific(reference: String, colection: String, msg: String): Response {
        return try {
            db.collection(reference)
                .document(colection)
                .delete()
                .await()

            Response.Success(msg)

        } catch (e: Exception) {
            showLogAssert("error", "${e.message}")
            Response.Error("${e.message}")
        }
    }

    suspend fun login(path: String, nim: String, password: String): Response {
        return try {
            val data = db.collection(path).whereEqualTo("nim", nim).get().await()
            var password1 = ""

            // Check nim
            if (data.isEmpty) {
                Response.Error(
                    "Username tidak di temukan"
                )
            } else {
                // Check Password
                for (i in data) {
                    password1 = i["password"] as String
                }

                if (nim == "001" && password1 == password) {
                    Response.Changed("")
                } else {
                    if (password1 == password) {
                        data.forEach {
                            showLogAssert("data", "${it.toObject(ModelUser::class.java)}")
                            val model = it.toObject(ModelUser::class.java)
                            SavedData.setObject(Constant.KEY_USER,model)
                        }
                        Response.Success("sukses")
                    } else {
                        Response.Error("Password salah")
                    }
                }

            }

        } catch (e: Exception) {
            Response.Error(e.message.toString())
        }
    }

    suspend fun register(path: String, data: Any, username: String): Response {
        return try {
            val sameUsername = db.collection(path).whereEqualTo("username", username).get().await()
            if (sameUsername.isEmpty) {
                db.collection(path).add(data).await()
                Response.Success("Berhasil")
            } else {
                Response.Error("Username sudah terdaftar")
            }
        } catch (e: Exception) {
            Response.Error("${e.message}")
        }

    }

    suspend fun checkTheSameData(path: String, key: String, data: Any): Response {
        return try {
            val response = db.collection(path).whereEqualTo(key, data).get().await()
            showLogAssert("response checkTheSameData", "${response.isEmpty}")
            Response.Changed(response.isEmpty)
        } catch (e: Exception) {
            Response.Error("${e.message}")
        }

    }

}