package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Token d'authentification pour l'accès au serveur.
 * Un token a
 *      id_token : l'id autogénéré du token,
 *      auth_token : le texte contenant la clé d'authentification.
 */
@Entity(tableName = "Tokens")
data class TokenAuthentification (
    @PrimaryKey(autoGenerate = true)  val id_token : Int,
    var auth_token : String? = null
)