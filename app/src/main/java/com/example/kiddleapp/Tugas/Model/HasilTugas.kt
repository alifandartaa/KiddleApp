package com.example.kiddleapp.Tugas.Model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class HasilTugas(
    val id_hasil_tugas:String?,
    val id_tugas: String?,
    val tanggal: String?,
    val file:String?
) : Parcelable