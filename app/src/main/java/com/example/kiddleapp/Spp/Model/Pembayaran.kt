package com.example.kiddleapp.Spp.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pembayaran(
    val bulan:String?,
    val harga:String?,
    val batas:String?,
    val semester:String?
):Parcelable