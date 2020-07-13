package com.example.kiddleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Guru
import com.example.kiddleapp.model.Model_Guru
import kotlinx.android.synthetic.main.activity_guru.*

class Guru : AppCompatActivity() {

    //untuk menyimpan guru
    private  var guru = ArrayList<Model_Guru>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guru)

        //recyclerView dengan linear layout
        rv_guru.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan arraylist
        guru.clear()

        //bisa diganti dengan data dari firebase
        val item = Model_Guru(R.drawable.avatar, "Lee Ji Eun", "175150201", "085779993333", "Bintang Kecil", "punten123")
        guru.add(item)

        //agar list guru dapat di-click sekaligus mengisi adapter dengan data di arraylist
        rv_guru.adapter = Adapter_Guru(guru){
            val intent: Intent = Intent(this@Guru, Profil::class.java).putExtra("data", it)
            intent.putExtra("tipeAkses", "GURU")
            startActivity(intent)
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_guru.setOnClickListener {
            onBackPressed()
        }

        btn_plus_guru.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}