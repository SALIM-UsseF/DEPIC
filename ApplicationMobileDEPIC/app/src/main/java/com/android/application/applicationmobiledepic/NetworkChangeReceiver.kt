package com.android.application.applicationmobiledepic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.IntentFilter
import androidx.core.content.ContextCompat.getSystemService








//        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


//        connectivityManager.registerDefaultNetworkCallback(object  : ConnectivityManager.NetworkCallback() {
//            override fun onAvailable(network: Network?) {
//                super.onAvailable(network)
//                Log.i("Test", "Default -> Network Available")
//            }
//
//            override fun onLost(network: Network?) {
//                super.onLost(network)
//                Log.i("Test", "Default -> Connection lost")
//            }
//        })
//
//
//var filters = IntentFilter()
////    filters.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
////    filters.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
////        filters.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
////        filters.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
////        filters.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
//registerReceiver(networkChangeReceiver, filters)
//
//
//
//if(isInternetConnected()){
//    // On gère les trucs avec connexion ici.
//    Toast.makeText(this, "Il y a une connexion", Toast.LENGTH_LONG).show()
//} else {
//    // On gère les trucs sans connexion ici.
//    Toast.makeText(this, "Il n'y a pas une connexion", Toast.LENGTH_LONG).show()
//}
//
//}
//




class NetworkChangeReceiver: BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val networks = connectivityManager.allNetworks
        val networkInfo = connectivityManager.activeNetworkInfo

//            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
//            val wifi = wifiManager.connectionInfo
//
//                Log.e("wififi","wifi : " + wifi.toString())



//            for(n in networks){
//                Log.e("iuhjiuj", n.toString() + "  ")
//            }

//     NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//     return mWifi.isConnected();

//            for(n in networks){
//                Log.e("efe",n.toString() + "  " + intent!!.action)
//            }
//            Log.e("yguyg", "uhiojjjuhiu" + "  " + intent!!.action)

        if (networkInfo != null && networkInfo.isConnected) {
//                //Il y a une connexion
            Log.e("joijojioj", "oijoijojoijoijioj     connexion")
//                Log.e("Connection","il y en a" + networkInfo.subtypeName + "  " + intent!!.action)
                Toast.makeText(context, "Il y a de la connexion.", Toast.LENGTH_SHORT).show()
        } else {
//                // il n'y a pas de connexion
                Log.e("Connection","il y en a pas" + intent!!.action)
                Toast.makeText(context, "Il n'y a pas de connexion.", Toast.LENGTH_SHORT).show()
        }
    }
}

/*
fun isInternetConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}*/