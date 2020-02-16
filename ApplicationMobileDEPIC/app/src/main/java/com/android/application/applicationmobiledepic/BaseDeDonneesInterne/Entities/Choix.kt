package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Choix")
data class Choix(
    @PrimaryKey val id_choix : Int,
    val intituleChoix : String,
    val question_id : Int
)