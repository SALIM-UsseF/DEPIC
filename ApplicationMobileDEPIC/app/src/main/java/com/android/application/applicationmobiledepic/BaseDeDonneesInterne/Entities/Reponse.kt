package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

//class Reponse(var id: Long, var reponse: String?, var id_question: Long, var id_Reponse_Sondage: Long)

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entité représentant une question,
 * questionId : id autogénéré pour la bdd,
 * questionIdWeb : id autogénéré pour la bdd du site,
 * id_Question_Web : id de la question dans la bdd du site,
 * sondageId : id du sondage dans cette bdd,
 * sondageIdWeb : id du sondage dans la bdd du site,
 * questionNumero : Numéro de la question dans le sondage,
 * questionType : Type de la question,
 * questionIntitule : Intitulé de la question
 */
@Entity(tableName = "Reponses")
data class Reponse(
    @PrimaryKey(autoGenerate = true) var id_reponse: Int = 0,
    var id_utilisateur : Int,
    var id_sondage: Int,
    var question_id: Int,
    var reponse: String
)