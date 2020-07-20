package com.example.kiddleapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class Model_Pengumuman (
    val judul:String,
    val isi: String,
    val tanggal:String,
    val gambar:Int,
    val video:Int


): Parcelable