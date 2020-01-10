package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO

//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DatabaseHandler
//
//abstract class DAOBase(pContext: Context) {
//
//    var db: SQLiteDatabase? = null
//        protected set
//    protected var mHandler: DatabaseHandler? = null
//
//    init {
//        this.mHandler = DatabaseHandler(pContext, NOM, null, VERSION)
//    }
//
//    fun open(): SQLiteDatabase? {
//        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
//        db = mHandler!!.writableDatabase
//        return db
//    }
//
//    fun close() {
//        db!!.close()
//    }
//
//    companion object {
//        // Nous sommes à la première version de la base
//        // Si je décide de la mettre à jour, il faudra changer cet attribut
//        protected val VERSION = 1
//        // Le nom du fichier qui représente ma base
//        protected val NOM = "database.db"
//    }
//}
