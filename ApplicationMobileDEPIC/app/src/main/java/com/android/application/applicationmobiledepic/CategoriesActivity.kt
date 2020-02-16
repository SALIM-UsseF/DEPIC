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

    private var listeCategories : ArrayList<Categorie> = ArrayList()

    private var linearLayoutOptionsCategories : LinearLayout? = null

    private var context : Context? = null

//    private var listeLinearLayoutCategories : ArrayList<LinearLayout> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.android.application.applicationmobiledepic.R.layout.activity_categories)
        context = this
        // On récupère l'intent
        val intentTemp = intent
        // On récupère les valeurs des catégories.
        val listeIdTemp = intentTemp.getIntegerArrayListExtra("ListeIdCategorie")
        val listeIntituleTemp = intentTemp.getStringArrayListExtra("ListeIntituleCategorie")
        val listeActiveTemp = intentTemp.getIntegerArrayListExtra("ListeActiveCategorie")
        if(listeIdTemp.size != 0) {
            for (i in 0..listeIdTemp.size-1) {
                var categorieTemporaire: Categorie
                if (listeActiveTemp[i] == 1) {
                    categorieTemporaire = Categorie(listeIdTemp[i], listeIntituleTemp[i], true)
                } else {
                    categorieTemporaire = Categorie(listeIdTemp[i], listeIntituleTemp[i], false)
                }
                listeCategories.add(categorieTemporaire)
            }
        }
        // On chercher le layout pour placer les options des catégories.
        linearLayoutOptionsCategories = findViewById(com.android.application.applicationmobiledepic.R.id.Layout_Options_Categories)
        for(categorie in listeCategories){
            AjoutLignePourCategorie(categorie)
        }
    }

    fun AjoutLignePourCategorie(categorie: Categorie){
        if(linearLayoutOptionsCategories != null){
            val linearLayoutTemporaire : LinearLayout = LayoutInflater.from(context).inflate(com.android.application.applicationmobiledepic.R.layout.layout_ligne_categorie, null) as LinearLayout
            val checkBox : CheckBox = linearLayoutTemporaire.getChildAt(0) as CheckBox
            if(categorie.active){
                checkBox.isChecked = true
            }
            val textView : TextView = linearLayoutTemporaire.getChildAt(1) as TextView
            textView.setText(categorie.intitule)
            linearLayoutOptionsCategories!!.addView(linearLayoutTemporaire)
        }
    }

    fun ValidationChangementOptionsCategories(view: View) {
        if(linearLayoutOptionsCategories != null) {
            for (i in 0..linearLayoutOptionsCategories!!.childCount - 1) {
                val layoutOptionCategorie = linearLayoutOptionsCategories!!.getChildAt(i) as LinearLayout
                if(layoutOptionCategorie.childCount != 0) {
                    val checkBox: CheckBox = layoutOptionCategorie.getChildAt(0) as CheckBox
                    listeCategories[i].active = checkBox.isChecked
                }
            }

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