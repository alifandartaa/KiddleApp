package com.example.kiddleapp.Spp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.kiddleapp.R
import com.example.kiddleapp.Spp.Model.PembayaranMurid
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_bukti_pembayaran.*

class BuktiPembayaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bukti_pembayaran)

        val data = intent.getParcelableExtra<PembayaranMurid>("data")
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        ic_back_bukti.setOnClickListener {
            onBackPressed()
        }

        tv_bukti_tanggal.text = "Tanggal: ${data.tanggal}"
        tv_bukti_harga.text = "Jumlah: ${data.harga}"
        tv_bukti_status.text = data.status
        Glide.with(this).load(data.bukti).into(img_bukti)

        Toast.makeText(this, "Buka ulang jika bukti tidak muncul", Toast.LENGTH_LONG).show()

        if(data.status == "Diterima"){
            btn_bukti_konfirmasi.visibility = View.GONE
            btn_bukti_tolak.visibility = View.GONE
        }

        btn_bukti_tolak.setOnClickListener {
            db.document("Pembayaran Murid/${data.bulan}/Bukti/${data.id_murid}").update(mapOf(
                "status" to "Ditolak"
            )).addOnCompleteListener {
                onBackPressed()
            }.addOnFailureListener {
                Toast.makeText(this, "Terdapat gangguang, mohon coba lagi!", Toast.LENGTH_LONG).show()
            }
        }

        btn_bukti_konfirmasi.setOnClickListener {
            db.document("Pembayaran Murid/${data.bulan}/Bukti/${data.id_murid}").update(mapOf(
                "status" to "Diterima"
            )).addOnCompleteListener {
                onBackPressed()
            }.addOnFailureListener {
                Toast.makeText(this, "Terdapat gangguang, mohon coba lagi!", Toast.LENGTH_LONG).show()
            }
        }
    }
}