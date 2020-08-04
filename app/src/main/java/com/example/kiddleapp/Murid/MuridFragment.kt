package com.example.kiddleapp.Murid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Murid.Adapter.MuridAdapter
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.fragment_murid.view.*

class MuridFragment : Fragment() {

    //untuk menyimpan murid
    private var murid = ArrayList<Murid>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_murid, container, false)

        //untuk dropdown. bisa ganti kelas yang ada di firebase nya untuk dropdown
        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter =
            ArrayAdapter<String>(activity!!.applicationContext, R.layout.dropdown_text, items)
        (view.dropdown_murid_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //recyclerView murid
        view.rv_murid.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        murid.clear()

        //bisa diganti dengan data dari firebase
        val temp = Murid(
            R.drawable.avatar, "198022", "Lee Ji Eun", "Bintang Besar",
            "Bandung, 3 Mei 1999", "Jl. Watugong No.17F", "Budi", "Siti",
            "0812345678", "089765432", "mangga123"
        )
        murid.add(temp)

        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
        view.rv_murid.adapter =
            MuridAdapter(murid) {
                val intent = Intent(activity, DetailMuridActivity::class.java).putExtra("data", it)
                startActivity(intent)
            }

        view.btn_plus_murid.setOnClickListener {
            val intent =
                Intent(activity, EditMuridActivity::class.java).putExtra("jenis", "TAMBAH_PROFIL")
            startActivity(intent)
        }

        return view
    }
}