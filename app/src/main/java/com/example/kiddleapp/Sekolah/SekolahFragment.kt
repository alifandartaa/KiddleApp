package com.example.kiddleapp.Sekolah

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kiddleapp.Guru.GuruActivity
import com.example.kiddleapp.Kegiatan.KegiatanActivity
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.fragment_sekolah.view.*

class SekolahFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sekolah, container, false)

        //intent untuk melihat profil sekolah
        view.relative_profil_sekolah.setOnClickListener {
            startActivity(Intent(activity, ProfilSekolahActivity::class.java))
        }

        //intent untuk melihat guru
        view.relative_list_guru.setOnClickListener {
            startActivity(Intent(activity, GuruActivity::class.java))
        }

        view.relative_kegiatan.setOnClickListener {
            startActivity(
                Intent(activity, KegiatanActivity::class.java).putExtra(
                    "jenis",
                    "Kegiatan"
                )
            )
        }

        return view
    }
}