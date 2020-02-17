package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Les utilisateurs de l'application, anonyme ou connu.
 * Un utilisateur a
 *      id_utilisateur : l'id de l'utilisateur obtenu du serveur,
 *      email : un email, si anonyme vaut ""
 *      adresseIP : une adresse IP, vaut toujours "".  (nécessaire pour la réception de la requête de création d'utilisateur)
 */
@Entity(tableName = "Utilisateur")
data class Utilisateur (
    @PrimaryKey var id_utilisateur: Int,
    val email: String,
    val adresseIp: String
)