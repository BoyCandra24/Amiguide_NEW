package com.theboys.amiguide.model


import java.io.Serializable

data class FeedbackModel(
    var uid: String? = "",
    var input_nama: String? = "",
    var input_feedback: String? = "",
    var feedback_id: String? = ""
) : Serializable


