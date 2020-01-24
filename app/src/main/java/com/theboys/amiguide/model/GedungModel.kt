package com.theboys.amiguide.model

import java.io.Serializable

data class GedungModel(
    var idGedung: String? = "",
    var namaGedung: String? = "",
    var photoGedung: String? = "",
    var lokasiGedung: String? = "",
    var detailGedung: String? = ""
) : Serializable


