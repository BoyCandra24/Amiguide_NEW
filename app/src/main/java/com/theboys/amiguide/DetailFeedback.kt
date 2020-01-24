package com.theboys.amiguide

import android.content.Intent
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
        val fid = intent.getStringExtra("FEEDBACKID")

        btn_kirim_update.setOnClickListener {
            kirimData(auth.currentUser!!.uid, fid)
        }
    }

    private fun kirimData(uid: String, feedbackId: String) {

        var datanya = mapOf(
            "uid" to uid,
            "input_nama" to update_nama.text.toString(),
            "input_feedback" to update_feedback.text.toString()
        )

        db.collection("feedbacks").document(feedbackId).update(datanya)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
                    update_nama.text!!.clear()
                    update_feedback.text!!.clear()
                    finishAffinity()
                    startActivity(Intent(this, FeedbackActivity::class.java))
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal\n${it.message}", Toast.LENGTH_SHORT).show()

            }
    }
}
