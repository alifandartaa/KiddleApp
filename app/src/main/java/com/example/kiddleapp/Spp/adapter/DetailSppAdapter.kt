package com.example.kiddleapp.Spp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.R
import com.example.kiddleapp.Spp.model.ItemBayarSpp
import kotlinx.android.synthetic.main.item_detail_spp.view.*

class DetailSppAdapter : RecyclerView.Adapter<DetailSppAdapter.DetailSppViewHolder>() {
    private lateinit var onItemClickCallback: onClickCallback
    var listItem = ArrayList<ItemBayarSpp>()

    class DetailSppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemBayarSpp: ItemBayarSpp) {
            with(itemView) {
                civ_murid_spplunas.setImageResource(itemBayarSpp.photo!!)
                tv_nama_murid_detailspp.text = itemBayarSpp.namaMurid
                tv_tanggallunas_murid.text = itemBayarSpp.tanggalBayar
                tv_konfirmlunas_murid.text = itemBayarSpp.konfirmasi
            }
        }

    }

    fun addItemToList(list: ArrayList<ItemBayarSpp>) {
        listItem.clear()
        listItem.addAll(list)
    }

    fun setOnItemClickCallback(onItemClickCallback: onClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailSppViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_detail_spp, parent, false)
        return DetailSppViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: DetailSppViewHolder, position: Int) {
        holder.bind(listItem[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClick(listItem[holder.adapterPosition])
        }
    }

    interface onClickCallback {
        fun onItemClick(data: ItemBayarSpp)
    }
}