package com.example.kiddleapp.Komentar
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class Komentar(
    val avatar: Int,
    val nama: String,
    val jabatan: String,
    val isi: String

) : Parcelable