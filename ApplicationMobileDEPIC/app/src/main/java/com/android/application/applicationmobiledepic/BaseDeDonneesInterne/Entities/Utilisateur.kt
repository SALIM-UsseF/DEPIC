package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Utilisateur")
data class Utilisateur (
    @PrimaryKey var id_utilisateur: Int,
    val email: String,
    val adresseIp: String
)