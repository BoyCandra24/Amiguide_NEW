package com.theboys.amiguide

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_feedback.*
import kotlinx.android.synthetic.main.activity_detail_feedback.*

class DetailFeedback : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_feedback)
        auth = FirebaseAuth.getInstance()

        update_nama.setText(intent.getStringExtra("NAMAFEED"))
        update_feedback.setText(intent.getStringExtra("FEEDBACK"))

        btn_kirim_update.setOnClickListener {
            kirimData(auth.currentUser!!.uid)
        }
    }

    private fun kirimData(uid: String) {

        var datanya = mapOf(
            "uid" to uid,
            "input_nama" to update_nama.text.toString(),
            "input_feedback" to update_feedback.text.toString()
        )

        db.collection("feedbacks").document().update(datanya)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
                    update_nama.text!!.clear()
                    update_feedback.text!!.clear()
                    finish()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal\n${it.message}", Toast.LENGTH_SHORT).show()

            }
    }
}
