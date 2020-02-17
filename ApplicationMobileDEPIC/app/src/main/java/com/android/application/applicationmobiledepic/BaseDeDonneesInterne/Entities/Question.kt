package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

//import android.arch.persistence.room.Entity
//import android.arch.persistence.room.PrimaryKey
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Classe représentant le modèle des questions,
 *      id_question : id de la question obtenu du serveur,
 *      sondage_id : id du sondage contenant cette question,
 *      intitule : intitule de la question,
 *      estObligatoire : etat de la question déterminant si elle est obligatoire,
 *      estUnique : état de la question déterminant en cas de question de type choix si c'est une question à choix multiple ou à choix unique,
 *      nombreDeCaractere : le nombre de caractères maximum autorisé pour une question à réponse libre,
 *      idQuestionDeGroupe : si la question est une sous-question, id de la question de type GroupeQuestion,
 *      minPoints : points minimum possible à donner à une question de type QuestionPoint,
 *      maxpoints : points maximum possible à donner à une question de type QuestionPoint,
 *      ordre : le numéro de la question dans le sondage,
 *      type : le type de la question.
 */
@Entity(tableName = "Questions")
data class Question(
@PrimaryKey val id_question : Int,
val sondage_id : Int,
val intitule : String,
val estObligatoire : Boolean,
val estUnique : Boolean?,
val nombreDeCaractere : Int?,
var idQuestionDeGroupe : Int?,
val minPoints : Int?,
val maxPoints : Int?,
val ordre : Int,
val type : String
)