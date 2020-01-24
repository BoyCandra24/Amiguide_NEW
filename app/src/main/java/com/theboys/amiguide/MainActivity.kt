package com.theboys.amiguide

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUserData(auth.currentUser!!.uid)
        setContentView(R.layout.activity_main)

        buttonListGedung.setOnClickListener {
            startActivity(Intent(this, GedungActivity::class.java))
        }
        buttonListDosen.setOnClickListener {
            startActivity(Intent(this, DosenActivity::class.java))
        }
        logo.setOnClickListener {
            auth.signOut()
            if (auth.currentUser == null) {
                startActivity(Intent(this, SignIn::class.java))
                finishAffinity()
            }
        }
        buttonFeedback.setOnClickListener {
            startActivity(Intent(this,FeedbackActivity::class.java))
        }

    }

    private fun getUserData(UID: String) {
        db.collection("users")
            .document(UID)
            .get().addOnSuccessListener {
                namaKu.text = it.getString("nama")
                emailKu.text = it.getString("email")
            }
    }

}
