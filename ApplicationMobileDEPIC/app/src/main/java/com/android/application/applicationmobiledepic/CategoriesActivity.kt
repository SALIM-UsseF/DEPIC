package com.android.application.applicationmobiledepic

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.widget.CheckBox
import android.widget.CheckedTextView
import android.widget.LinearLayout
import android.widget.TextView
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Categorie
import android.R
import android.app.Activity
import android.view.LayoutInflater




class CategoriesActivity : AppCompatActivity() {

    // La liste des catégories
    private var listeCategories : ArrayList<Categorie> = ArrayList()
    // Le layout où ajouter les views pour l'ajout / le retrait d'abonnement à une catégorie
    private var linearLayoutOptionsCategories : LinearLayout? = null
    // le context
    private var context : Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.android.application.applicationmobiledepic.R.layout.activity_categories)
        // on récupère le context
        context = this
        // On récupère l'intent
        val intentTemp = intent
        // On récupère les valeurs des catégories dans l'intent.
        val listeIdTemp = intentTemp.getIntegerArrayListExtra("ListeIdCategorie")
        val listeIntituleTemp = intentTemp.getStringArrayListExtra("ListeIntituleCategorie")
        val listeActiveTemp = intentTemp.getIntegerArrayListExtra("ListeActiveCategorie")
        if(listeIdTemp.size != 0) {
            for (i in 0..listeIdTemp.size-1) {
                // Si il y a des catégories, on les récupère toutes
                var categorieTemporaire: Categorie
                if (listeActiveTemp[i] == 1) {
                    categorieTemporaire = Categorie(listeIdTemp[i], listeIntituleTemp[i], true)
                } else {
                    categorieTemporaire = Categorie(listeIdTemp[i], listeIntituleTemp[i], false)
                }
                listeCategories.add(categorieTemporaire)
            }
        }
        // On chercher le layout pour placer les options d'abonnement des catégories.
        linearLayoutOptionsCategories = findViewById(com.android.application.applicationmobiledepic.R.id.Layout_Options_Categories)
        // On ajoute les views pour les options d'abonnement
        for(categorie in listeCategories){
            AjoutLignePourCategorie(categorie)
        }
    }

    // On ajoute un layout constitué d'un checkbox et d'un textview pour la catégorie passé en paramètre
    fun AjoutLignePourCategorie(categorie: Categorie){
        // Si le layout où insérer les views existe
        if(linearLayoutOptionsCategories != null){
            // On inflate le layout qui contient le checkbox et le textview
            val linearLayoutTemporaire : LinearLayout = LayoutInflater.from(context).inflate(com.android.application.applicationmobiledepic.R.layout.layout_ligne_categorie, null) as LinearLayout
            val checkBox : CheckBox = linearLayoutTemporaire.getChildAt(0) as CheckBox
            // Si la catégorie est déjà activé, on coche le checkbox
            if(categorie.active){
                checkBox.isChecked = true
            }
            val textView : TextView = linearLayoutTemporaire.getChildAt(1) as TextView
            textView.setText(categorie.intitule)
            linearLayoutOptionsCategories!!.addView(linearLayoutTemporaire)
        }
    }

    // On sauvegarde les changements et les envois à l'activité principale
    fun ValidationChangementOptionsCategories(view: View) {
        if(linearLayoutOptionsCategories != null) {
            for (i in 0..linearLayoutOptionsCategories!!.childCount - 1) {
                // Le layout contenant le checkbox et le textview
                val layoutOptionCategorie = linearLayoutOptionsCategories!!.getChildAt(i) as LinearLayout
                // la première vue est le checkbox qui représente l'état d'activation d'une catégorie
                if(layoutOptionCategorie.childCount != 0) {
                    val checkBox: CheckBox = layoutOptionCategorie.getChildAt(0) as CheckBox
                    listeCategories[i].active = checkBox.isChecked
                }
            }
            // On créé un intent pour renvoyer les nouvelles données
            var intent = Intent(context, CategoriesActivity::class.java)
            val listeIdCategorie = ArrayList<Int>()
            val listeIntituleCategorie = ArrayList<String>()
            val listeActiveCategorie = ArrayList<Int>()
            for (categorie in listeCategories) {
                listeIdCategorie.add(categorie.id_categorie)
                listeIntituleCategorie.add(categorie.intitule)
                if (categorie.active) {
                    listeActiveCategorie.add(1)
                } else {
                    listeActiveCategorie.add(0)
                }
            }
            intent.putIntegerArrayListExtra("ListeIdCategorie", listeIdCategorie)
            intent.putStringArrayListExtra("ListeIntituleCategorie", listeIntituleCategorie)
            intent.putIntegerArrayListExtra("ListeActiveCategorie", listeActiveCategorie)

            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

    }



}