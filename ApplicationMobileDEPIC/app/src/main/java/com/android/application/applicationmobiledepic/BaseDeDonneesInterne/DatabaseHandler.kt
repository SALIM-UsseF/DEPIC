package com.android.application.applicationmobiledepic.BaseDeDonneesInterne

//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import android.util.Log
//
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.ParametreDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.QuestionDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.ReponseDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.ReponseSondageDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.SondageDAO
//
//class DatabaseHandler
////    public static final String METIER_KEY = "id";
////    public static final String METIER_INTITULE = "intitule";
////    public static final String METIER_SALAIRE = "salaire";
//
////    public static final String METIER_TABLE_NAME = "Metier";
////    public static final String METIER_TABLE_CREATE =
////            "CREATE TABLE " + METIER_TABLE_NAME + " (" +
////                    METIER_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
////                    METIER_INTITULE + " TEXT, " +
////                    METIER_SALAIRE + " REAL);";
//
////    public static final String METIER_TABLE_DROP = "DROP TABLE IF EXISTS " + METIER_TABLE_NAME + ";";
//
//
//(context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
//
//
//    /*
//     *  On Créé les tables de la base de données.
//     */
//    override fun onCreate(db: SQLiteDatabase) {
//        Log.e("DataHandler oncreate", "On passe dedans")
//        db.execSQL(ParametreDAO.TABLE_CREATE)
//
//        db.execSQL(QuestionDAO.TABLE_CREATE)
//
//        db.execSQL(ReponseDAO.TABLE_CREATE)
//        db.execSQL(ReponseSondageDAO.TABLE_CREATE)
//        db.execSQL(SondageDAO.TABLE_CREATE)
//    }
//
//
//    /*
//     *  On supprime les bases de la base de données puis on les recréés pour pouvoir les mettre à jour.
//     */
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL(ParametreDAO.TABLE_DROP)
//        db.execSQL(QuestionDAO.TABLE_DROP)
//        db.execSQL(ReponseDAO.TABLE_DROP)
//        db.execSQL(ReponseSondageDAO.TABLE_DROP)
//        db.execSQL(SondageDAO.TABLE_DROP)
//        onCreate(db)
//    }
//
//    fun onDestroy(db : SQLiteDatabase){
//        db.execSQL(ParametreDAO.TABLE_DROP)
//        db.execSQL(QuestionDAO.TABLE_DROP)
//        db.execSQL(ReponseDAO.TABLE_DROP)
//        db.execSQL(ReponseSondageDAO.TABLE_DROP)
//        db.execSQL(SondageDAO.TABLE_DROP)
//    }
//}

