package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionAvecChoix (
    @Embedded val question: Question,
    @Relation(
        parentColumn = "id_question",
        entityColumn = "id_choix"
    )
    val choix : List<Choix>
)
