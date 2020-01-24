package com.theboys.amiguide.model

import java.io.Serializable

data class DosenModel(
    var matkul: String? = "",
    var namaDosen: String? = "",
    var photoDosen: String? = "",
    var lokasiMejaDosen: String? = ""

) : Serializable