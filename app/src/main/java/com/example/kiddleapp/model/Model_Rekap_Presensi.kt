package com.example.kiddleapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase. ganti semua ke String!
@Parcelize
data class Model_Rekap_Presensi (
    val bulan:String,
    val hadir:String,
    val sakit:String,
    val izin:String,
    val alpha:String
): Parcelable