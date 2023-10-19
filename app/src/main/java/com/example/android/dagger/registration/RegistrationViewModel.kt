/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.dagger.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.dagger.di.ActivityScope
import com.example.android.dagger.models.DatosNuevaPersonaREST
import com.example.android.dagger.services.ServicioUsuarios
import com.example.android.dagger.user.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * RegistrationViewModel is the ViewModel that the Registration flow ([RegistrationActivity]
 * and fragments) uses to keep user's input data.
 *
 * @Inject tells Dagger how to provide instances of this type. Dagger also knows
 * that UserManager is a dependency.
 */
@ActivityScope
class RegistrationViewModel @Inject constructor(
    val userManager: UserManager,
    val servicioUsuarios: ServicioUsuarios

): ViewModel() {

    private var username: String? = null
    private var password: String? = null
    private var acceptedTCs: Boolean? = null

    fun updateUserData(username: String, password: String) {
        this.username = username
        this.password = password
    }

    fun acceptTCs() {
        acceptedTCs = true
    }

    fun registerUser() {
        assert(username != null)
        assert(password != null)
        assert(acceptedTCs == true)
        // peticion al servicio de alta de persona
        username?.let {
            val nuevaPersona = DatosNuevaPersonaREST(it,44,it+"@gmail.com","12345678A")
            viewModelScope.launch {
                // ^ Con esta linea hacemos que si el viewMode se muere, los trabajos se cancelen
                // ^ En que hilo se ejecutan? En el hilo de mi app.. UI... que me lo destroza y deja la interfaz bloqueada
                withContext(Dispatchers.IO){
                    //^ Ese trabajo que va asociado al ciclo de vida de mi view, sejecute en un hilo dedicado a operaciones de IO
                    try{
                        servicioUsuarios.nuevaPersona(nuevaPersona);
                    }catch (e:Exception){
                        Log.e("RegistrationViewModel","Error en petición",e);
                    }
                }
            }
        }

        userManager.registerUser(username!!, password!!)
    }
}
