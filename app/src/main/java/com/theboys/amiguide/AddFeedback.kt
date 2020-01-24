package com.theboys.amiguide

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_feedback.*

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

    private fun getNama(uid: String){
        db.collection("users").document(uid).get().addOnSuccessListener {
            input_nama.setText(it.getString("nama"))
            input_nama.visibility = View.GONE
        }
    }

    private fun kirimData(uid: String) {

        var datanya = mapOf(
            "uid" to uid,
            "input_nama" to input_nama.text.toString(),
            "input_feedback" to input_feedback.text.toString()
        )

        db.collection("feedbacks").document().set(datanya)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
                    input_nama.text!!.clear()
                    input_feedback.text!!.clear()
                    finish()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal\n${it.message}", Toast.LENGTH_SHORT).show()

            }
    }
}
