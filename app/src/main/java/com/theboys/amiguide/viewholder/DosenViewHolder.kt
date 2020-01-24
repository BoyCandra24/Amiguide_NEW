package com.theboys.amiguide.viewholder



import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theboys.amiguide.DetailDosenActivity
import com.theboys.amiguide.R
import com.theboys.amiguide.model.DosenModel


class DosenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var namaDosen: TextView = itemView.findViewById(R.id.tv_nama_Dosen)
    private var matkulDosen: TextView = itemView.findViewById(R.id.tv_item_from_dosen)
    private var photoDosen: ImageView = itemView.findViewById(R.id.img_item_dosen)
    private var containerDosen: RelativeLayout = itemView.findViewById(R.id.item_container_dosen)

    fun bind(dosen: DosenModel) {

        namaDosen.text = dosen.namaDosen
        matkulDosen.text = dosen.matkul
        Glide.with(itemView.context).load(dosen.photoDosen).into(photoDosen)


        containerDosen.setOnClickListener {
            kirimData1(dosen)
        }
    }

    private fun kirimData1(dosen: DosenModel) {
        val paket = Intent(itemView.context, DetailDosenActivity::class.java)
        paket.putExtra("NAMADOSEN", dosen.namaDosen.toString())
        paket.putExtra("MATKULDOSEN", dosen.matkul.toString())
        paket.putExtra("POTODOSEN", dosen.photoDosen.toString())
        paket.putExtra("LOKASIMEJA", dosen.lokasiMejaDosen.toString())
        itemView.context.startActivity(paket)
    }
}