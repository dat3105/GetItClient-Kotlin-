package com.dinhconghien.getitapp.Models

import java.util.*

data class BrandLapName(var idBrandLap: String = "",
          var nameBrand : String = "",
          var avaBrandLap : String = ""
) {
    override fun toString(): String {
        return nameBrand.toUpperCase(Locale.ENGLISH)
    }
}