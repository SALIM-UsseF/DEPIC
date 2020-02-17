package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Classe représentant le modèle des choix.
 * Utilisé pour les questions à choix unique ou multiples.
 * Un choix a
 *      id_choix : un id obtenu du serveur,
 *      intituleChoix : un intitule,
 *      question_id : l'idée de la question concernéee par ce choix.
 */

@Entity(tableName = "Choix")
data class Choix(
    @PrimaryKey val id_choix : Int,
    val intituleChoix : String,
    val question_id : Int
)