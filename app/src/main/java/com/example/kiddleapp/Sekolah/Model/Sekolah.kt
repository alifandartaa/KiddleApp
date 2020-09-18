package com.example.kiddleapp.Sekolah.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sekolah(
    val id_sekolah:String?,
    val nama: String?,
    val alamat: String?,
    val kontak: String?,
    val isi: String?,
    val visi: String?,
    val misi: String?,
    val fasilitas: String?,
    val prestasi: String?,
    val gambar: String?

) : Parcelable