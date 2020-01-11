package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory


class ModeleSondagePossible (val id_sondage: Int,
                             val intituleSondage: String,
                             val descriptionSondage: String,
                             val administrateur_id: Int)