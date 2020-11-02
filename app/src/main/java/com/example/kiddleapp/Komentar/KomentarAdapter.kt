package com.example.kiddleapp.Komentar

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.Model.HasilTugas
import com.google.firebase.firestore.FirebaseFirestore


class KomentarAdapter(private var data: List<Komentar>, private val listener: (Komentar) -> Unit) :
    RecyclerView.Adapter<KomentarAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context
    private var listKomentar = ArrayList<Komentar>()

    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img_avatar: ImageView = view.findViewById(R.id.img_komentar)
        private val tv_nama: TextView = view.findViewById(R.id.tv_nama_komentar)
        private val tv_jabatan: TextView = view.findViewById(R.id.tv_jabatan_komentar)
        private val tv_isi: TextView = view.findViewById(R.id.tv_isi_komentar)


        fun bindItem(data: Komentar, listener: (Komentar) -> Unit, context: Context, position: Int) {
            FirebaseFirestore.getInstance().collection("${data.koleksi}").whereEqualTo("nomor",data.id_guru).addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (document in result!!) {
                    Glide.with(context).load(document.getString("avatar")).centerCrop().into(img_avatar)
                    tv_nama.text = document.getString("nama")
                    if(data.koleksi == "Guru"){
                        tv_jabatan.text = document.getString("jabatan")
                    } else{
                        tv_jabatan.text = "Murid ${document.getString("kelas")}"
                    }

                }
            }
            tv_isi.text = data.isi
            itemView.setOnClickListener {
                listener(data)
            }
            listener.invoke(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_komentar, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    fun addItemToList(list: ArrayList<Komentar>) {
        listKomentar.clear()
        listKomentar.addAll(list)
    }

}

