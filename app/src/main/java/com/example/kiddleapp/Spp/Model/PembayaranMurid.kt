package com.example.kiddleapp.Spp.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PembayaranMurid(
    val id_murid:String?,
    val bulan:String?,
    val bukti:String?,
    val status:String?,
    val tanggal:String?,
    val harga:String?
):Parcelable