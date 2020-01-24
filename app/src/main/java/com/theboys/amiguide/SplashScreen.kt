package com.theboys.amiguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (auth.currentUser == null) {
            setContentView(R.layout.activity_splash_screen)
            Handler().postDelayed(
                {
                    startActivity(Intent(this, SignIn::class.java))
                    finish()
                }, 3000
            )
        }else{
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }
}
