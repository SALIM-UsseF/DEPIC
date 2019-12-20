package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO

//import android.arch.persistence.room.*
import androidx.room.*
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Question
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Reponse
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Sondage
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.SondageAvecQuestions

@Dao
interface MyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSondages(vararg sondages: Sondage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(vararg questions: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReponses(vararg reponses: Reponse)


    @Update
    suspend fun updateSondages(vararg sondages: Sondage)

    @Update
    suspend fun updateQuestions(vararg questions: Question)

    @Update
    suspend fun updateReponses(vararg reponses: Reponse)


    @Delete
    suspend fun deleteSondages(vararg sondages: Sondage)

    @Delete
    suspend fun deleteQuestions(vararg questions: Question)

    @Delete fun deleteReponses(vararg reponses: Reponse)

    @Delete
    suspend fun deleteArraySondages(sondages: Array<Sondage>)

    @Delete
    suspend fun deleteArrayQuestions(questions: Array<Question>)

    @Query("DELETE FROM Sondages")
    suspend fun deleteAllSondages()

    @Query("Delete FROM Questions")
    suspend fun deleteAllQuestions()

    @Query("Delete FROM Reponses")
    suspend fun deleteAllReponses()




    @Transaction
    @Query("SELECT * FROM Sondages")
    suspend fun getSondagesWithQuestions(): List<SondageAvecQuestions>




    @Query("SELECT * FROM Questions")
    suspend fun loadAllQuestions(): Array<Question>

    @Query("Select * FROM Questions WHERE Questions.sondageId = :sondageId")
    suspend fun loadOneQuestionFromSondageID(sondageId: Int): Array<Question>

    @Query("Select * FROM Questions WHERE Questions.questionId = :questionID")
    suspend fun loadOneQuestionFromQuestionId(questionID: Int): Array<Question>

    @Query("SELECT * FROM Sondages")
    suspend fun loadAllSondages(): Array<Sondage>

    @Query("Select * FROM Sondages WHERE Sondages.sondageId = :sondageId")
    suspend fun loadOneSondageFromSondageId(sondageId: Int): Array<Sondage>

    @Query("SELECT Sondages.sondageNom FROM Sondages")
    suspend fun loadAllNomsSondages(): Array<String>

    @Query("Select * FROM Reponses")
    suspend fun loadAllReponses(): Array<Reponse>
}