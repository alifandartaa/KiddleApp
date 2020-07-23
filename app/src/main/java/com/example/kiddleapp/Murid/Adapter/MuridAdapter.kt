package com.example.kiddleapp.Murid.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R

class MuridAdapter(private var data: List<Murid>, private val listener: (Murid) -> Unit) :
    RecyclerView.Adapter<MuridAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img_avatar: ImageView = view.findViewById(R.id.img_avatar_murid)
        private val tv_nama: TextView = view.findViewById(R.id.tv_nama_murid)

        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model

        fun bindItem(data: Murid, listener: (Murid) -> Unit, context: Context, position: Int) {
            img_avatar.setImageResource(data.avatar)
            tv_nama.text = data.nama

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_murid, parent, false)
        return ViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }
}