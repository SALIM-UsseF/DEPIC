package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

//import android.arch.persistence.room.Entity
//import android.arch.persistence.room.PrimaryKey
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Entité représentant un sondage,
 * sondageId : id automatique pour la base de données
 * sondageIdWeb : id automatique venant de la base de données du site
 * sondageNom : nom du sondage à afficher
 */
@Entity(tableName = "Sondages")
data class Sondage(
    @PrimaryKey(autoGenerate = true) var sondageId: Int = 0,
    var sondageIdWeb: Int,
    var sondageNom: String
)
