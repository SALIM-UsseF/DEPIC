package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO

//import android.arch.persistence.room.*
import androidx.room.*
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.*

@Dao
interface MyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSondages(vararg sondages: Sondage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(vararg questions: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReponses(vararg reponses: Reponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChoix(vararg choix: Choix)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSondages(sondages: ArrayList<Sondage>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllQuestions(questions: ArrayList<Question>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllReponses(reponses: ArrayList<Reponse>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllChoix(choix: ArrayList<Choix>)


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

    @Query("Delete FROM Choix")
    suspend fun deleteAllchoix()





    @Transaction
    @Query("SELECT * FROM Sondages")
    suspend fun getSondagesWithQuestions(): List<SondageAvecQuestions>




    @Query("SELECT * FROM Questions")
    suspend fun loadAllQuestions(): Array<Question>

    @Query("Select * FROM Questions WHERE Questions.sondage_id = :sondageId")
    suspend fun loadOneQuestionFromSondageID(sondageId: Int): Array<Question>

    @Query("Select * FROM Questions WHERE Questions.id_question = :questionID")
    suspend fun loadOneQuestionFromQuestionId(questionID: Int): Array<Question>

    @Query("SELECT * FROM Sondages")
    suspend fun loadAllSondages(): Array<Sondage>

    @Query("Select * FROM Sondages WHERE Sondages.id_sondage = :sondageId")
    suspend fun loadOneSondageFromSondageId(sondageId: Int): Array<Sondage>

    @Query("SELECT Sondages.intituleSondage FROM Sondages")
    suspend fun loadAllNomsSondages(): Array<String>

    @Query("Select * FROM Reponses")
    suspend fun loadAllReponses(): Array<Reponse>

    @Query("SELECT * FROM Choix WHERE Choix.id_question = :questionID")
    suspend fun loadAllChoixPourQuestion(questionID : Int): Array<Choix>

    @Query("SELECT * FROM Choix")
    suspend fun loadAllChoix(): Array<Choix>
}