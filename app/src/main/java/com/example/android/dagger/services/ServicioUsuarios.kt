package com.example.android.dagger.services

import com.example.android.dagger.models.DatosNuevaPersonaREST
import com.example.android.dagger.models.DatosPersonaREST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServicioUsuarios {
        @GET("/api/v1/user/")
        suspend fun recuperarTodasLasPersonas(): Response<List<DatosPersonaREST>>;

        @POST("/api/v1/user/")
        suspend fun nuevaPersona(@Body datosNuevaPersona: DatosNuevaPersonaREST): Response<DatosPersonaREST>;
        //@GET "/api/v1/user/{id}"

}