package com.example.kiddleapp.Presensi.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class PresensiMurid(
    var id_murid:String? = null,
    var kehadiran: String? = null
): Parcelable