package com.example.kiddleapp.Tugas.Adapter

import android.app.ProgressDialog
import android.content.Context
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.Model.HasilTugas
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class HasilTugasAdapter(
    private var data: List<HasilTugas>,
    private val listener: (HasilTugas) -> Unit
) : RecyclerView.Adapter<HasilTugasAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context
    private var listHasil = ArrayList<HasilTugas>()
    private val db = FirebaseFirestore.getInstance()
   var storage = FirebaseStorage.getInstance().reference
   // var storage = FirebaseStorage.getInstance()
//    var storageRef: StorageReference = storage.getReferenceFromUrl("<your_bucket>")
//    var islandRef = storageRef.child("file.txt")

    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img_avatar: ImageView = view.findViewById(R.id.img_avatar_murid_tugas)
        val tv_nama: TextView = view.findViewById(R.id.tv_nama_murid_tugas)
        private val tv_tanggal: TextView = view.findViewById(R.id.tv_tanggal_murid_tugas)
        val img_download_tugas:ImageView=view.findViewById(R.id.img_download_tugas)
        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model

        fun bindItem(
            data: HasilTugas,
            listener: (HasilTugas) -> Unit,
            context: Context,
            position: Int
        ) {

            Glide.with(context).load(data.avatar).centerCrop().into(img_avatar)
            tv_nama.text = data.nama
            tv_tanggal.text = data.tanggal
            itemView.setOnClickListener {
                listener(data)
            }

            listener.invoke(data)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_hasil_tugas, parent, false)
        return ViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)

        holder.img_download_tugas.setOnClickListener(View.OnClickListener { v ->
            MaterialAlertDialogBuilder(contextAdapter).apply {
                setTitle("Unduh Berkas")
                setMessage(
                    "Apa anda yakin ingin mengunduh berkas pengumpulan ini?"
                )
                setPositiveButton("Ya") { _, _ ->
                    val storage = FirebaseStorage.getInstance()
                    val storageRef =storage.getReferenceFromUrl(data[position].file!!)
                    val pd = ProgressDialog(context)
                    pd.setTitle(data[position].id_hasil_tugas + "_"+holder.tv_nama.text.toString() )
                    pd.show()


                    val rootPath = File(
                        Environment.getExternalStorageDirectory(),
                        "KiddleApp"
                    )

                    if (!rootPath.exists()) {
                        rootPath.mkdirs()
                    }


                    val localFile = File(rootPath, data[position].id_hasil_tugas + "_"+holder.tv_nama.text.toString())

                    storageRef.getFile(localFile).addOnSuccessListener(object :
                        OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
                        override fun onSuccess(taskSnapshot: FileDownloadTask.TaskSnapshot?) {
                            Log.e(
                                "firebase ",
                                ";local tem file created  created $localFile"
                            )
//                            if (!isVisible()) {
//                                return
//                            }
                            if (localFile.canRead()) {
                                pd.dismiss()
                            }
                            Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show()
                           // Toast.makeText(context, "Internal storage/MADBO/Nature.jpg", Toast.LENGTH_LONG).show()
                        }

                    }).addOnFailureListener(object : OnFailureListener {
                        override fun onFailure(exception: Exception) {
                            Log.e(
                                "firebase ",
                                ";local tem file not created  created $exception"
                            )
                            Toast.makeText(context, "Download Incompleted", Toast.LENGTH_LONG).show()
                        }
                    })
                }
                setNegativeButton("Tidak") { _, _ -> }
            }.show()
         //   val intent: Intent = Intent(v.context, DetailTugasActivity::class.java).putExtra("data", data[position])
         //   v.context.startActivity(intent)
        })
    }

    fun addItemToList(list: ArrayList<HasilTugas>) {
        listHasil.clear()
        listHasil.addAll(list)
    }
}