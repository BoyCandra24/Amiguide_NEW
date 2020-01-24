package com.theboys.amiguide.viewholder

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theboys.amiguide.DetailActivity
import com.theboys.amiguide.R
import com.theboys.amiguide.model.GedungModel

class GedungViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var nameGedung: TextView = itemView.findViewById(R.id.tv_nama_gedung)
    private var lokasiGedung: TextView = itemView.findViewById(R.id.tv_item_from)
    private var photoGedung: ImageView = itemView.findViewById(R.id.img_item_photo)
    private var containerGedung: RelativeLayout = itemView.findViewById(R.id.item_container)

    fun bind(gedung: GedungModel) {

        nameGedung.text = gedung.namaGedung
        lokasiGedung.text = gedung.lokasiGedung
        Glide.with(itemView.context).load(gedung.photoGedung).into(photoGedung)


        containerGedung.setOnClickListener {
            kirimData(gedung)
        }
    }

    private fun kirimData(gedung: GedungModel) {
        val paket = Intent(itemView.context, DetailActivity::class.java)
        paket.putExtra("NAMAGEDUNG", gedung.namaGedung.toString())
        paket.putExtra("DESCGEDUNG", gedung.detailGedung.toString())
        paket.putExtra("POTOGEDUNG", gedung.photoGedung.toString())
        paket.putExtra("LOKASIGEDUNG", gedung.lokasiGedung.toString())
        itemView.context.startActivity(paket)
    }
}