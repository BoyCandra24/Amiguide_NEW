package com.theboys.amiguide

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_feedback.*
import java.util.*

class AddFeedback : AppCompatActivity() {

    //buat variable firestore instance dengan nama db
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    //variable fireauth instance dengan nama auth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_feedback)
        auth = FirebaseAuth.getInstance()
        Log.d("UID", auth.currentUser!!.uid)
        getNama(auth.currentUser!!.uid)
        btn_kirim.setOnClickListener {
            kirimData(auth.currentUser!!.uid)
        }
    }

    private fun getNama(uid: String) {
        db.collection("users").document(uid).get().addOnSuccessListener {
            input_nama.setText(it.getString("nama"))
            input_nama.visibility = View.GONE
        }
    }

    private fun kirimData(uid: String) {

        var feedbackId = UUID.randomUUID().toString()

        Log.d("FEEDBACK", feedbackId)

        var datanya = mapOf(
            "uid" to uid,
            "input_nama" to input_nama.text.toString(),
            "input_feedback" to input_feedback.text.toString(),
            "feedback_id" to feedbackId
        )

        db.collection("feedbacks").document(feedbackId).set(datanya)
            .addOnSuccessListener {
                Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
                input_nama.text!!.clear()
                input_feedback.text!!.clear()
                finishAffinity()
                startActivity(Intent(this, FeedbackActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal\n${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
