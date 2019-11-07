package com.android.application.applicationmobiledepic

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

//import com.android.application.applicationtestkotlin.BaseDeDonneesInterne.DAO.SondageDAO
//import com.android.application.applicationtestkotlin.BaseDeDonneesInterne.DatabaseHandler
//import com.android.application.applicationtestkotlin.BaseDeDonneesInterne.Modele.Sondage

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var linearLayout: LinearLayout? = null

    private var context: Context? = null

    private var test = 0

    private val param = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,
        1.0f
    )

//    private var databaseHandler: DatabaseHandler? = null

    private var sqLiteDatabase: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

        val reponses = ArrayList<String>()

        val intitulerQuestion = "Quel est le nom de famille de Vincent ?"
        reponses.add("Barichard")
        reponses.add("Garcia")
        reponses.add("Tassadit")

        linearLayout = findViewById(R.id.layout_general)

//        databaseHandler = DatabaseHandler(context, "Base_Sondage", null, 1)

//        sqLiteDatabase = databaseHandler!!.writableDatabase


//        val sondage1 = Sondage(1, "Sondage num 1")


    }


    fun CliquageQCU(view: View) {
        Toast.makeText(this, "Attention, cliquage du QCU.", Toast.LENGTH_SHORT).show()
        val question = "Quelle est la bonne réponse ?"
        val numero = test
        val reponses = ArrayList<String>()
        reponses.add("La réponse 2$test")
        reponses.add("La réponse 1 --------------------------------------")
        reponses.add("La réponse 2 et 3")

        test++

        //        LinearLayout linearLayout = findViewById(R.id.LayoutLigne);
        val item = QuestionItem(this, question,
            numero, TypeQuestion.QUESTION_CHOIX_UNIQUE, reponses)

        // Créé le LinearLayout qui contiendra la question et les réponses.
        val linearLayoutHorizontal = Ajout_LinearLayout_Question_Reponse()

        // Créé le TextView de la question.
        Ajout_TextView_Question(linearLayoutHorizontal, question, numero)

        // Créé ce qu'i lfaut pour la réponse.
        Ajout_QCU_reponses(linearLayoutHorizontal, item.parametresQuestion!!)
    }


    fun CliquageQCM(view: View) {
        Toast.makeText(this, "Attention, cliquage du QCM.", Toast.LENGTH_SHORT).show()
        val question = "Combien y a t'il de repas par jour ?-------------------------------------------"
        val numero = 2
        val reponses = ArrayList<String>()
        reponses.add("1 ---------------------------")
        reponses.add("2")
        reponses.add("3")
        reponses.add("8 ?")

        val linearLayout = findViewById<LinearLayout>(R.id.LayoutLigne)

        val item = QuestionItem(this, question,
            numero, TypeQuestion.QUESTION_CHOIX_MULTIPLE, reponses)

        // Créé le LinearLayout qui contiendra la question et les réponses.
        val linearLayoutHorizontal = Ajout_LinearLayout_Question_Reponse()

        // Créé le TextView de la question.
        Ajout_TextView_Question(linearLayoutHorizontal, question, numero)

        // Créé ce qu'i lfaut pour la réponse.
        Ajout_QCM_reponses(linearLayoutHorizontal, item.parametresQuestion!!)
    }


    fun Ajout_LinearLayout_Question_Reponse(): LinearLayout {
        val linearLayoutHorizontal = LinearLayout(this)
        linearLayoutHorizontal.orientation = LinearLayout.HORIZONTAL
        linearLayoutHorizontal.id = View.generateViewId()
        linearLayoutHorizontal.setPadding(0, 50, 0, 50)
        linearLayout!!.addView(linearLayoutHorizontal, linearLayout!!.childCount, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        return linearLayoutHorizontal
    }


    fun Ajout_TextView_Question(linearLayoutParent: LinearLayout, question: String, numero: Int) {
        // On créé le textView qui contiendra la question.
        val textView = TextView(this)
        // On donne un ID au textView.
        textView.id = View.generateViewId()
        val questionTexte = "$numero) $question"
        textView.text = questionTexte
        textView.gravity = View.TEXT_ALIGNMENT_GRAVITY
        // On ajoute le View à la ligne du listView.
        val params = LinearLayout.LayoutParams(param)
        params.gravity = Gravity.CENTER
        linearLayoutParent.addView(textView, 0, LinearLayout.LayoutParams(params))
        // On affiche la question et son numéro dans le textView.
    }


    fun Ajout_QCU_reponses(linearLayoutParent: LinearLayout, reponsesPossibles: ArrayList<String>) {
        // On créé le radioGroup qui contiendra les différentes réponses possibles.
        val radioGroup = RadioGroup(this)
        // On donne une ID au radioGroup.
        radioGroup.id = View.generateViewId()
        // On ajoute le radioGroup à la nouvelle ligne du listView.
        val params = LinearLayout.LayoutParams(param)
        params.gravity = Gravity.CENTER_HORIZONTAL
        linearLayoutParent.addView(radioGroup, 1, LinearLayout.LayoutParams(params))

        // Pour chaque réponse possible.
        for (i in reponsesPossibles.indices) {
            // On créé le radioButton qui correspond à la réponse possible.
            val radioButton = RadioButton(context)
            // On donne une ID au radioButton.
            radioButton.id = View.generateViewId()
            radioButton.text = reponsesPossibles[i]
            //On ajoute le radioButton au radiogroup précédant.
            (linearLayoutParent.getChildAt(linearLayoutParent.childCount - 1) as RadioGroup).addView(radioButton, 0, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        }
    }

    fun Ajout_QCM_reponses(linearLayoutParent: LinearLayout, reponsesPossibles: ArrayList<String>) {
        // On créé le linearLayout qui contiendra les différentes réponses possibles.
        val linearLayout = LinearLayout(this)
        // On donne une ID au radioGroup.
        linearLayout.id = View.generateViewId()
        // On ajoute le radioGroup à la nouvelle ligne du listView.
        linearLayout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        )
        params.gravity = Gravity.CENTER
        linearLayoutParent.addView(linearLayout, 1, LinearLayout.LayoutParams(params))

        // Pour chaque réponse possible.
        for (i in reponsesPossibles.indices) {
            // On créé le radioButton qui correspond à la réponse possible.
            val checkBox = CheckBox(context)
            // On donne une ID au radioButton.
            checkBox.id = View.generateViewId()
            checkBox.text = reponsesPossibles[i]
            //On ajoute le radioButton au radiogroup précédant.
            linearLayout.addView(checkBox, 0, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        }
    }


    internal class QuestionItem(context: Context, var intitulerQuestion: String?, var numeroQuestion: Int, var typeQuestion: TypeQuestion?, parametresQuestion: ArrayList<String>) {
        var parametresQuestion: ArrayList<String>? = null

        init {
            this.parametresQuestion = ArrayList()
            this.parametresQuestion!!.addAll(parametresQuestion)
        }


        override fun toString(): String {
            var string = ("intituler de a question : " + this.intitulerQuestion
                    + "\nNuméro de la question : " + this.numeroQuestion
                    + "\nType de la question : " + this.typeQuestion
                    + "\nParamètres de la question : ")

            val stringBuilder = StringBuilder(string)

            for (i in parametresQuestion!!.indices) {
                val stringConcatenation = parametresQuestion!![i] + "\n       "
                stringBuilder.append(stringConcatenation)
            }

            string = stringBuilder.toString()

            return string
        }
    }

}