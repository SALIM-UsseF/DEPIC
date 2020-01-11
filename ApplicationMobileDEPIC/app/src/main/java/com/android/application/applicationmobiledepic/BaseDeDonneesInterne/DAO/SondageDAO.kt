package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO

//import android.content.ContentValues
//import android.content.Context
//import android.database.Cursor
//import android.util.Log
//
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Sondage
//
//class SondageDAO(pContext: Context) : DAOBase(pContext) {
//
//    /**
//     * @param p le sondage à ajouter à la base
//     */
//    fun ajouter(p: Sondage?) {
//        if (p != null) {
//            val value = ContentValues()
//            value.put(SondageDAO.NOM, p.nom)
//            //            value.put(SondageDAO.ID_QUESTION, p.getId_Question());
//            db!!.insert(SondageDAO.TABLE_NAME, null, value)
//        } else {
//            Log.e("Ajouter ReponsSondagDAO", "La reponseSondage est nulle.")
//        }
//    }
//
//    /**
//     * @param id l'identifiant du sondage à supprimer
//     */
//    fun supprimer(id: Long) {
//        db!!.delete(TABLE_NAME, "$KEY = ?", arrayOf(id.toString()))
//    }
//
//    /**
//     * @param p le sondage modifié
//     */
//    fun modifier(p: Sondage) {
//        val value = ContentValues()
//        value.put(SondageDAO.NOM, p.nom)
//        db!!.update(TABLE_NAME, value, "$KEY = ?", arrayOf(p.id.toString()))
//    }
//
//    /**
//     * @param id l'identifiant du sondage à récupérer
//     */
//    fun selectionner(id: Long): Sondage {
//        var sondage = Sondage(0, "")
//        val id_String = "" + id
//        val cursor: Cursor
//        cursor = db!!.rawQuery("SELECT " + SondageDAO.KEY + ", " + SondageDAO.NOM
//                + " FROM " + SondageDAO.TABLE_NAME + " WHERE " + SondageDAO.KEY + " = ?", arrayOf(id_String))
//
//        while (cursor.moveToNext()) {
//            val new_id = cursor.getLong(0)
//            val nom = cursor.getString(1)
//            sondage = Sondage(new_id, nom)
//            Log.e("Affichage selection", "Sondage numéro $new_id de nom $nom")
//        }
//        cursor.close()
//        return sondage
//    }
//
//
//    fun affichage() {
//        val cursor: Cursor
//        cursor = db!!.rawQuery("SELECT " + SondageDAO.KEY + ", " + SondageDAO.NOM
//                + " FROM " + SondageDAO.TABLE_NAME, null)
//
//        while (cursor.moveToNext()) {
//            val id = cursor.getLong(0)
//            val nom = cursor.getString(1)
//
//            Log.e("Affichage", "Sondage numéro $id de nom $nom")
//        }
//        cursor.close()
//    }
//
//    companion object {
//
//        val TABLE_NAME = "sondage"
//        val KEY = "id"
//        val NOM = "nom"
//        //    public static final String ID_QUESTION = "id_Question";
//
//        //    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//        //            + NOM + " TEXT, " + ID_QUESTION + " INTEGER,   FOREIGN KEY(" + ID_QUESTION + ") REFERENCES " + QuestionDAO.TABLE_NAME + "(" + QuestionDAO.KEY + "));";
//
//        val TABLE_CREATE = ("CREATE TABLE " +
//                TABLE_NAME + " (" +
//                KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + NOM + " TEXT);")
//
//
//        val TABLE_DROP = "DROP TABLE IF EXISTS $TABLE_NAME;"
//    }
//}
