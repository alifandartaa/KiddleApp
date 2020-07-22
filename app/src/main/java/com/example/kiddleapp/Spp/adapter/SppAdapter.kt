package com.example.kiddleapp.Spp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.R
import com.example.kiddleapp.Spp.model.ItemSpp
import kotlinx.android.synthetic.main.item_pembayaran_spp.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class SppAdapter : RecyclerView.Adapter<SppAdapter.SppViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    var listItem = ArrayList<ItemSpp>()

    class SppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemSpp: ItemSpp) {
            with(itemView) {
                tv_bulan_spp.text = itemSpp.bulanSpp.toString()
                val localeID = Locale("in", "ID")
                val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
                tv_jumlahbayar_spp.text = formatRupiah.format(itemSpp.jumlahPembayaran)
            }
        }

    }

    fun addItemToList(list: ArrayList<ItemSpp>) {
        listItem.clear()
        listItem.addAll(list)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SppViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pembayaran_spp, parent, false)
        return SppViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: SppViewHolder, position: Int) {
        holder.bind(listItem[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClick(listItem[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClick(data: ItemSpp)
    }
}