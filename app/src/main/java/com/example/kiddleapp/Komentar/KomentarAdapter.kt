package com.example.kiddleapp.Komentar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.R


class KomentarAdapter(private var data: List<Komentar>, private val listener: (Komentar) -> Unit) :
    RecyclerView.Adapter<KomentarAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img_avatar: ImageView = view.findViewById(R.id.img_komentar)
        private val tv_nama: TextView = view.findViewById(R.id.tv_nama_komentar)
        private val tv_jabatan: TextView = view.findViewById(R.id.tv_jabatan_komentar)
        private val tv_isi: TextView = view.findViewById(R.id.tv_isi_komentar)

        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model

        fun bindItem(
            data: Komentar,
            listener: (Komentar) -> Unit,
            context: Context,
            position: Int
        ) {
            img_avatar.setImageResource(data.avatar)
            tv_nama.text = data.nama
            tv_jabatan.text = data.jabatan
            tv_isi.text = data.isi

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_komentar, parent, false)
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

