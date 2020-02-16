package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class Categorie (
    @PrimaryKey var id_categorie: Int,
    var intitule: String,
    var active: Boolean
)