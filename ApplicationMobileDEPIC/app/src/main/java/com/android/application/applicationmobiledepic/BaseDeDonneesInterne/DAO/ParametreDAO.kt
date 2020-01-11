package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Parametre

//class ParametreDAO(pContext: Context) : DAOBase(pContext) {
//
//    /**
//     * @param p le parametre à ajouter à la base
//     */
//    fun ajouter(p: Parametre?) {
//        if (p != null) {
//            val value = ContentValues()
//            value.put(ParametreDAO.ATTRIBUT, p.attribut)
//            value.put(ParametreDAO.VALEUR, p.valeur)
//            value.put(ParametreDAO.ID_QUESTION, p.id_Question)
//            db!!.insert(ParametreDAO.TABLE_NAME, null, value)
//        } else {
//            Log.e("Ajouter ParametreDAO", "Le parametre est nulle.")
//        }
//    }
//
//    /**
//     * @param id l'identifiant du parametre à supprimer
//     */
//    fun supprimer(id: Long) {
//        db!!.delete(TABLE_NAME, "$KEY = ?", arrayOf(id.toString()))
//    }
//
//    /**
//     * @param p le parametre modifié
//     */
//    fun modifier(p: Parametre) {
//        val value = ContentValues()
//        value.put(ParametreDAO.ATTRIBUT, p.attribut)
//        value.put(ParametreDAO.VALEUR, p.valeur)
//        value.put(ParametreDAO.ID_QUESTION, p.id_Question)
//        db!!.update(TABLE_NAME, value, "$KEY = ?", arrayOf(p.id.toString()))
//    }
//
//    /**
//     * @param id l'identifiant du parametre à récupérer
//     */
//    fun selectionner(id: Long): Parametre? {
//        // CODE
//        return null
//    }
//
//    fun affichage() {
//        val cursor: Cursor
//        cursor = db!!.rawQuery("SELECT " + ParametreDAO.KEY + ", " + ParametreDAO.ATTRIBUT + ", " + ParametreDAO.VALEUR + ", "
//                + ParametreDAO.ID_QUESTION + " FROM " + ParametreDAO.TABLE_NAME, null)
//
//        while (cursor.moveToNext()) {
//            val id = cursor.getLong(0)
//            val attribut = cursor.getString(1)
//            val valeur = cursor.getString(2)
//            val quest = cursor.getLong(3)
//
//            Log.e("Affichage", "Paramètre de numéro $id d'attribut $attribut de valeur $valeur pour la question $quest")
//        }
//        cursor.close()
//    }
//
//    companion object {
//        val TABLE_NAME = "parametre"
//        val KEY = "id"
//        val ATTRIBUT = "attribut"
//        val VALEUR = "valeur"
//        val ID_QUESTION = "id_Question"
//
//        val TABLE_CREATE = ("CREATE TABLE "
//                + TABLE_NAME + " ("
//                + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + ATTRIBUT + " TEXT, "
//                + VALEUR + " TEXT, "
//                + ID_QUESTION + " long, "
//                + " FOREIGN KEY(" + ID_QUESTION + ") REFERENCES " + QuestionDAO.TABLE_NAME + "(" + QuestionDAO.KEY + "));")
//
//        val TABLE_DROP = "DROP TABLE IF EXISTS $TABLE_NAME;"
//    }
//}
