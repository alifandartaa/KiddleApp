package com.example.kiddleapp.Kegiatan.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class Kegiatan(
    val id : String?,
    val jenis : String?,
    val judul: String?,
    val isi: String?,
    val tanggal: String?,
    val gambar: String?,
    val video: String?,
    val link: String?


) : Parcelable