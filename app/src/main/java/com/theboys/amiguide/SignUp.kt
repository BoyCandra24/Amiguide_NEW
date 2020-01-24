package com.theboys.amiguide

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.io.IOException

class SignUp : AppCompatActivity(){

    private val PICK_IMAGE_REQUEST = 1234

    private var filePath: Uri? = null
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference


        btn_uploadPhoto.setOnClickListener{
            pilihFile()

        }
        // Create a new user with a first and last name

        btn_daftar_daftar.setOnClickListener {
            signUpUser()
        }



        btn_masuk_daftar.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST &&
            resultCode == Activity.RESULT_OK &&
            data != null && data.data !=null)
        {
            filePath = data.data;

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filePath)
                image_view!!.setImageBitmap(bitmap)
            } catch (e:IOException)
            {
                e.printStackTrace()
            }
/*            val uploadTask = storageReference!!.putFile(data!!.data!!)
            val taks = uploadTask.continueWithTask {
                taks ->
                if (!taks.isSuccessful){
                 Toast.makeText(this, "GAGAL",Toast.LENGTH_LONG).show();
                }

                storageReference!!.downloadUrl
            }.addOnCompleteListener {
                taks ->
                if (taks.isSuccessful){
                    val downloadUri = taks.result
                    val url = downloadUri!!.toString().substring(0,downloadUri.toString().indexOf("&token"))
                    Log.d("LINKIMG", url)
                    Picasso.get().load(url).into(image_view)

                }
            }*/
        }
    }





    private fun pilihFile(){
        val intent = Intent()
        intent.type="image/*"
        intent.action= Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), PICK_IMAGE_REQUEST)
    }



    private fun signUpUser() {


        val passwordnya = input_pass_signin.text.toString()
        val nimnya = input_nim_signin.text.toString()
        val namanya = input_nama_signin.text.toString()

        val emailnya = input_email_signin.text.toString()
        if (input_email_signin.text.toString().isEmpty()) {
            input_email_signin.error = "Tolong masukan email"
            input_email_signin.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(input_email_signin.text.toString()).matches()) {
            input_email_signin.error = "Tolong masukan email"
            input_email_signin.requestFocus()
            return
        }

        if (input_pass_signin.text.toString().isEmpty()) {
            input_pass_signin.error = "Tolong masukan NIM"
            input_pass_signin.requestFocus()
            return

        }

        auth.createUserWithEmailAndPassword(emailnya, passwordnya)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(
                        this, "Berhasil ${task.result}",
                        Toast.LENGTH_SHORT
                    ).show()
                    dataKu(
                        auth.currentUser!!.uid,
                        emailnya,
                        namanya,
                        nimnya,
                        passwordnya
                    )


                    startActivity(Intent(this, SignIn::class.java))
                    finish()
                }

                // ...
            }

            .addOnFailureListener {
                Toast.makeText(
                    baseContext, "Gagal mendaftar\n${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun dataKu(userId: String, email: String, nama: String, nim: String, password: String) {

        val dataku = hashMapOf(
            "email" to email,
            "nama" to nama,
            "nim" to nim,
            "pass" to password
        )

        db.collection("users").document(userId).set(dataku as Map<String, Any>)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal\n${it.message}", Toast.LENGTH_SHORT).show()

            }


    }
}
