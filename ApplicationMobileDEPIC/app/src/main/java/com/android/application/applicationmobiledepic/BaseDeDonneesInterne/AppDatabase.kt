package com.android.application.applicationmobiledepic.BaseDeDonneesInterne

//import android.arch.persistence.room.Database
//import android.arch.persistence.room.RoomDatabase
import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.MyDAO
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.*

@Database(entities = arrayOf(Sondage::class, Question::class, Reponse::class, Choix::class, Utilisateur::class, TokenAuthentification::class, Categorie::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun myDAO(): MyDAO
}
