package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

//import android.arch.persistence.room.Embedded
//import android.arch.persistence.room.Relation
import androidx.room.Embedded
import androidx.room.Relation

data class SondageAvecQuestions (
    @Embedded val sondage: Sondage,
    @Relation(
//        parentColumn = "sondageId",
//        entityColumn = "questionId"
        parentColumn = "id_sondage",
        entityColumn = "id_question"
    )
    val questions : List<Question>
)
