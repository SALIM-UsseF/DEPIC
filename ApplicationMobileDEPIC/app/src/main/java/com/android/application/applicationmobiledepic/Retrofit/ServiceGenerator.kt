package com.android.application.applicationmobiledepic.Retrofit

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.io.IOException
import android.widget.Toast


class ServiceGenerator {

    // Token pour l'authentification
    private var authToken: String = ""


    /**
     * Instance du builder du client HTTP avec utilisation du token d'authentification.
     */
    private val httpClientAvecTokenAuth = OkHttpClient.Builder().addInterceptor {
            chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", authToken)
            .build()
        chain.proceed(newRequest)
    }

    /**
     * Instance du builder du client HTTP sans utilisation du token d'authentification.
     */
    private val httpClientBasique = OkHttpClient.Builder()


    private val baseURL : String = "http://192.168.43.24:3100/"

    /**
     * Builder des requêtes.
     */
    private val builder = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())


    /**
     * Lancée du builder des requêtes.
     */
    private var retrofit = builder.build()


    /**
     * Intercepteur interceptant les erreurs.
     */
    private val interceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request()
            return chain.proceed(request)
        }
    }


    fun Message(c: Context, TAG: String, t: Throwable) {
        Toast.makeText(
            c,
            if (t is IOException) "Erreur de connexion" else "Problème de conversion des données ou de configuration.",
            Toast.LENGTH_SHORT
        ).show()
        Log.e(TAG, t.message)
        Log.e(TAG, t.toString())
    }


    fun <S> createService(serviceClass: Class<S>, tokenAuthentification: String?): S {
        /*
         * Test si l'interceptor a été ajouté au client http utilisé par retrofit,
         * si il n'y est pas alors le client http n'a pas été initialisé.
         * Si il y est alors il n'y a pas besoin d'initialiser de nouveau le client http.
         */

        if(tokenAuthentification == null) {
            authToken = ""
        } else {
            authToken = tokenAuthentification
        }

        if (!authToken.equals("") && !httpClientAvecTokenAuth.interceptors().contains(interceptor)) {
            // Ajout l'intercepteur au client http.
            httpClientAvecTokenAuth.addInterceptor(interceptor)

            // Ajout le client http au builder de retrofit.
            builder.client(httpClientAvecTokenAuth.build())

            // Créer l'instance de retrofit utilisé pour les requêtes.
            retrofit = builder.build()

        } else if(authToken.equals("") && !httpClientBasique.interceptors().contains(interceptor)) {
            // Ajout l'intercepteur au client http.
            httpClientBasique.addInterceptor(interceptor)

            // Ajout le client http au builder de retrofit.
            builder.client(httpClientBasique.build())

            // Créer l'instance de retrofit utilisé pour les requêtes.
            retrofit = builder.build()
        } else {
            retrofit = builder.build()
        }
        return retrofit.create(serviceClass)
    }

}