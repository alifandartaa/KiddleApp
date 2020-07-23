package com.example.kiddleapp.Kegiatan.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class Kegiatan(
    val judul: String,
    val isi: String,
    val tanggal: String,
    val gambar: Int,
    val video: Int,
    val link: String


) : Parcelable