package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO

//import android.content.ContentValues
//import android.content.Context
//import android.database.Cursor
//import android.util.Log
//
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.ReponseSondage
//
//class ReponseSondageDAO(pContext: Context) : DAOBase(pContext) {
//
//    /**
//     * @param p le ReponseSondage à ajouter à la base
//     */
//    fun ajouter(p: ReponseSondage?) {
//        if (p != null) {
//            val value = ContentValues()
//            value.put(ReponseSondageDAO.IP_UTILISATEUR, p.iP_utilisateur)
//            value.put(ReponseSondageDAO.ADRESSE_MAIL_UTILISATEUR, p.adresse_Mail_Utilisateur)
//            value.put(ReponseSondageDAO.ID_SONDAGE, p.id_Sondage)
//            //            value.put(ReponseSondageDAO.ID_REPONSE, p.getId_Reponse());
//            db!!.insert(ReponseSondageDAO.TABLE_NAME, null, value)
//        } else {
//            Log.e("Ajouter ReponsSondagDAO", "La reponseSondage est nulle.")
//        }
//    }
//
//    /**
//     * @param id l'identifiant du reponseSondage à supprimer
//     */
//    fun supprimer(id: Long) {
//        db!!.delete(TABLE_NAME, "$KEY = ?", arrayOf(id.toString()))
//    }
//
//    /**
//     * @param p le reponseSondage modifié
//     */
//    fun modifier(p: ReponseSondage) {
//        val value = ContentValues()
//        value.put(ReponseSondageDAO.IP_UTILISATEUR, p.iP_utilisateur)
//        value.put(ReponseSondageDAO.ADRESSE_MAIL_UTILISATEUR, p.adresse_Mail_Utilisateur)
//        value.put(ReponseSondageDAO.ID_SONDAGE, p.id_Sondage)
//        db!!.update(TABLE_NAME, value, "$KEY = ?", arrayOf(p.id.toString()))
//    }
//
//    /**
//     * @param id l'identifiant du reponseSondage à récupérer
//     */
//    fun selectionner(id: Long): ReponseSondage? {
//        // CODE
//        return null
//    }
//
//
//    fun affichage() {
//        val cursor: Cursor
//        cursor = db!!.rawQuery("SELECT " + ReponseSondageDAO.KEY + ", " + ReponseSondageDAO.IP_UTILISATEUR + ", " + ReponseSondageDAO.ADRESSE_MAIL_UTILISATEUR + ", "
//                + ReponseSondageDAO.ID_SONDAGE + " FROM " + ReponseSondageDAO.TABLE_NAME, null)
//
//        while (cursor.moveToNext()) {
//            val id = cursor.getLong(0)
//            val IP_utilisateur = cursor.getString(1)
//            val Adresse_Mail = cursor.getString(2)
//            val sondage = cursor.getLong(3)
//
//            Log.e("Affichage", "Reponse sondage numero $id d'IP $IP_utilisateur d'adresse $Adresse_Mail du sondage $sondage")
//        }
//        cursor.close()
//    }
//
//    companion object {
//
//        val TABLE_NAME = "reponseSondage"
//        val KEY = "id"
//        val IP_UTILISATEUR = "IP_utilisateur"
//        val ADRESSE_MAIL_UTILISATEUR = "Adresse_Mail_Utilisateur"
//        val ID_SONDAGE = "id_Sondage"
//        //    public static final String ID_REPONSE = "id_Reponse";
//
//
//        //    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//        //            + IP_UTILISATEUR + " TEXT, " + ADRESSE_MAIL_UTILISATEUR + " TEXT, " + ID_SONDAGE + " INTEGER, "
//        //            + ID_REPONSE + "INTEGER, FOREIGN KEY(" + ID_SONDAGE + ") REFERENCES " + SondageDAO.TABLE_NAME + "(" + SondageDAO.KEY + "),"
//        //            + " FOREIGN KEY(" + ID_REPONSE + ") REFERENCES " + ReponseDAO.TABLE_NAME + "(" + ReponseDAO.KEY + "));";
//
//        val TABLE_CREATE = ("CREATE TABLE "
//                + TABLE_NAME + " ("
//                + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + IP_UTILISATEUR + " TEXT, "
//                + ADRESSE_MAIL_UTILISATEUR + " TEXT, "
//                + ID_SONDAGE + " INTEGER,"
//                + " FOREIGN KEY(" + ID_SONDAGE + ") REFERENCES " + SondageDAO.TABLE_NAME + "(" + SondageDAO.KEY + "));")
//
//
//        val TABLE_DROP = "DROP TABLE IF EXISTS $TABLE_NAME;"
//    }
//}
