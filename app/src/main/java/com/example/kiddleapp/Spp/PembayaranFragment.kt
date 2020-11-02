package com.example.kiddleapp.Spp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.R
import com.example.kiddleapp.Spp.Adapter.PembayaranAdapter
import com.example.kiddleapp.Spp.Model.Pembayaran
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_pembayaran.view.*
import java.util.*
import kotlin.collections.ArrayList

class PembayaranFragment: Fragment() {

    private val pembayaran: ArrayList<Pembayaran> = arrayListOf()
    private lateinit var sharedPreferences: SharedPreferences
    private var root:View? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        root = inflater.inflate(R.layout.fragment_pembayaran, container, false)

        sharedPreferences = activity?.getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)!!
        if(sharedPreferences.getString("kelas", "") == "admin") {
            pembayaran.clear()
            showRecylerList(pembayaran)

            root!!.btn_plus_pembayaran.visibility = View.VISIBLE
            root!!.btn_plus_pembayaran.setOnClickListener {
                val intent = Intent(activity, EditPembayaranActivity::class.java)
                startActivity(intent)
            }

            val semester = listOf("Semua Semester", "Ganjil", "Genap")
            val adapter_semester = ArrayAdapter(activity!!.applicationContext, R.layout.dropdown_text, semester)
            (root!!.dropdown_semester_pembayaran.editText as? AutoCompleteTextView)?.setAdapter(adapter_semester)

            root!!.dropdown_value_semester_pembayaran.setOnItemClickListener { parent, view, position, id ->
                var item = parent.getItemAtPosition(position).toString()
                pembayaran.clear()
                showRecylerList(pembayaran)
            }
        } else {
            root!!.dropdown_semester_pembayaran.isEnabled = false
            pembayaran.clear()
            showRecylerList(pembayaran)
        }

        return root
    }

    private fun showRecylerList(list: ArrayList<Pembayaran>): PembayaranAdapter {
        val adapter = PembayaranAdapter(list){

        }

        getPagePembayaranList{item: ArrayList<Pembayaran> ->
            pembayaran.clear()
            pembayaran.addAll(item)
            adapter.addItemToList(list)
            adapter.notifyDataSetChanged()
            Collections.reverse(list)
            root!!.rv_pembayaran?.layoutManager = LinearLayoutManager(context)
            root!!.rv_pembayaran.adapter = adapter
        }
        return adapter
    }

    private fun getPagePembayaranList(callback: (item: ArrayList<Pembayaran>) -> Unit) {
        val listPembayaran: ArrayList<Pembayaran> = arrayListOf()
        if(root!!.dropdown_value_semester_pembayaran.text.toString() == "" || root!!.dropdown_value_semester_pembayaran.text.toString() == "Semua Semester") {
            db.collection("SPP").addSnapshotListener { result, e ->
                if(e != null){
                    return@addSnapshotListener
                }

                if(result!!.isEmpty){
                    Toast.makeText(context, "Tidak ada pemabayaran yang tersedia!", Toast.LENGTH_SHORT).show()
                } else {
                    for(document in result!!){
                        listPembayaran.add(
                            Pembayaran(
                                document.getString("bulan"),
                                document.getString("harga"),
                                document.getString("batas"),
                                document.getString("semester")
                            )
                        )
                    }
                }
                callback.invoke(listPembayaran)
            }
        } else if(root!!.dropdown_value_semester_pembayaran.text.toString() != "Semua Semester"){
            db.collection("SPP").whereEqualTo("semester", root!!.dropdown_value_semester_pembayaran.text.toString()).addSnapshotListener { result, e ->
                if(e != null){
                    return@addSnapshotListener
                }

                if(result!!.isEmpty){
                    Toast.makeText(context, "Tidak ada pemabayaran yang tersedia!", Toast.LENGTH_SHORT).show()
                } else {
                    for(document in result!!){
                        listPembayaran.add(
                            Pembayaran(
                                document.getString("bulan"),
                                document.getString("harga"),
                                document.getString("batas"),
                                document.getString("semester")
                            )
                        )
                    }
                }
                callback.invoke(listPembayaran)
            }
        }
    }

}