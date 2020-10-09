package com.example.kiddleapp.Murid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.Jurnal.Adapter.JurnalAdapter
import com.example.kiddleapp.Jurnal.Model.Jurnal
import com.example.kiddleapp.Murid.Adapter.MuridAdapter
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit__murid.*
import kotlinx.android.synthetic.main.activity_jurnal.*
import kotlinx.android.synthetic.main.activity_jurnal.auto_kelas_jurnal
import kotlinx.android.synthetic.main.fragment_murid.*
import kotlinx.android.synthetic.main.fragment_murid.view.*
import java.util.*
import kotlin.collections.ArrayList

class MuridFragment : Fragment() {

    //untuk menyimpan
    private val murid: ArrayList<Murid> = arrayListOf()
    private val db = FirebaseFirestore.getInstance()
    private val muridCollection = db.collection("Murid")
    private lateinit var kelastext:TextView
    private var root: View? = null
    private lateinit var sharedPreferences: SharedPreferences

    override  fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        root = inflater.inflate(R.layout.fragment_murid, container, false)

        sharedPreferences = activity?.getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)!!

        //untuk dropdown. bisa ganti kelas yang ada di firebase nya untuk dropdown
        if(sharedPreferences.getString("kelas","") == "admin"){

            murid.clear()
            showRecyclerList(murid)

            root!!.btn_plus_murid.visibility = View.VISIBLE
            root!!.btn_plus_murid.setOnClickListener {
                val intent = Intent(activity, EditMuridActivity::class.java).putExtra("jenis", "TAMBAH_MURID")
                startActivity(intent)
            }

            val kelas = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar", "Semua Kelas")
            val adapter_kelas = ArrayAdapter(activity!!.applicationContext, R.layout.dropdown_text, kelas)
            (root!!.dropdown_murid_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter_kelas)

            root!!.dropdown_value_murid_kelas.setOnItemClickListener { parent, view, position, id ->
                var item = parent.getItemAtPosition(position).toString()
                murid.clear()
                showRecyclerList(murid)
            }

        }else{
            root!!.dropdown_murid_kelas.isEnabled=true
            root!!.dropdown_value_murid_kelas.setText(sharedPreferences.getString("kelas",""))
            murid.clear()
            showRecyclerList(murid)
        }

        return root
    }




    //recyclerView Jurnal
    private fun showRecyclerList(list: ArrayList<Murid>): MuridAdapter {
        val adapter = MuridAdapter(list) {
            //Log.d("Tugas Activity", "Result: $it")

        }
        getPageTugasList { item: ArrayList<Murid> ->
            murid.clear()
            murid.addAll(item)
            adapter.addItemToList(list)
            Log.d("Tugas Activity", "showRecyclerList: before adapter notify")
            adapter.notifyDataSetChanged()
            Collections.reverse(list);
            Log.d("Tugas Activity", "showRecyclerList: before rv_tugas set adapter layout")
            root!!.rv_murid?.layoutManager = LinearLayoutManager(context)
            root!!.rv_murid.adapter = adapter

        }
        return adapter
    }

    private fun getPageTugasList(callback: (item: ArrayList<Murid>) -> Unit) {
        val listMurid: ArrayList<Murid> = arrayListOf()
        Log.d("page", root!!.dropdown_value_murid_kelas?.text.toString())
        if(root!!.dropdown_value_murid_kelas.text.toString() == "" || root!!.dropdown_value_murid_kelas.text.toString() =="Semua Kelas"){
            Log.d("page2", view?.dropdown_value_murid_kelas?.text.toString() )
            muridCollection.addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if(result!!.isEmpty){
                    Toast.makeText(
                        context,
                        "Tidak Ada Murid yang Terdaftar",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }else{
                    for (document in result!!) {
                        Log.d("TugasActivity", document.toString())
                        listMurid.add(
                            Murid(
                                document.getString("avatar"),
                                document.getString("nomor"),
                                document.getString("angkatan"),
                                document.getString("nama"),
                                document.getString("kelas"),
                                document.getString("ttl"),
                                document.getString("alamat"),
                                document.getString("ayah"),
                                document.getString("ibu"),
                                document.getString("kontak_ayah"),
                                document.getString("kontak_ibu"),
                                document.getString("password")
                            )
                        )

                    }
                }

                Log.d("TugasActivity", "callback should be call")
                callback.invoke(listMurid)
            }

        }else if(root!!.dropdown_value_murid_kelas?.text.toString() != "Semua Kelas"){
            Log.d("page3", root!!.dropdown_value_murid_kelas?.text.toString() )
            muridCollection.whereEqualTo("kelas",root!!.dropdown_value_murid_kelas?.text.toString()).addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if(result!!.isEmpty){
                    Toast.makeText(
                        context,
                        "Tidak Ada Murid yang Terdaftar",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }else{
                    for (document in result!!) {
                        Log.d("TugasActivity", document.toString())
                        listMurid.add(
                            Murid(
                                document.getString("avatar"),
                                document.getString("nomor"),
                                document.getString("angkatan"),
                                document.getString("nama"),
                                document.getString("kelas"),
                                document.getString("ttl"),
                                document.getString("alamat"),
                                document.getString("ayah"),
                                document.getString("ibu"),
                                document.getString("kontak_ayah"),
                                document.getString("kontak_ibu"),
                                document.getString("password")
                            )
                        )

                    }
                }

                Log.d("TugasActivity", "callback should be call")
                callback.invoke(listMurid)
            }


        }


        Log.d("Tugas Activity", "getPageTugasList: after get collection data, should not be printed")
    }
}