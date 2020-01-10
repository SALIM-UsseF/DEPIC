package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Reponse
//
//class ReponseDAO(pContext: Context) : DAOBase(pContext) {
//
//    /**
//     * @param p la reponse à ajouter à la base
//     */
//    fun ajouter(p: Reponse?) {
//        if (p != null) {
//            val value = ContentValues()
//            value.put(ReponseDAO.REPONSE, p.reponse)
//            value.put(ReponseDAO.ID_QUESTION, p.id_question)
//            db!!.insert(ReponseDAO.TABLE_NAME, null, value)
//        } else {
//            Log.e("Ajouter ReponseDAO", "La reponse est nulle.")
//        }
//    }
//
//    /**
//     * @param id l'identifiant de la reponse à supprimer
//     */
//    fun supprimer(id: Long) {
//        db!!.delete(TABLE_NAME, "$KEY = ?", arrayOf(id.toString()))
//    }
//
//    /**
//     * @param p la reponse modifié
//     */
//    fun modifier(p: Reponse) {
//        val value = ContentValues()
//        value.put(ReponseDAO.REPONSE, p.reponse)
//        value.put(ReponseDAO.ID_QUESTION, p.id_question)
//        db!!.update(TABLE_NAME, value, "$KEY = ?", arrayOf(p.id.toString()))
//    }
//
//    /**
//     * @param id l'identifiant de la reponse à récupérer
//     */
//    fun selectionner(id: Long): Reponse? {
//        // CODE
//        return null
//    }
//
//
//    fun affichage() {
//        val cursor: Cursor
//        cursor = db!!.rawQuery("SELECT " + QuestionDAO.KEY + ", " + ReponseDAO.REPONSE + ", " + ReponseDAO.ID_QUESTION + ", "
//                + ReponseDAO.ID_REPONSE_SONDAGE + " FROM " + ReponseDAO.TABLE_NAME, null)
//
//        while (cursor.moveToNext()) {
//            val id = cursor.getLong(0)
//            val reponse = cursor.getString(1)
//            val quest = cursor.getLong(2)
//            val reponse_Sondage = cursor.getLong(3)
//
//            Log.e("Affichage", "Reponse de numero $id de valeur $reponse pour la question $quest du sondage $reponse_Sondage")
//        }
//        cursor.close()
//    }
//
//    companion object {
//
//        val TABLE_NAME = "reponse"
//        val KEY = "id"
//        val REPONSE = "reponse"
//        val ID_QUESTION = "id_question"
//        val ID_REPONSE_SONDAGE = "id_reponse_sondage"
//
//        val TABLE_CREATE = ("CREATE TABLE "
//                + TABLE_NAME + " ("
//                + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + REPONSE + " TEXT, "
//                + ID_QUESTION + " INTEGER,"
//                + ID_REPONSE_SONDAGE + " INTEGER,"
//                + " FOREIGN KEY(" + ID_QUESTION + ") REFERENCES " + QuestionDAO.TABLE_NAME + "(" + QuestionDAO.KEY + "),"
//                + " FOREIGN KEY(" + ID_REPONSE_SONDAGE + ") REFERENCES " + ReponseSondageDAO.TABLE_NAME + "(" + ReponseSondageDAO.KEY + "));")
//
//        val TABLE_DROP = "DROP TABLE IF EXISTS $TABLE_NAME;"
//    }
//}
