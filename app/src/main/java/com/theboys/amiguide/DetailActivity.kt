package com.theboys.amiguide

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.custom_appbar.*

class DetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        appBarText.text = "Detail Gedung"
        Glide.with(this).load(intent.getStringExtra("POTOGEDUNG")).into(detailGedungGambar)

        detailGedungNama.text = intent.getStringExtra("NAMAGEDUNG")
        detailGedungDesc.text = intent.getStringExtra("DESCGEDUNG")
        detailGedungLocation.text = intent.getStringExtra("LOKASIGEDUNG")


    }

}
