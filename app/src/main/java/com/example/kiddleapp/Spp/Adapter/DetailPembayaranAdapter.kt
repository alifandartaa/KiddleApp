package com.example.kiddleapp.Spp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiddleapp.R
import com.example.kiddleapp.Spp.Model.PembayaranMurid
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class DetailPembayaranAdapter(private var data: List<PembayaranMurid>, private val listener: (PembayaranMurid) -> Unit) : RecyclerView.Adapter<DetailPembayaranAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context
    private var listDetail = ArrayList<PembayaranMurid>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tv_nama: TextView = view.findViewById(R.id.tv_detail_nama)
        private val tv_status: TextView = view.findViewById(R.id.tv_detail_status)
        private val img_avatar:ImageView = view.findViewById(R.id.img_detail_avatar)

        fun bindItem(data:PembayaranMurid, listener: (PembayaranMurid) -> Unit, context: Context, position: Int) {
            FirebaseFirestore.getInstance().collection("Murid").whereEqualTo("nomor", data.id_murid).addSnapshotListener { result, e ->
                if(e!= null){
                    return@addSnapshotListener
                }
                for(document in result!!){
                    tv_nama.text = document.getString("nama")
                    Glide.with(context).load(document.getString("avatar")).centerCrop().into(img_avatar)
                }
            }
            tv_status.text = data.status
            itemView.setOnClickListener {
                listener(data)
            }
            listener.invoke(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_detail_pembayaran, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItemToList(list: ArrayList<PembayaranMurid>){
        listDetail.clear()
        listDetail.addAll(list)
    }
}