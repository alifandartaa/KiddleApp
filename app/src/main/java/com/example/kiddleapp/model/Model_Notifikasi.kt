package com.example.kiddleapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Model_Notifikasi (
    val judul:String,
    val icon:Int,
    val waktu:String,
    val jenis:String
): Parcelable