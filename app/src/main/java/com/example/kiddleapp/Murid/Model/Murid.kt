package com.example.kiddleapp.Murid.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase. ganti semua ke String!
@Parcelize
data class Murid(
    val avatar: Int,
    val nomor: String,
    val nama: String,
    val kelas: String,
    val ttl: String,
    val alamat: String,
    val ayah: String,
    val ibu: String,
    val kontakAyah: String,
    val kontakIbu: String,
    val password:String
): Parcelable