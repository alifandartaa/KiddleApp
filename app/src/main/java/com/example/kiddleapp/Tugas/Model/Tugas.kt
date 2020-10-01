package com.example.kiddleapp.Tugas.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class Tugas(
    val id_tugas:String?,
    val kelas: String?,
    val judul: String?,
    val isi: String?,
    val tanggal: String?,
    val jam: String?,

    val gambar: String?,
    val video: String?,
    val link: String?
): Parcelable
