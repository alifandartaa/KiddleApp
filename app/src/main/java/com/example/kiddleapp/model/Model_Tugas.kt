package com.example.kiddleapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class Model_Tugas (
    val kelas :String,
    val judul:String,
    val isi : String,
    val tanggal:String,
    val jam: String,
    val jumlah:String,
    val gambar:Int,
    val video:Int,
    val link:String


): Parcelable