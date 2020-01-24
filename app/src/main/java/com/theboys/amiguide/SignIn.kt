package com.theboys.amiguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()





        btnSignIn.setOnClickListener {
            val email= inputEmail.text.toString()
            val password= inputPassword.text.toString()


            loginMasuk(email, password)


        }



        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }

    }

    private fun loginMasuk (email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                startActivity(Intent(this,MainActivity::class.java))
                Toast.makeText(this,"Login sukses", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
            .addOnFailureListener {
                Toast.makeText(this,"Login gagal", Toast.LENGTH_SHORT).show()
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(currentUser : FirebaseUser?) {

    }

}
