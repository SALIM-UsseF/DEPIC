package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

//import android.arch.persistence.room.Entity
//import android.arch.persistence.room.PrimaryKey
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
 * type : Type de la question,
 * questionIntitule : Intitulé de la question
 */
@Entity(tableName = "Questions")

data class Question(
/*
    @PrimaryKey(autoGenerate = true) var questionId: Int = 0,
    var questionIdWeb: Long,
    var sondageId: Int,
    var sondageIdWeb: Long,
    var questionNumero: Int,
    var type: String,
    var questionIntitule: String
*/

@PrimaryKey val id_question : Int,
val sondage_id : Int,
val intitule : String,
val estObligatoire : Boolean,
val nombreChoix : Int?,
val estUnique : Boolean?,
val nombreDeCaractere : Int?,
val numerosDeQuestionsGroupe : String?,
var idQuestionDeGroupe : Int?,
val minPoints : Int?,
val maxPoints : Int?,
val ordre : Int,
val type : String


)