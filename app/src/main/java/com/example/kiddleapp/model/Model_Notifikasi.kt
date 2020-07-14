package com.example.kiddleapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class Model_Notifikasi (
    val judul:String,
    val icon:Int,
    val waktu:String,
    val jenis:String
): Parcelable