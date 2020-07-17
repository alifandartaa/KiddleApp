package com.example.kiddleapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.kiddleapp.Detail_Kegiatan
import com.example.kiddleapp.R
import com.example.kiddleapp.model.Model_Kegiatan


class Adapter_Kegiatan(private var data:List<Model_Kegiatan>, private val listener: (Model_Kegiatan) -> Unit):RecyclerView.Adapter<Adapter_Kegiatan.ViewHolder>()  {

    lateinit var contextAdapter:Context

    //assign value dari model ke xml
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val tv_judul:TextView = view.findViewById(R.id.tv_judul_list_kegiatan)
        val img_kegiatan :ImageView = view.findViewById(R.id.img_banner_list_kegiatan)
         val vv_kegiatan :VideoView = view.findViewById(R.id.vv_banner_list_kegiatan)
         val btn :Button = view.findViewById(R.id.btn_list_kegiatan)
        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model

        fun bindItem(data:Model_Kegiatan, listener: (Model_Kegiatan) -> Unit, context: Context, position: Int) {
            tv_judul.text = data.judul

            if(data.gambar!=0)
            {
               img_kegiatan.setVisibility(View.VISIBLE);
                vv_kegiatan.setVisibility(View.GONE);
                img_kegiatan.setImageResource(data.gambar)
            }
            else if(data.video!=0)
            {
                img_kegiatan.setVisibility(View.VISIBLE);
                vv_kegiatan.setVisibility(View.GONE);
                vv_kegiatan.setVideoURI(Uri.parse("android.resource://" + context.getPackageName() +"/"+data.video))
                vv_kegiatan.seekTo( 10 );
                //vv_kegiatan.start()
               // var media_Controller: MediaController = MediaController(this)
                //vv_kegiatan.setMediaController(media_Controller)
               // media_Controller.setAnchorView(vv_kegiatan)

            }

            itemView.setOnClickListener {
                listener(data)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_kegiatan, parent, false)
        return ViewHolder(inflatedView)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
        // biar gambarnya ada radiusnya
        if(data[position].gambar!=0){
            val url: Int = data[position].gambar
            Glide.with(contextAdapter)
                .load(url)
                .transform(RoundedCorners(32))
                .into(holder.img_kegiatan)

        }else if(data[position].video!=0){
            val url2: Int = data[position].video
            Glide.with(contextAdapter)
                .load(Uri.parse("android.resource://" + contextAdapter.getPackageName() +"/"+url2))
                .transform(RoundedCorners(32))
                .into(holder.img_kegiatan)
        }
        //biar tobolnya bisa diklik seklian parsng data
            holder.btn.setOnClickListener { v ->
            val i = Intent(v.context, Detail_Kegiatan::class.java).putExtra("data",data[position])
            v.context.startActivity(i)
        }


    }
}