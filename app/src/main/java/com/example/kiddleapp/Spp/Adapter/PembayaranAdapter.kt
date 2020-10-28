package com.example.kiddleapp.Spp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.R
import com.example.kiddleapp.Spp.DetailPembayaranActivity
import com.example.kiddleapp.Spp.Model.Pembayaran

class PembayaranAdapter(private var data: List<Pembayaran>, private val listener: (Pembayaran) -> Unit): RecyclerView.Adapter<PembayaranAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context
    private var listPembayaran = ArrayList<Pembayaran>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private  val tv_bulan: TextView = view.findViewById(R.id.tv_pembayaran_bulan)
        private val tv_harga:TextView = view.findViewById(R.id.tv_pembayaran_harga)

        fun bindItem(data: Pembayaran, listener: (Pembayaran) -> Unit, context: Context, position: Int) {
            tv_bulan.text = data.bulan
            tv_harga.text = "Rp." + data.harga

            itemView.setOnClickListener {
                listener(data)
            }

            listener.invoke(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_pembayaran, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(it.context, DetailPembayaranActivity::class.java).putExtra("data", data[position])
            it.context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItemToList(list: ArrayList<Pembayaran>) {
        listPembayaran.clear()
        listPembayaran.addAll(list)
    }
}