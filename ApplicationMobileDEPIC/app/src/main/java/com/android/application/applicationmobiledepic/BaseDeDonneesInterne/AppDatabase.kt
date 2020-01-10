package com.android.application.applicationmobiledepic.BaseDeDonneesInterne

//import android.arch.persistence.room.Database
//import android.arch.persistence.room.RoomDatabase
import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.MyDAO
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Choix
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Question
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Reponse
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Sondage

@Database(entities = arrayOf(Sondage::class, Question::class, Reponse::class, Choix::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun myDAO(): MyDAO
}
