package com.example.kiddleapp.Komentar
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class Komentar(
    val id_guru:String?,
//    val avatar: String?,
//    val nama: String?,
//    val jabatan: String?,
    val isi: String?

) : Parcelable