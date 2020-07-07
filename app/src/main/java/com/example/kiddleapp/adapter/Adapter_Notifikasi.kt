package com.example.kiddleapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.R
import com.example.kiddleapp.model.Model_Notifikasi

class Adapter_Notifikasi(private var data:List<Model_Notifikasi>, private val listener: (Model_Notifikasi) -> Unit): RecyclerView.Adapter<Adapter_Notifikasi.ViewHolder>() {

    lateinit var contextAdapter:Context

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val img_icon:ImageView = view.findViewById(R.id.img_icon_notifikasi)
        private val tv_judul:TextView = view.findViewById(R.id.tv_judul_notifikasi)
        private val tv_waktu:TextView = view.findViewById(R.id.tv_waktu_notifikasi)
        private val tv_jenis:TextView = view.findViewById(R.id.tv_jenis_notifikasi)

        fun bindItem(data: Model_Notifikasi, listener: (Model_Notifikasi) -> Unit, context: Context, position: Int) {
            img_icon.setImageResource(data.icon)
            tv_judul.text = data.judul
            tv_waktu.text = data.waktu
            tv_jenis.text = data.jenis

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_notifikasi, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return  data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }
}