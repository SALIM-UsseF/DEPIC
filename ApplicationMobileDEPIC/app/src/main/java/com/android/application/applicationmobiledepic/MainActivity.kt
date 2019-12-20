package com.android.application.applicationmobiledepic

import android.app.ProgressDialog.show
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.ConnectivityManager
import android.net.Network
import android.nfc.Tag
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
//import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.room.CoroutinesRoom
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.AppDatabase

//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.ParametreDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.QuestionDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.ReponseDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.ReponseSondageDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.SondageDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DatabaseHandler
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Parametre
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Question
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Reponse
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Sondage
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Retrofit.ReponseRequete
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Retrofit.ServerApiService
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Retrofit.ServiceGenerator
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.layout_conteneur_base.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope, AdapterView.OnItemSelectedListener {

    //Le coroutinecontext pour pouvoir utilise rles coroutines
    override val coroutineContext = Dispatchers.Main + SupervisorJob()
    //    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    //Le linearlayout pour mettre les views questions
    private var linearLayout: LinearLayout? = null

    //Le contexte du mainActivity
    private var context: Context? = null

    //Un int de test
    private var test = 0

    //Les params de constructeur de layouit utiles
    private val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
    )

    //La bdd utile
    private lateinit var db : AppDatabase

    //L'hanlder de bdd pas utile
//    private var databaseHandler: DatabaseHandler? = null


    //La bdd pas utile
    private var sqLiteDatabase: SQLiteDatabase? = null


    //Le cursor, pas utile
    private val cursor: Cursor? = null


    //Le spinner
    private var spinner: Spinner? = null

    //L'arraylist des view des questions
    private val listLayoutQuestion = ArrayList<View>()

    //Le sondage actuellement en cours d'utilisation
    private var sondage : Sondage? = null

    //L'arraylist de questions actuellement affichés
    private var questions : Array<Question>? = null

    //Le nom du sondage actuellement utilise
    private var nomSondage = "Choissisez un sondage"

    //L'arraylist contenant les noms des sondages disponibles à l'utilisateur
    private var nomSondagesDisponibles = ArrayList<String>()

    //L'arraylist conenant les sondages disponibles à l'utilisateur
    private var sondagesDisponibles = ArrayList<Sondage>()

    private lateinit var spinnerAdapter : ArrayAdapter<String>

    private var listeTAGReponse = ArrayList<String>()


    //String keys for bundle
    private val TAG_LISTE_REPONSES = "listeReponses"
    private val TAG_ID_SONDAGE = "sondageId"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //On sauvegarde le context
        context = this

        //On sauvegarde le linearLayout où les questions doivent être placés
        linearLayout = findViewById(R.id.layout_general)

        //Build de la base de données interne

        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).build()

        //Ajout de l'option annulatrice pour le choix du sondage
        nomSondagesDisponibles.add("Choissisez un sondage")

        //Recherche du spinner pour choix de sondages
        spinner = findViewById<View>(R.id.spinner) as Spinner
//         Create an ArrayAdapter using the string array and a default spinner layout
        this.spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nomSondagesDisponibles)


//                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                        R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner!!.adapter = spinnerAdapter

        initialisationListeSondagesDisponibles(savedInstanceState)

        spinner!!.onItemSelectedListener = this

        if(isInternetConnected()){
            Toast.makeText(this, "Il y a une connexion", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Il n'y a pas une connexion", Toast.LENGTH_LONG).show()
        }
    }


    fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    //Ajoute au spinner tous les noms de sondages disponibles dans la base de données.
    fun initialisationListeSondagesDisponibles(savedInstanceState: Bundle?){
        launch{
//            var listeNomsSondagesDisponibles = db.myDAO().loadAllNomsSondages()
            var listeSondagesDisponibles = db.myDAO().loadAllSondages()
            listeSondagesDisponibles.forEach {
                nomSondagesDisponibles.add(it.sondageNom)
                sondagesDisponibles.add(it)
            }
            if(savedInstanceState != null) {
                // On récupère l'id du dernier sondage utilisé et non envoyé
                val sondageId = savedInstanceState.getInt(TAG_ID_SONDAGE)
                // sondageId == -1 s'il n'y avait pas de sondage précédemment utilisé
                if (sondageId != -1) {
                    linearLayout!!.getChildAt(0).visibility = View.VISIBLE
                    Log.e("uhdiudzdhi", "odjsdoicjdscojsd    " + sondageId)
                    gettingSondageFromBDDWithSavedInstanceState(sondageId, savedInstanceState)
                }
            }
            getReponses()
        }
    }


    fun lancement(requete: String){
        val coroutineScope = CoroutineScope(Dispatchers.Default)


        coroutineScope.launch {
            when(requete){
                "testDestroy" -> testDestroy()
                "getAllSondages" -> getAllSondages()
                "getAllQuestions" -> getAllQuestions()
                "putSondages" -> putSondages()
                "putQuestions" -> putQuestions()
//                "getSondageFromSondageId" -> getSondageFromSondageID()
//                "getQuestionFromSondageId" -> getQuestionFromSondageID()
                "getQuestionFromQuestionId" -> getQuestionFromQuestionID()
                "deleteAllSondages" -> deleteAllSondages()
                "deleteAllQuestions" -> deleteAllQuestions()
            }

        }
    }





    suspend fun getSondageFromSondageID(sondageId: Int): Sondage?{
        val sondages = db.myDAO().loadOneSondageFromSondageId(sondageId)
        for (sond in sondages) {
            Log.e(
                "testestSFS",
                "Attention normalement que un resultat   :   " + sond.sondageId + "  " + sond.sondageIdWeb + "  " + sond.sondageNom
            )
            if(sond.sondageId == sondageId){
                return sond
            }
        }
        return null
    }


    fun gettingSondageFromBDDWithSavedInstanceState(sondageId: Int, savedInstanceState: Bundle?){
        launch {
            sondage = getSondageFromSondageID(sondageId)
            if(sondage != null){
                Log.e("test sondage", "test " + sondage!!.sondageId + "  " + sondage!!.sondageIdWeb + "  " + sondage!!.sondageNom)
                Log.e("gettingSondageFromBDD", "On a reçu le sondage de la BDD : " + sondage!!.sondageId)
                questions = getQuestionFromSondageID(sondage!!.sondageId)
                if(questions != null){
                    Log.e("gettingSondageFromBDD", "On a reçu les questions du sondage : " + questions!!.size)
                    affichageQuestions()

                } else {
                    Log.e("test questions", "NULL NULL NULL NULL NULL")
                }
            } else {
                Log.e("test sondage", "NULL NULL NULL   : " + sondageId)
            }
            if(savedInstanceState != null) {
                val listeReponses = savedInstanceState.getStringArrayList(TAG_LISTE_REPONSES)
                if (listeReponses != null) {
                    for (i in 0 until test) {
                        val view = linearLayout!!.findViewWithTag<View>("Reponse" + i)
                        when (view) {
                            is CheckBox -> view.isChecked = (listeReponses.get(i).equals("True"))
                            is RadioButton -> view.isChecked = (listeReponses.get(i).equals("True"))
                            is EditText -> view.setText(listeReponses.get(i))
                            else -> Log.e(
                                "initListeSondages",
                                "Une des views n'est pas d'une classe acceptable."
                            )
                        }
                    }
                }
            }
        }
    }



    fun gettingSondageFromBDD(sondageId: Int){
        launch {
            sondage = getSondageFromSondageID(sondageId)
            if(sondage != null){
                 questions = getQuestionFromSondageID(sondage!!.sondageId)
                if(questions != null){
                    affichageQuestions()
                } else {
                    Log.e("test questions", "NULL NULL NULL NULL NULL")
                }
            } else {
                Log.e("test sondage", "NULL NULL NULL   : " + sondageId)
            }
        }
    }




    suspend fun affichageQuestions(){
        if(questions != null) {
            Log.e("affichageQuestions", "Il y a des question : " + questions!!.size)
            for (question in questions!!) {
                var questionItem : QuestionItem?
                questionItem = convertisseurQuestion(question)
                if(questionItem != null){
                    Log.e("affichageQuestion", "On essaye d'afficher une question")
                    when(questionItem.typeQuestion){
                        TypeQuestion.QUESTION_REPONSE_LIBRE -> LancementQuestionTexteLibre(questionItem)
                        TypeQuestion.QUESTION_A_POINTS -> LancementQuestionAPoints(questionItem)
                        TypeQuestion.QUESTION_CHOIX_UNIQUE -> LancementQuestionChoixUnique(questionItem)
                        TypeQuestion.QUESTION_CHOIX_MULTIPLE -> LancementQuestionChoixMultiple(questionItem)
                        else -> Log.e("affichageQuestions", "Le type du questionItem n'est pas répertorié.")
                    }
                } else {
                    Log.e("affichageQuestions", "Attention, un questionItem null est reçu de ConvertisseurQuestion")
                }

            }
        } else {
            Log.e("affichageQuestions", "il n'y a pas de question, c'est null")
        }
    }



    private fun convertisseurQuestion(question: Question): QuestionItem?{
        var listParametres = question.questionIntitule.split(";")
        var intitule = listParametres.get(0)
        var arrayListParametres = ArrayList<String>()
        for(i in 1 until (listParametres.size-1)){
            arrayListParametres.add(listParametres.get(i))
        }
        var typeQuestion = getTypeQuestion(question.questionType)
        if(typeQuestion != null){
            var questionItem = QuestionItem(this, intitule, question.questionNumero, typeQuestion, arrayListParametres)
            return questionItem
        } else {
            Log.e("convertisseurQuestion", "Attention, une tentative de convertissement de typeQuestion à renvoyé null.")
        }
        return null
    }



    suspend fun getReponses(){
        val reponses = db.myDAO().loadAllReponses()
        for (reponse in reponses) {
            Log.e(
                "getReponses",
                "Réponse  :   " + reponse.reponseId + "  " + reponse.sondageId + "  " + reponse.sondageIdWeb + "  " + reponse.listeReponses
            )
        }
    }





    suspend fun getQuestionFromSondageID(sondageId: Int): Array<Question>{
        val questions = db.myDAO().loadOneQuestionFromSondageID(sondageId)
        for (quest in questions) {
            Log.e(
                "testestQFS",
                "Attention normalement que un resultat 1  :   " + quest.questionId + "  " + quest.questionIdWeb + "  " + quest.sondageId + "  " + quest.sondageIdWeb + "  " + quest.questionIntitule + "  " + quest.questionNumero + "  " + quest.questionType
            )
        }
        return questions
    }


    fun AffichageSondageFromBDD(sondage: Sondage){
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        lateinit var questions : Array<Question>
        coroutineScope.launch {
            questions = getQuestionFromSondageID(sondage.sondageId)
        }
        Log.e("test get questions", "" + questions.size);
    }





    fun getTypeQuestion(typeQuestion : String): TypeQuestion?{
        when(typeQuestion){
            "Question à points" -> return TypeQuestion.QUESTION_A_POINTS
            "Question à choix multiple" -> return TypeQuestion.QUESTION_CHOIX_MULTIPLE
            "Question à choix unique" -> return TypeQuestion.QUESTION_CHOIX_UNIQUE
            "Question à réponse libre" -> return TypeQuestion.QUESTION_REPONSE_LIBRE
            else -> return null
        }
    }


    suspend fun testDestroy(){
//        deleteAllQuestions()
//        deleteAllSondages()
//        putQuestions()
//        putSondages()
        getAllSondages()
        getAllQuestions()
//        AffichageSondageFromBDD()
//        gettingSondageFromBDD(7)
    }

    suspend fun putSondages(){
        val sondage = Sondage(sondageIdWeb = 2, sondageNom = "sondage num 2")
        db.myDAO().insertSondages(sondage)
    }

    suspend fun putQuestions(){
        val question1 = Question(questionIdWeb = 2, sondageId = 9, sondageIdWeb = 9, questionNumero = 1, questionType = "Question à choix multiple", questionIntitule =  "intitule de la pifitude;test reponse 1;test reponse 2; test reponse 3")
        val question2 = Question(questionIdWeb = 3, sondageId = 9, sondageIdWeb = 9, questionNumero = 2, questionType = "Question à choix unique", questionIntitule =  "intitule de la pifitude version 2;test reponse 11; test reponse 222;test reponse 333")
        val question3 = Question(questionIdWeb = 4, sondageId = 9, sondageIdWeb = 9, questionNumero = 3, questionType = "Question à points", questionIntitule =  "intitule de la pifitude version 2;test reponse 11; test reponse 222;test reponse 333")
        val question4 = Question(questionIdWeb = 5, sondageId = 9, sondageIdWeb = 9, questionNumero = 4, questionType = "Question à réponse libre", questionIntitule =  "intitule de la pifitude version 2;test reponse 11; test reponse 222;test reponse 333")

        db.myDAO().insertQuestions(question1, question2, question3, question4)
    }

    suspend fun getAllSondages(){
        val sondages = db.myDAO().loadAllSondages()
        for (sond in sondages) {
            Log.e(
                "testest",
                "" + sond.sondageId + "  " + sond.sondageIdWeb + "  " + sond.sondageNom
            )
        }
    }

    suspend fun getAllQuestions(){
        val questions = db.myDAO().loadAllQuestions()
        for (quest in questions) {
            Log.e(
                "testest",
                "" + quest.questionId + "  " + quest.questionIdWeb + "  " + quest.sondageId + "  " + quest.sondageIdWeb + "  " + quest.questionIntitule + "  " + quest.questionNumero + "  " + quest.questionType
            )
        }
    }




    suspend fun getQuestionFromQuestionID(){
        val questions = db.myDAO().loadOneQuestionFromQuestionId(2)
        for (quest in questions) {
            Log.e(
                "testestQFQ",
                "Attention normalement que un resultat 2  :   " + quest.questionId + "  " + quest.questionIdWeb + "  " + quest.sondageId + "  " + quest.sondageIdWeb + "  " + quest.questionIntitule + "  " + quest.questionNumero + "  " + quest.questionType
            )
        }
    }

    suspend fun deleteAllQuestions(){
        db.myDAO().deleteAllQuestions()
//        db.myDAO().deleteQuestions()
        Log.e("testest", "Delete questions")
    }

    suspend fun  deleteAllSondages(){
        db.myDAO().deleteAllSondages()
//        db.myDAO().deleteSondages()
        Log.e("testest", "Delete sondages")
    }



    fun findSondageforNom(nomSondage : String): Sondage?{
        sondagesDisponibles.forEach {
            if(it.sondageNom.equals(nomSondage)){
                Log.e("ijiojijijoj", "nijnniubibuijno " + it.sondageId)
                return it
            }
        }
        return null
    }

//
//
//    class AccessDBBTask() : AsyncTask<Void, Void, String>() {
//        override fun doInBackground(vararg params: Void?): String? {
//            val count = 0
//                publishProgress((int) ((i / (float) count) * 100));
//                // Escape early if cancel() is called
//            return count;
//        }
//
//        override fun onPreExecute() {
//            super.onPreExecute()
//            // ...
//        }
//
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
////            showDialog("Downloaded " + result + " bytes");
//        }
//    }


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


    private fun LancementQuestionChoixMultiple(questionItem: QuestionItem) {

        val linearLayout = findViewById<LinearLayout>(R.id.LayoutLigne)

        listLayoutQuestion.add(linearLayout)

        // Créé le LinearLayout qui contiendra la question et les réponses.
        val linearLayoutHorizontal = Ajout_LinearLayout_Question_Reponse()

        // Créé le TextView de la question.
        Ajout_TextView_Question(linearLayoutHorizontal, questionItem.intitulerQuestion,
                questionItem.numeroQuestion)

        // Créé ce qu'il faut pour la réponse.
        Ajout_QCM_reponses(linearLayoutHorizontal, questionItem.parametresQuestion!!)
    }


    private fun LancementQuestionChoixUnique(questionItem: QuestionItem) {

        // Créé le LinearLayout qui contiendra la question et les réponses.
        val linearLayoutHorizontal = Ajout_LinearLayout_Question_Reponse()

        listLayoutQuestion.add(linearLayoutHorizontal)

        // Créé le TextView de la question.
        Ajout_TextView_Question(linearLayoutHorizontal, questionItem.intitulerQuestion,
                questionItem.numeroQuestion)

        // Créé ce qu'i lfaut pour la réponse.
        Ajout_QCU_reponses(linearLayoutHorizontal, questionItem.parametresQuestion!!)
    }


    private fun LancementQuestionAPoints(questionItem: QuestionItem) {

        // Créé le LinearLayout qui contiendra la question et la réponse.
        val linearLayoutHorizontal = Ajout_LinearLayout_Question_Reponse()

        listLayoutQuestion.add(linearLayoutHorizontal)

        // Créé le TextView de la question.
        Ajout_TextView_Question(linearLayoutHorizontal, questionItem.intitulerQuestion,
                questionItem.numeroQuestion)


        val editText = EditText(this)
        editText.id = View.generateViewId()
        editText.setTag("Reponse" + this.test)
        test++

        // On ajoute le radioGroup à la nouvelle ligne du listView.
        val params = LinearLayout.LayoutParams(param)
        params.gravity = Gravity.CENTER_HORIZONTAL
        linearLayoutHorizontal.addView(editText, 1, LinearLayout.LayoutParams(params))

        //        editText.setInputType();
    }


    private fun LancementQuestionTexteLibre(questionItem: QuestionItem) {

        // Créé le LinearLayout qui contiendra la question et la réponse.
        val linearLayoutHorizontal = Ajout_LinearLayout_Question_Reponse()

        listLayoutQuestion.add(linearLayoutHorizontal)

        // Créé le TextView de la question.
        Ajout_TextView_Question(linearLayoutHorizontal, questionItem.intitulerQuestion,
                questionItem.numeroQuestion)


        val editText = EditText(this)
        editText.id = View.generateViewId()
        editText.setTag("Reponse" + this.test)
        test++

        // On ajoute le radioGroup à la nouvelle ligne du listView.
        val params = LinearLayout.LayoutParams(param)
        params.gravity = Gravity.CENTER_HORIZONTAL
        linearLayoutHorizontal.addView(editText, 1, LinearLayout.LayoutParams(params))

        //        editText.setInputType();
    }


    fun Ajout_LinearLayout_Question_Reponse(): LinearLayout {
        val linearLayoutHorizontal = LinearLayout(this)
        linearLayoutHorizontal.orientation = LinearLayout.HORIZONTAL
        linearLayoutHorizontal.id = View.generateViewId()
        linearLayoutHorizontal.setPadding(0, 50, 0, 50)
        linearLayout!!.addView(linearLayoutHorizontal, linearLayout!!.childCount, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        return linearLayoutHorizontal
    }


    fun Ajout_TextView_Question(linearLayoutParent: LinearLayout, question: String?, numero: Int) {
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
            radioButton.tag = "Reponse" + this.test
            test++
            //On ajoute le radioButton au radiogroup précédant.
            var indice = linearLayoutParent.childCount - 1
            (linearLayoutParent.getChildAt(indice) as RadioGroup).addView(radioButton, (linearLayoutParent.getChildAt(indice) as RadioGroup).childCount, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
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
            checkBox.tag = "Reponse" + this.test
            test++
            //On ajoute le radioButton au radiogroup précédant.
            linearLayout.addView(checkBox, linearLayout.childCount, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
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

    override fun onItemSelected(parent: AdapterView<*>, view: View?,
                                pos: Int, id: Long) {
        val nom = parent.getItemAtPosition(pos) as String
        nomSondage = nom
        for (i in linearLayout!!.childCount downTo 2) {
            linearLayout!!.removeViewAt(i - 1)
        }
        if (nom != "Choissisez un sondage") {
            linearLayout!!.getChildAt(0).visibility = View.VISIBLE
            sondage = findSondageforNom(nom)
            if(sondage != null){
                Log.e("onItemSelected", "On a séléctionner un item")
                gettingSondageFromBDD(sondage!!.sondageId)
            }
        } else {
            linearLayout!!.getChildAt(0).visibility = View.INVISIBLE
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        Log.e("Spinner  : ", "On a cliqué sur rien.")

    }

    fun ReinitialiserLayoutSondage(view: View) {
            for(i in 0 until test){
                var view = linearLayout!!.findViewWithTag<View>("Reponse" + i)
                Log.e("reninit", "" + view.javaClass)
                when(view){
                    is CheckBox -> view.isChecked = false
                    is RadioButton -> view.isChecked = false
                    is EditText -> view.setText("")
                    else -> Log.e("ReinitialiserSondage","L'une des views n'est pas de classe acceptable.")
                }
            }
    }


    fun validationSondage(view : View){
        var listeReponses = ArrayList<String>()
        for(i in 0 until test){
            var view = linearLayout!!.findViewWithTag<View>("Reponse" + i)
            when(view){
                is CheckBox -> listeReponses.add(view.isChecked.toString())
                is RadioButton -> listeReponses.add(view.isChecked.toString())
                is EditText -> listeReponses.add(view.text.toString())
                else -> Log.e("ReinitialiserSondage","L'une des views n'est pas de classe acceptable.")
            }
        }
        var message = ""
        listeReponses.forEach {
            message = message.plus(it)
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        launch{
            if(sondage != null) {
                val reponse = Reponse(
                    sondageId = sondage!!.sondageId,
                    sondageIdWeb = sondage!!.sondageIdWeb,
                    listeReponses = message
                )
                db.myDAO().insertReponses(reponse)
            }
        }


        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java)
        val call_Post = serverApiService.getTestConnexion()

        Log.e("tets", "ted-tddt")

        call_Post.enqueue(object : Callback<ReponseRequete> {
            @Override
            override fun onResponse(call : Call<ReponseRequete>, response : Response<ReponseRequete>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
                    Log.e("TAG", "La personne a été enlevée")
                    //Met à jour l'affichage.
                } else {
                    //Affiche le code de la reponse, soit le code http de la requête.
                    Log.e("blah", "Status code : " /*+ response.code()*/)
                }
            }
            @Override
            override fun onFailure(call : Call<ReponseRequete>, t : Throwable ) {
                //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                Log.e("yhuygutcy", "pfkspfkdspckpkpsd :        " + t.localizedMessage)
//                ServiceGenerator.Message(this, "blah", t)
            }
        })
    }



    override fun onDestroy() {
        super.onDestroy()
        coroutineContext[Job]!!.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(sondage != null){
            outState.putInt(TAG_ID_SONDAGE,sondage!!.sondageId)
            val listReponses = ArrayList<String>()
            for(i in 0 until test){
                val view = linearLayout!!.findViewWithTag<View>("Reponse" + i)
                when(view){
                    is CheckBox ->  if(view.isChecked){
                        listReponses.add("True")
                    } else {
                        listReponses.add("False")
                    }
                    is RadioButton -> if(view.isChecked){
                        listReponses.add("True")
                    } else {
                        listReponses.add("False")
                    }
                    is EditText -> listReponses.add(view.text.toString())
                    else -> Log.e("onSaveInstanceState", "Une des views n'est pas d'une classe acceptable.")
                }
            }
            outState.putStringArrayList(TAG_LISTE_REPONSES,listReponses)
        } else {
            outState.putInt(TAG_ID_SONDAGE, -1)
        }
    }
}



