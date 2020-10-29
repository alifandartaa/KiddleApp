package com.example.kiddleapp.Komentar
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class Komentar(
    val id_guru:String?,
    val isi: String?,
    val koleksi:String?
) : Parcelable