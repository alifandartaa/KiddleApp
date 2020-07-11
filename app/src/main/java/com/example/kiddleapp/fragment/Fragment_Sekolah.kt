package com.example.kiddleapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kiddleapp.Notifikasi
import com.example.kiddleapp.Profil_sekolah
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.fragment_sekolah.view.*

class Fragment_Sekolah:Fragment() {
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_sekolah, container, false)

        //intent untuk melihat profil sekolah
        view.relative_profil_sekolah.setOnClickListener {
            startActivity(Intent(activity, Profil_sekolah::class.java))
        }

        view.relative_list_guru.setOnClickListener {
            Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
        }

        view.relative_kegiatan.setOnClickListener {
            Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}