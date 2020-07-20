package com.example.kiddleapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.R
import com.example.kiddleapp.model.Model_Jurnal
import com.example.kiddleapp.model.Model_Murid
import com.example.kiddleapp.model.Model_Tugas
import kotlinx.android.synthetic.main.list_murid.view.*

class Adapter_Jurnal(private var data:List<Model_Jurnal>, private val listener: (Model_Jurnal) -> Unit):RecyclerView.Adapter<Adapter_Jurnal.ViewHolder>()  {

    lateinit var contextAdapter:Context

    //assign value dari model ke xml
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val img_jenis:ImageView = view.findViewById(R.id.img_jenis_list_jurnal)
        private val tv_jenis:TextView = view.findViewById(R.id.tv_jenis_list_jurnal)
        private val tv_judul:TextView = view.findViewById(R.id.tv_judul_list_jurnal)
        private val tv_tanggal:TextView = view.findViewById(R.id.tv_tanggal_list_jurnal)


        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model
        fun bindItem(data:Model_Jurnal, listener: (Model_Jurnal) -> Unit, context: Context, position: Int) {
            if(data.judul=="Motorik"){
                img_jenis.setImageResource(R.drawable.ic_motorik)
            }else if(data.judul=="Keterampilan"){
                img_jenis.setImageResource(R.drawable.ic_keterampilan)
            }else if(data.judul=="Agama"){
                img_jenis.setImageResource(R.drawable.ic_agama)
            }else if(data.judul=="Berbahasa") {
                img_jenis.setImageResource(R.drawable.ic_berbahasa)
            }else if(data.judul=="Kognitif") {
                img_jenis.setImageResource(R.drawable.ic_kognitif)
            }
            tv_jenis.text=data.jenis
            tv_judul.text = data.judul
            tv_tanggal.text=data.tanggal

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_jurnal, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }
}