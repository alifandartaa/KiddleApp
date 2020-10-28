package com.example.kiddleapp.Spp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.R
import com.example.kiddleapp.Spp.Adapter.DetailPembayaranAdapter
import com.example.kiddleapp.Spp.Model.Pembayaran
import com.example.kiddleapp.Spp.Model.PembayaranMurid
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detail_pembayaran.*
import kotlinx.android.synthetic.main.activity_konfirm_spp.*
import java.util.*
import kotlin.collections.ArrayList

class DetailPembayaranActivity : AppCompatActivity() {

    private val pembayaranMurid: ArrayList<PembayaranMurid> = arrayListOf()
    private val db = FirebaseFirestore.getInstance()
    private var storage = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pembayaran)

        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)
        val data = intent.getParcelableExtra<Pembayaran>("data")

        tv_detail_bulan.text = data.bulan
        tv_detail_harga.text = "Rp." + data.harga
        tv_detail_batas.text = "Batas: " + data.batas

        ic_back_detail_pembayaran.setOnClickListener {
            onBackPressed()
        }

        ic_menu_detail_pembayaran.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit){
                    val intent = Intent(this@DetailPembayaranActivity, EditPembayaranActivity::class.java).putExtra("data", data)
                    intent.putExtra("jenis", "EDIT")
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                } else if(it.itemId == R.id.menu_hapus){
                    MaterialAlertDialogBuilder(this).apply {
                        setTitle("Hapus Pembayaran")
                        setMessage("Apa anda yakin ingin menghapus pembayaran ini?")
                        setPositiveButton("Ya"){_,_ ->
                            db.document("Pembayaran/${data.bulan}").delete().addOnSuccessListener {
                                db.document("Pembayaran Murid/${data.bulan}").delete().addOnSuccessListener {

                                }
                                val intent:Intent = Intent(this@DetailPembayaranActivity, MainActivity::class.java).putExtra("jenis", "pembayaran")
                                startActivity(intent)
                                finish()
                                Toast.makeText(context, "Pembayaran berhasil dihapus", Toast.LENGTH_SHORT).show()
                            }
                        }.show()
                    }
                }
                return@setOnMenuItemClickListener false
            }
            popup.inflate(R.menu.menu_edit_hapus)
            popup.show()
        }

        if(sharedPreferences.getString("kelas", "") == "admin"){
            ic_menu_detail_pembayaran.visibility = View.VISIBLE
            pembayaranMurid.clear()
            showRecylerList(pembayaranMurid)

            val kelas = listOf("Semua Kelas", "Bintang Kecil", "Bintang Besar")
            val adapter_kelas = ArrayAdapter(this.applicationContext, R.layout.dropdown_text, kelas)
            (dropdown_kelas_pembayaran.editText as? AutoCompleteTextView)?.setAdapter(adapter_kelas)

            dropdown_value_kelas_pembayaran.setOnItemClickListener { parent, view, position, id ->
                var item = parent.getItemAtPosition(position).toString()
                pembayaranMurid.clear()
                showRecylerList(pembayaranMurid)
            }
        }else {
            dropdown_kelas_pembayaran.isEnabled = false
            dropdown_value_kelas_pembayaran.setText(sharedPreferences.getString("kelas", ""))
            pembayaranMurid.clear()
            showRecylerList(pembayaranMurid)
        }
    }

    private fun showRecylerList(list: ArrayList<PembayaranMurid>):DetailPembayaranAdapter {
        val adapter = DetailPembayaranAdapter(list){

        }
        getPagePembayaranList{item: ArrayList<PembayaranMurid> ->
            pembayaranMurid.clear()
            pembayaranMurid.addAll(item)
            adapter.addItemToList(list)
            adapter.notifyDataSetChanged()
            Collections.reverse(list)
            rv_pembayaran_murid.layoutManager = LinearLayoutManager(this)
            rv_pembayaran_murid.adapter = adapter
        }
        return adapter
    }

    private fun getPagePembayaranList(callback: (ArrayList<PembayaranMurid>) -> Unit) {
        val listPembayaranMurid: ArrayList<PembayaranMurid> = arrayListOf()
        if(dropdown_value_kelas_pembayaran.text.toString() == "" || dropdown_value_kelas_pembayaran.text.toString() == "Semua Kelas"){
            db.collection("Pembayaran Murid/${tv_detail_bulan.text}/Bukti").addSnapshotListener { result, e ->
                if(e!= null){
                    return@addSnapshotListener
                }
                if(result!!.isEmpty){
                    Toast.makeText(this, "Tidak ada data pembayaran!", Toast.LENGTH_SHORT).show()
                } else {
                    for(document in result!!){
                        listPembayaranMurid.add(
                            PembayaranMurid(
                                document.getString("id_murid"),
                                document.getString("bulan"),
                                document.getString("bukti"),
                                document.getString("status")
                            )
                        )
                    }
                }
                callback.invoke(listPembayaranMurid)
            }
        } else if(dropdown_value_kelas_pembayaran.text.toString() != "Semua Kelas"){
            db.collection("Pembayaran Murid/${tv_detail_bulan.text}/Bukti").whereEqualTo("kelas", dropdown_value_kelas_pembayaran.text.toString()).addSnapshotListener { result, e ->
                if(e!= null){
                    return@addSnapshotListener
                }
                if(result!!.isEmpty){
                    Toast.makeText(this, "Tidak ada data pembayaran!", Toast.LENGTH_SHORT).show()
                } else {
                    for(document in result!!){
                        listPembayaranMurid.add(
                            PembayaranMurid(
                                document.getString("id_murid"),
                                document.getString("bulan"),
                                document.getString("bukti"),
                                document.getString("status")
                            )
                        )
                    }
                }
                callback.invoke(listPembayaranMurid)
            }
        }
    }
}