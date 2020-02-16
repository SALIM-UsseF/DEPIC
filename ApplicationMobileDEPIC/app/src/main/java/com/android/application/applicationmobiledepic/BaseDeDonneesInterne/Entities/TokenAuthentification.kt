package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tokens")
data class TokenAuthentification (
    @PrimaryKey(autoGenerate = true)  val id_token : Int,
    var auth_token : String? = null
)