package com.theboys.amiguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_dosen.*

class DetailDosenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_dosen)

        Glide.with(this).load(intent.getStringExtra("POTODOSEN")).into(detailDosenGambar)

        detailDosenNama.text = intent.getStringExtra("NAMADOSEN")
        detailMatkul.text = intent.getStringExtra("MATKULDOSEN")
        detailDosenMeja.text = intent.getStringExtra("LOKASIMEJA")
    }
}
