package com.example.android.dagger.models;

import com.squareup.moshi.Json

//import com.squareup.moshi.Json
data class DatosPersonaREST  (
        var id: Int,
//        @Json
        @Json(name = "nombre")
        var name: String,
        var edad: Int,
        var email: String,
        var dni: String
)

