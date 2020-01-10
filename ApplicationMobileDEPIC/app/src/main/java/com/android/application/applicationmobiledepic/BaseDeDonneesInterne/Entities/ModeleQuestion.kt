package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

class ModeleQuestion(val id_question : Int,
                     val sondage_id : Int,
                     val intitule : String,
                     val estObligatoire : Boolean,
                     val nombreChoix : Int,
                     val estUnique : Boolean,
                     val nombreDeCaractere : Int,
                     val numerosDeQuestionsGroupe : String,
                     val ordre : Int,
                     val type : String)
