package com.example.kiddleapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.fragment_pengaturan.view.*

class Fragment_Pengaturan: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_pengaturan, container, false)

        //ketika switch dinyalakan atau dimatikan
        view.switch_pengaturan_notif.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
        }

        //intent ke halaman onboarding
        view.tv_onboarding.setOnClickListener {
            Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
        }

        //intent ke halaman tentang aplikasi
        view.tv_about.setOnClickListener {
            Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
        }

        //intent ke halaman bantuan
        view.tv_bantuan.setOnClickListener {
            Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
        }

        //hapus sharedpreferences untuk logout
        view.tv_logout.setOnClickListener {
            Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}