package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Classe représentant le modèle des catégories.
 * Une catégorie est ce à quoi un utilisateur peut s'abonner pour recevoir ses sondages.
 * Chaque sondage à une catégorie.
 * Une catégorie a
 *      id_categorie : un id obtenu du serveur,
 *      intitule : un intitule,
 *      active : un etat active représentant si l'utilisateur veut recevoir les sondages de cette catégorie.
 */
@Entity(tableName = "Categories")
data class Categorie (
    @PrimaryKey var id_categorie: Int,
    var intitule: String,
    var active: Boolean
)