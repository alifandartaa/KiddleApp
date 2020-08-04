package com.example.kiddleapp.Jurnal.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class Jurnal(
    val jenis: String,
    val kelas: String,
    val judul: String,
    val isi: String,
    val tanggal: String,
    val gambar: Int,
    val video: Int


) : Parcelable