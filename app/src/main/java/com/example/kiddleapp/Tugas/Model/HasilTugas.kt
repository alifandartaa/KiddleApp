package com.example.kiddleapp.Tugas.Model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class HasilTugas(
    val avatar: Int,
    val nama: String,
    val tanggal: String,
    val jam: String

) : Parcelable