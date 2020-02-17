package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

//import android.arch.persistence.room.Entity
//import android.arch.persistence.room.PrimaryKey
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.EtatSondage


/**
 * Classe représentant le modèle des sondages,
 * id_sondage : id du sondage obtenu du serveur,
 * intituleSondage : intitule du sondage,
 * administrateur_id : id de l'administrateur ayant créé ce sondage,
 * etat : l'état du sondage, peut être (DISPONIBLE, REPNDU, ENVOYE),
 * categorie_id : id de la catégorie dont fait partie ce sondage.
 *
 * DISPONIBLE : les réponses du sondage n'ont pas encore été enregistrées dans la base de données interne ou envoyées au serveur.
 * REPONDU : les réponses du sondage ont été enregistrées dans la base de données interne mais pas encore envoyées au serveur.
 * ENVOYE : les réponses du sondage ont été enregistrées dans la base de données interne et envoyées au serveur.
 */
@Entity(tableName = "Sondages")
data class Sondage(
/*
    @PrimaryKey(autoGenerate = true) var sondageId: Int = 0,
    var sondageIdWeb: Int,
    var sondageNom: String
*/


@PrimaryKey val id_sondage: Int,
val intituleSondage: String,
val descriptionSondage: String,
val administrateur_id: Int,
var etat: String,
val categorie_id : Int
)