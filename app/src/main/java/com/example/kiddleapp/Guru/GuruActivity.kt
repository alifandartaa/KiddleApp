package com.example.kiddleapp.Guru

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Guru.Adapter.GuruAdapter
import com.example.kiddleapp.Guru.Model.Guru
import com.example.kiddleapp.Profil.EditProfilActivity
import com.example.kiddleapp.Profil.ProfilActivity
import com.example.kiddleapp.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_guru.*

class GuruActivity : AppCompatActivity() {

    //untuk menyimpan guru
    private var guru: ArrayList<Guru> = arrayListOf()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guru)

        showRecyclerList(guru)

        //intent untuk kembali ke halaman sebelumnya
        img_back_guru.setOnClickListener {
            onBackPressed()
        }

    }

    private fun showRecyclerList(list: ArrayList<Guru>): GuruAdapter {
        val adapter = GuruAdapter(list) {

        }

        getPageGuruList{item: ArrayList<Guru> ->
            guru.addAll(item)
            adapter.notifyDataSetChanged()
            adapter.addItemToList(list)
            rv_guru.layoutManager = LinearLayoutManager(this)
            rv_guru.adapter = adapter
        }

        return adapter
    }

    private fun getPageGuruList(callback: (item:ArrayList<Guru>) -> Unit) {
        val listGuru: ArrayList<Guru> = arrayListOf()
        db.collection("Guru").addSnapshotListener { value, error ->
            if(error != null) return@addSnapshotListener
            for(document in value!!) {
                listGuru.add(Guru(
                    document.getString("avatar"),
                    document.getString("nama"),
                    document.getString("nomor"),
                    document.getString("kontak"),
                    document.getString("jabatan"),
                    document.getString("password")
                ))
            }
            callback.invoke(listGuru)
        }
    }
}