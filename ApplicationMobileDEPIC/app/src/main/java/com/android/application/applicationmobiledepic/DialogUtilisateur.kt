package com.android.application.applicationmobiledepic

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

//class DialogUtilisateur: AlertDialog(){
//
//
//    override fun onCreate() {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//    return activity?.let {
//        // Use the Builder class for convenient dialog construction
//        val builder = AlertDialog.Builder(it)
//        builder.setMessage(R.string.Message_Utilisateur)
//            .setPositiveButton(R.string.Button_Accepter,
//                DialogInterface.OnClickListener { dialog, id ->
//                    // FIRE ZE MISSILES!
//                })
//            .setNegativeButton(R.string.Button_Refuser,
//                DialogInterface.OnClickListener { dialog, id ->
//                    // User cancelled the dialog
//                })
//        // Create the AlertDialog object and return it
//        builder.create()
//    } ?: throw IllegalStateException("Activity cannot be null")
//}
//}
