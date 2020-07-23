package com.example.kiddleapp.Notifikasi.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase. ganti semua ke String!
@Parcelize
data class Notifikasi(
    val judul: String,
    val icon: Int,
    val waktu: String,
    val jenis: String
) : Parcelable