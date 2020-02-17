package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

//class Reponse(var id: Long, var reponse: String?, var id_question: Long, var id_Reponse_Sondage: Long)

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.EtatReponse

/**
 * Classe représentant le modèle des réponses,
 * id_reponse : id autogénéré pour la réponse par la base de données interne,
 * id_utilisateur : id de l'utilisateur ayant fait cette réponse,
 * id_sondage : id du sondage contenant la question liée avec cette réponse,
 * question_id : id de la question liée avec cette réponse,
 * reponse : la réponse,
 * etat : les différents états possible spour la réponses (A_REPONDRE, ENREGISTRER, ENVOYER)
 *
 * A_REPONDRE : la réponse n'a ni été enregistrée ni été envoyée.
 * ENREGISTRER : la réponse a été enregistrée dans la base de données interne en attent mais pas envoyée au serveur.
 * ENVOYER : la réponse a été enregistrée dans la base de données interne et envoyée au serveur.
 */
@Entity(tableName = "Reponses")
data class Reponse(
    @PrimaryKey(autoGenerate = true) var id_reponse: Int = 0,
    var id_utilisateur : Int,
    var id_sondage: Int,
    var question_id: Int,
    var reponse: String,
    var etat: String
)