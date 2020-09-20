package com.example.kiddleapp.Rapor.Model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//menggunakan parcelable agar bisa mengambil data sekaligus dari firebase
@Parcelize
data class Rapor(
    val id_murid :String?,
    val kelas : String?,
    val semester : String?,
    val nilai_berbahasa : String?,
    val nilai_kognitif: String?,
    val nilai_agama: String?,
    val nilai_keterampilan: String?,
    val nilai_motorik: String?,
    val des_berbahasa : String?,
    val des_kognitif: String?,
    val des_agama: String?,
    val des_keterampilan: String?,
    val des_motorik: String?

) : Parcelable