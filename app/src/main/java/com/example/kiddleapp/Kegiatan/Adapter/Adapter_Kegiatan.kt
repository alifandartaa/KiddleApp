package com.example.kiddleapp.Kegiatan.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.kiddleapp.Jurnal.DetailJurnalActivity
import com.example.kiddleapp.Jurnal.Model.Jurnal
import com.example.kiddleapp.Kegiatan.DetailKegiatanActivity
import com.example.kiddleapp.Kegiatan.Model.Kegiatan
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_detail_jurnal.*


class Adapter_Kegiatan(private var data: List<Kegiatan>, private val listener: (Kegiatan) -> Unit) :
    RecyclerView.Adapter<Adapter_Kegiatan.ViewHolder>() {

    lateinit var contextAdapter: Context
    private var listKegiatan= ArrayList<Kegiatan>()

    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv_judul: TextView = view.findViewById(R.id.tv_judul_list_kegiatan)
        val img_kegiatan: ImageView = view.findViewById(R.id.img_banner_list_kegiatan)
        val vv_kegiatan: VideoView = view.findViewById(R.id.vv_banner_list_kegiatan)
        //   val btn :Button = view.findViewById(R.id.btn_list_kegiatan)
        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model

        fun bindItem(
            data: Kegiatan,
            listener: (Kegiatan) -> Unit,
            context: Context,
            position: Int
        ) {
            tv_judul.text = data.judul

            if (data.gambar != "") {
                img_kegiatan.visibility = View.VISIBLE
                vv_kegiatan.visibility = View.GONE
                Glide.with(context).load(data.gambar).centerCrop().into(img_kegiatan)

            } else if (data.video != "") {
                img_kegiatan.visibility = View.VISIBLE
                vv_kegiatan.visibility = View.GONE
                vv_kegiatan.setVideoURI(Uri.parse(data.video))
                vv_kegiatan.seekTo(10)
                //vv_kegiatan.start()
                //  var media_Controller: MediaController = MediaController(this)
                //vv_kegiatan.setMediaController(media_Controller)
                // media_Controller.setAnchorView(vv_kegiatan)

            }else{
                img_kegiatan.visibility = View.VISIBLE
                vv_kegiatan.visibility = View.GONE
                Glide.with(context).load(R.drawable.black_layer).centerCrop().into(img_kegiatan)
            }

            itemView.setOnClickListener {
                listener(data)
            }
            listener.invoke(data)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_kegiatan, parent, false)
        return ViewHolder(
            inflatedView
        )

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
        // biar gambarnya ada radiusnya
        if(data[position].gambar!=""){
            val url: String? = data[position].gambar
            Glide.with(contextAdapter)
                .load(url)
                .centerCrop()
                .transform(RoundedCorners(32))
                .into(holder.img_kegiatan)

        }else if(data[position].video!=""){
            val url2: String? = data[position].video
            Glide.with(contextAdapter)
                .load(Uri.parse(url2))
                .centerCrop()
                .transform(RoundedCorners(32))
                .into(holder.img_kegiatan)
        }else{
            Glide.with(contextAdapter).load(R.drawable.black_layer).centerCrop().transform(RoundedCorners(32)).into(holder.img_kegiatan)
        }

        holder.itemView.setOnClickListener(View.OnClickListener { v ->
            val intent: Intent = Intent(v.context, DetailKegiatanActivity::class.java).putExtra("data", data[position])
            v.context.startActivity(intent)
        })
//        //biar tobolnya bisa diklik seklian parsng data
//            holder.btn.setOnClickListener { v ->
//            val i = Intent(v.context, DetailKegiatanActivity::class.java).putExtra("data",data[position])
//            v.context.startActivity(i)
//        }


    }

    fun addItemToList(list: ArrayList<Kegiatan>) {
        listKegiatan.clear()
        listKegiatan.addAll(list)
    }
}