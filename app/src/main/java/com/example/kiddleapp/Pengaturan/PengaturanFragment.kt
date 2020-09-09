package com.example.kiddleapp.Pengaturan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.example.kiddleapp.LoginActivity
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.fragment_pengaturan.view.*

class PengaturanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pengaturan, container, false)

        val sharedPreferences:SharedPreferences = activity?.getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)!!

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
            sharedPreferences.edit().putString("id_guru", null).apply()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finishAffinity()
        }

        return view
    }
}