package com.theboys.amiguide.viewholder


import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theboys.amiguide.DetailDosenActivity
import com.theboys.amiguide.DetailFeedback
import com.theboys.amiguide.FeedbackActivity
import com.theboys.amiguide.R
import com.theboys.amiguide.model.DosenModel
import com.theboys.amiguide.model.FeedbackModel

class FeedbackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var inputNama: TextView = itemView.findViewById(R.id.tv_nama_feedback)
    private var inputFeedback: TextView = itemView.findViewById(R.id.tv_feedback)
    private var editFeedback: ImageView = itemView.findViewById(R.id.editFeddback)
    private var delFeedback: ImageView = itemView.findViewById(R.id.deleteFeddback)
    private var uidSaya: String = FirebaseAuth.getInstance().currentUser!!.uid
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun bind(feedback: FeedbackModel) {

        inputNama.text = feedback.input_nama
        inputFeedback.text = feedback.input_feedback

        editFeedback.setOnClickListener {
            if(feedback.uid == uidSaya){
                kirimDataEdit(feedback)
            }else{
                Toast.makeText(itemView.context, "Feddback ini bukan punya anda", Toast.LENGTH_SHORT).show()
            }
        }

        delFeedback.setOnClickListener {
            if(feedback.uid == uidSaya){
                db.collection("feedbacks").document(feedback.feedback_id.toString()).delete().addOnSuccessListener {
                    Toast.makeText(itemView.context, "Berhasil dihapus silahkan refresh", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(itemView.context, "Feddback ini bukan punya anda", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun kirimDataEdit(feedback: FeedbackModel) {
        val paket = Intent(itemView.context, DetailFeedback::class.java)
        paket.putExtra("UIDFEED", feedback.uid.toString())
        paket.putExtra("NAMAFEED", feedback.input_nama.toString())
        paket.putExtra("FEEDBACK", feedback.input_feedback.toString())
        paket.putExtra("FEEDBACKID", feedback.feedback_id.toString())
        itemView.context.startActivity(paket)
    }
}