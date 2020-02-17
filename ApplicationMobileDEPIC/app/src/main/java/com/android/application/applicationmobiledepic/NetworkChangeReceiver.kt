package com.android.application.applicationmobiledepic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast




class NetworkChangeReceiver: BroadcastReceiver() {

    /**
     * Fonction étant appelée lors du changement détat de la connexion à un réseau (connexion ou déconnexion)
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            //Il y a une connexion à un réseau
            Log.e("Connexion","il y a une connexion à un réseau : " + networkInfo.subtypeName + "  " + intent!!.action)
            Toast.makeText(context, "Il y a une connexion à un réseau.", Toast.LENGTH_SHORT).show()
        } else {
            // il n'y a pas de connexion à un réseau
            Log.e("Connexion","Il n'y a pas de connexion à un réseau" + intent!!.action)
            Toast.makeText(context, "Il n'y a pas de connexion à un réseau.", Toast.LENGTH_SHORT).show()
        }
    }
}
