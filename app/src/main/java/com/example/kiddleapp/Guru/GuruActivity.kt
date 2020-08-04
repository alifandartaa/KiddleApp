package com.example.kiddleapp.Guru

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Guru.Adapter.GuruAdapter
import com.example.kiddleapp.Guru.Model.Guru
import com.example.kiddleapp.Profil.EditProfilActivity
import com.example.kiddleapp.Profil.ProfilActivity
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_guru.*

class GuruActivity : AppCompatActivity() {

    //untuk menyimpan guru
    private var guru = ArrayList<Guru>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guru)

        //recyclerView dengan linear layout
        rv_guru.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan arraylist
        guru.clear()

        //bisa diganti dengan data dari firebase
        val item = Guru(
            R.drawable.avatar,
            "Lee Ji Eun",
            "175150201",
            "085779993333",
            "Bintang Kecil",
            "punten123"
        )
        guru.add(item)

        //agar list guru dapat di-click sekaligus mengisi adapter dengan data di arraylist
        rv_guru.adapter = GuruAdapter(guru) {
            val intent: Intent =
                Intent(this@GuruActivity, ProfilActivity::class.java).putExtra("data", it)
            intent.putExtra("tipeAkses", "GURU")
            startActivity(intent)
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_guru.setOnClickListener {
            onBackPressed()
        }

        btn_plus_guru.setOnClickListener {
            val intent = Intent(this@GuruActivity, EditProfilActivity::class.java).putExtra(
                "jenis",
                "TAMBAH_PROFIL"
            )
            startActivity(intent)
        }
    }
}