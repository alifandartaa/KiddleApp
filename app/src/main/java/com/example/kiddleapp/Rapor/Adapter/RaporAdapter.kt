package com.example.kiddleapp.Rapor.Adapter


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiddleapp.Jurnal.DetailJurnalActivity
import com.example.kiddleapp.Jurnal.Model.Jurnal
import com.example.kiddleapp.Murid.Adapter.MuridAdapter
import com.example.kiddleapp.Murid.DetailMuridActivity
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import com.example.kiddleapp.Rapor.EditRaporActivity

class RaporAdapter(private var data: List<Murid>, private val listener: (Murid) -> Unit) :
    RecyclerView.Adapter<RaporAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context
    private var listMurid = ArrayList<Murid>()

    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img_avatar: ImageView = view.findViewById(R.id.img_avatar_murid)
        private val tv_nama: TextView = view.findViewById(R.id.tv_nama_murid)

        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model

        fun bindItem(data: Murid, listener: (Murid) -> Unit, context: Context, position: Int) {
            Glide.with(context).load(data.avatar).centerCrop().into(img_avatar)
            tv_nama.text = data.nama

            itemView.setOnClickListener {
                listener(data)
            }
            listener.invoke(data)
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
        holder.itemView.setOnClickListener(View.OnClickListener { v ->
            val intent: Intent =
                Intent(v.context, EditRaporActivity::class.java).putExtra("data", data[position])
            v.context.startActivity(intent)
            (v.context as Activity).finish()
        })
    }

    fun addItemToList(list: ArrayList<Murid>) {
        listMurid.clear()
        listMurid.addAll(list)
    }
}