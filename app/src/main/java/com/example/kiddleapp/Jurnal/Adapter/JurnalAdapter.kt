package com.example.kiddleapp.Jurnal.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.Jurnal.DetailJurnalActivity
import com.example.kiddleapp.Jurnal.Model.Jurnal
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.DetailTugasActivity
import com.example.kiddleapp.Tugas.Model.Tugas

class JurnalAdapter(private var data: List<Jurnal>, private val listener: (Jurnal) -> Unit) :
    RecyclerView.Adapter<JurnalAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context
    private var listJurnal= ArrayList<Jurnal>()


    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img_jenis: ImageView = view.findViewById(R.id.img_jenis_list_jurnal)
        private val tv_jenis: TextView = view.findViewById(R.id.tv_jenis_list_jurnal)
        private val tv_judul: TextView = view.findViewById(R.id.tv_judul_list_jurnal)
        private val tv_tanggal: TextView = view.findViewById(R.id.tv_tanggal_list_jurnal)


        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model
        fun bindItem(data: Jurnal, listener: (Jurnal) -> Unit, context: Context, position: Int) {
            if (data.jenis == "Motorik") {
                img_jenis.setImageResource(R.drawable.ic_motorik)
            } else if (data.jenis == "Keterampilan") {
                img_jenis.setImageResource(R.drawable.ic_keterampilan)
            } else if (data.jenis == "Agama") {
                img_jenis.setImageResource(R.drawable.ic_agama)
            } else if (data.jenis == "Berbahasa") {
                img_jenis.setImageResource(R.drawable.ic_berbahasa)
            } else if (data.jenis == "Kognitif") {
                img_jenis.setImageResource(R.drawable.ic_kognitif)
            }
            tv_jenis.text=data.jenis
            tv_judul.text = data.judul
            tv_tanggal.text=data.tanggal

            itemView.setOnClickListener {
                listener(data)
            }
            listener.invoke(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_jurnal, parent, false)
        return ViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
        holder.itemView.setOnClickListener(View.OnClickListener { v ->
            val intent: Intent = Intent(v.context, DetailJurnalActivity::class.java).putExtra("data", data[position])
            v.context.startActivity(intent)
            (v.context as Activity).finish()
        })
    }

    fun addItemToList(list: ArrayList<Jurnal>) {
        listJurnal.clear()
        listJurnal.addAll(list)
    }
}