package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO

//import android.arch.persistence.room.*
import androidx.room.*
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.*
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.EtatReponse
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.EtatSondage

@Dao
interface MyDAO {

    // Pour les sondages
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSondages(vararg sondages: Sondage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSondages(sondages: ArrayList<Sondage>)

    @Update
    suspend fun updateSondages(vararg sondages: Sondage)

    @Query("UPDATE Sondages SET etat = :etatSondage WHERE id_sondage = :sondageId")
    suspend fun updateSondage(sondageId: Int, etatSondage: String)

    @Delete
    suspend fun deleteSondages(vararg sondages: Sondage)

    @Delete
    suspend fun deleteArraySondages(sondages: Array<Sondage>)

    @Query("DELETE FROM Sondages")
    suspend fun deleteAllSondages()

    @Query("SELECT * FROM Sondages")
    suspend fun loadAllSondages(): Array<Sondage>

    @Query("Select * FROM Sondages WHERE Sondages.id_sondage = :sondageId")
    suspend fun loadOneSondageFromSondageId(sondageId: Int): Array<Sondage>

    @Query("SELECT Sondages.intituleSondage FROM Sondages")
    suspend fun loadAllNomsSondages(): Array<String>



    // Pour les questions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(vararg questions: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllQuestions(questions: ArrayList<Question>)

    @Update
    suspend fun updateQuestions(vararg questions: Question)

    @Query("Delete FROM Questions")
    suspend fun deleteAllQuestions()

    @Delete
    suspend fun deleteArrayQuestions(questions: Array<Question>)

    @Delete
    suspend fun deleteQuestions(vararg questions: Question)

    @Query("Delete FROM Questions WHERE sondage_id = :sondageId")
    suspend fun deleteQuestionsDeSondage(sondageId: Int)

    @Query("SELECT * FROM Questions")
    suspend fun loadAllQuestions(): Array<Question>

    @Query("Select * FROM Questions WHERE Questions.sondage_id = :sondageId")
    suspend fun loadQuestionsFromSondageID(sondageId: Int): Array<Question>

    @Query("Select * FROM Questions WHERE Questions.id_question = :questionID")
    suspend fun loadOneQuestionFromQuestionId(questionID: Int): Array<Question>



    // Pour les sondages et leurs questions
    @Transaction
    @Query("SELECT * FROM Sondages")
    suspend fun getSondagesWithQuestions(): List<SondageAvecQuestions>



    // Pour les choix
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChoix(vararg choix: Choix)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllChoix(choix: ArrayList<Choix>)

    @Query("Delete FROM Choix")
    suspend fun deleteAllchoix()

    @Query("Delete FROM Choix WHERE question_id = :questionId")
    suspend fun deleteChoixDeQuestion(questionId: Int)

    @Query("SELECT * FROM Choix WHERE Choix.question_id = :questionID")
    suspend fun loadAllChoixPourQuestion(questionID : Int): Array<Choix>

    @Query("SELECT * FROM Choix")
    suspend fun loadAllChoix(): Array<Choix>



    // Pour les reponses
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReponses(vararg reponses: Reponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllReponses(reponses: ArrayList<Reponse>)

    @Update
    suspend fun updateReponses(vararg reponses: Reponse)

    @Query("UPDATE Reponses SET etat = :etatReponse WHERE id_reponse = :id_reponse")
    suspend fun updateReponse( id_reponse : Int, etatReponse: String)

    @Delete fun deleteReponses(vararg reponses: Reponse)

    @Query("Delete FROM Reponses")
    suspend fun deleteAllReponses()

    @Query("DELETE FROM Reponses WHERE id_sondage = :sondageId")
    suspend fun deleteReponsesDeSondage(sondageId: Int)

    @Query("Select * FROM Reponses")
    suspend fun loadAllReponses(): Array<Reponse>



    // Pour les catégories
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories : ArrayList<Categorie>)

    @Query("SELECT * FROM Categories")
    suspend fun loadAllCategories(): Array<Categorie>



    // Pour les utilisateurs
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUtilisateur(utilisateur: Utilisateur)

    @Query("UPDATE Utilisateur SET email = :email WHERE id_utilisateur = :utilisateurID")
    suspend fun updateUtilisateur(utilisateurID : Int, email : String)

    @Query("SELECT * FROM Utilisateur")
    suspend fun loadUtilisateur(): Array<Utilisateur>



    // Pour les tokens d'authentifications
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(vararg token: TokenAuthentification)

    @Query("SELECT * FROM Tokens")
    suspend fun loadTokens(): Array<TokenAuthentification>
}