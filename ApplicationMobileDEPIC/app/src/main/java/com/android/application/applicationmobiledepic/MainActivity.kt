package com.android.application.applicationmobiledepic

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.ConnectivityManager
import android.os.Bundle
//import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.AppDatabase
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.*

//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.ParametreDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.QuestionDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.ReponseDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.ReponseSondageDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO.SondageDAO
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DatabaseHandler
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Parametre
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Retrofit.ServerApiService
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Retrofit.ServiceGenerator
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList

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
    private var listeQuestions : ArrayList<Question> = ArrayList()

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
    private val TAG_REPONSES = "reponses"
    private val TAG_INDICE_QUESTION_MAX = "indiceQuestionMax"

    private var savedInstanceState: Bundle? = Bundle()

    private var testConnexion = false

    private var indiceQuestionMax = 0
    private var indiceQuestionAfficher = 0

//    var networkChangeReceiver = NetworkChangeReceiver()

    private var listeReponsesTemporaires = ArrayList<String>()

    private var listeSousQuestions = ArrayList<Int>()

    private var listeChoix = ArrayList<Choix>()

    private var linearLayoutPourQuestionGroup: LinearLayout? = null
    private var linearLayoutPourQuestionPoint: LinearLayout? = null
    private var linearLayoutPourQuestionTexteLibre: LinearLayout? = null
    private var linearLayoutPourQuestionChoixMultiples: LinearLayout? = null
    private var radioGroupPourQuestionChoixUnique: RadioGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        // Initialise les différentes valeurs nécessaires.
        initialisationValeurs(savedInstanceState)
        // Initialise la BDD.
        initialisationBDD()
        // Test de la connexion.
        TestConnexion()
    }







    fun initialisationValeurs(savedInstanceState: Bundle?){

        if(savedInstanceState != null && savedInstanceState.javaClass == Bundle::class.java) {
            // On récupère l'id du dernier sondage utilisé et non envoyé
            val sondageId = savedInstanceState.getInt(TAG_ID_SONDAGE)
            // sondageId == 0 s'il n'y avait pas de sondage précédemment utilisé
            if (sondageId != -1) {
                listeReponsesTemporaires = savedInstanceState.getStringArrayList(TAG_LISTE_REPONSES)!!
                indiceQuestionMax = savedInstanceState.getInt(TAG_INDICE_QUESTION_MAX)
            }
        }


        //On sauvegarde le context
        context = this
        //On sauvegarde le linearLayout où les questions doivent être placés
        linearLayout = findViewById(R.id.layout_general)


        //Ajout de l'option annulatrice pour le choix du sondage
        nomSondagesDisponibles.add("Choissisez un sondage")

        //Recherche du spinner pour choix de sondages
        spinner = findViewById<View>(R.id.spinner) as Spinner
        //Create an ArrayAdapter using the string array and a default spinner layout
        this.spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nomSondagesDisponibles)


        //                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                        R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner!!.adapter = spinnerAdapter


        spinner!!.onItemSelectedListener = this

        this.savedInstanceState = savedInstanceState
    }


    fun initialisationBDD(){
        //Build de la base de données interne
        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).build()

    }




    fun TestConnexion(){
        val connectivityManager =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val networks = connectivityManager.allNetworks
        val networkInfo = connectivityManager.activeNetworkInfo
        // On test si le network est connecté
        if(networkInfo != null && networkInfo.isConnected){
            Log.e("Test connexion", "Il y a une connexion.")
            // Il y a une connexion mais on ne sait pas s'il y a accès à Internet.

            LancementRequeteTestConnexion()
            RechargementbaseDeDonnees()
        } else {
            // Pas de connexion.
            Log.e("Test connexion", "Il n'y a pas de connexion.")
            initialisationListeSondagesDisponibles()
            testConnexion = false
        }
    }


    fun ResultatTestConnexion(resultat : Boolean){
        if(resultat){
            Log.e("Resultat Test Connexion","On à accès à Internet")
            testConnexion = true
            ReceptionSondagesPublics()
            if(savedInstanceState != null && savedInstanceState!!.javaClass == Bundle::class.java) {
                val sondageId = savedInstanceState!!.getInt(TAG_ID_SONDAGE)
                RequeteSondageVoulu(sondageId)
                RecuperationQuestionsDuSondage(sondageId.toString())
            }
        } else {
            Log.e("Resultat Test Connexion","On n'à pas accès à Internet")
            testConnexion = false
            initialisationListeSondagesDisponibles()
        }
    }



    fun RechargementbaseDeDonnees(){
        //Remet à jour la BDD
        launch {
            db.myDAO().deleteAllQuestions()
            db.myDAO().deleteAllReponses()
            db.myDAO().deleteAllSondages()
            db.myDAO().deleteAllchoix()

        }
    }




    //Ajoute au spinner tous les noms de sondages disponibles dans la base de données.
    fun initialisationListeSondagesDisponibles(){
        launch{
//            var listeNomsSondagesDisponibles = db.myDAO().loadAllNomsSondages()
            var listeSondagesDisponibles = db.myDAO().loadAllSondages()
            listeSondagesDisponibles.forEach {
                nomSondagesDisponibles.add(it.intituleSondage)
                sondagesDisponibles.add(it)
            }
            if(savedInstanceState != null && savedInstanceState!!.javaClass == Bundle::class.java) {
                // On récupère l'id du dernier sondage utilisé et non envoyé
                val sondageId = savedInstanceState!!.getInt(TAG_ID_SONDAGE)
                // sondageId == 0 s'il n'y avait pas de sondage précédemment utilisé
                if (sondageId != -1) {
                    listeReponsesTemporaires = savedInstanceState!!.getStringArrayList(TAG_LISTE_REPONSES)!!
                    indiceQuestionMax = savedInstanceState!!.getInt(TAG_INDICE_QUESTION_MAX)
                    /*
                    linearLayout!!.getChildAt(0).visibility = View.VISIBLE
                    Log.e("uhdiudzdhi", "odjsdoicjdscojsd    " + sondageId)
                    gettingSondageFromBDDWithSavedInstanceState(sondageId)
                     */
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
//                "getAllSondages" -> getAllSondages()
//                "getAllQuestions" -> getAllQuestions()
//                "putSondages" -> putSondages()
//                "putQuestions" -> putQuestions()
//                "getSondageFromSondageId" -> getSondageFromSondageID()
//                "getQuestionFromSondageId" -> getQuestionFromSondageID()
//                "getQuestionFromQuestionId" -> getQuestionFromQuestionID()
//                "deleteAllSondages" -> deleteAllSondages()
//                "deleteAllQuestions" -> deleteAllQuestions()
            }

        }
    }





    suspend fun getSondageFromSondageID(sondageId: Int): Sondage?{
        val sondages = db.myDAO().loadOneSondageFromSondageId(sondageId)
        for (sond in sondages) {
//            Log.e(
//                "testestSFS",
//                "Attention normalement que un resultat   :   " + sond.id_sondage + "  " + sond.intituleSondage + "  " + sond.descriptionSondage + "  " + sond.administrateur_id
//            )
            if(sond.id_sondage == sondageId){
                return sond
            }
        }
        return null
    }


    fun gettingSondageFromBDDWithSavedInstanceState(sondageId: Int){
        launch {
            sondage = getSondageFromSondageID(sondageId)
            if(sondage != null){
//                Log.e("test sondage", "test " + sondage!!.sondageId + "  " + sondage!!.sondageIdWeb + "  " + sondage!!.sondageNom)
//                Log.e("gettingSondageFromBDD", "On a reçu le sondage de la BDD : " + sondage!!.sondageId)
                var questions = getQuestionFromSondageID(sondage!!.id_sondage)
                listeQuestions.addAll(questions)

                Log.e("gettingSondageFromBDD", "On a reçu les questions du sondage : " + questions.size)
//                affichageQuestions()
            } else {
                Log.e("test sondage", "NULL NULL NULL   : " + sondageId)
            }
            if(savedInstanceState != null && savedInstanceState!!.javaClass == Bundle::class.java) {
                val listeReponses = savedInstanceState!!.getStringArrayList(TAG_LISTE_REPONSES)
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
                 var questions = getQuestionFromSondageID(sondage!!.id_sondage)
                listeQuestions.addAll(questions)
//                affichageQuestions()
            } else {
                Log.e("test sondage", "NULL NULL NULL   : " + sondageId)
            }
        }
    }



    fun affichageQuestion(){

        var question = listeQuestions.get(indiceQuestionAfficher)

        if(question.questionType.equals("GroupeQuestion")){
            LancementQuestionGroupe(question)

        } else if(question.questionType.equals("QuestionOuverte")){
            LancementQuestionTexteLibre(question, linearLayout!!, null)

        } else if(question.questionType.equals("QuestionChoix")){
            LancementChoix(question, linearLayout!!)
//                RequeteChoixPourQuestionEtSondage(question, linearLayout!!, 0)


        } else if(question.questionType.equals("QuestionPoint")){
            LancementQuestionAPoints(question, linearLayout!!, null)
        }
    }



    /*
    fun affichageQuestions(){
        Log.e("affichageQuestions", "Il y a des question : " + listeQuestions.size)
        for (question in listeQuestions) {
            Log.e("affichageQuestion", "On essaye d'afficher une question")
            if(question.questionType.equals("GroupeQuestion")){
                Log.e("affichageQuestion", "groupe question")

            } else if (question.questionType.equals("QuestionOuverte")){
                Log.e("affichageQuestion", "groupe ouverte")
                LancementQuestionTexteLibre(question)

            } else if (question.questionType.equals("QuestionChoix") && question.lesChoix != null){
                val listeReponsesPossibles = ArrayList<String>()
                listeReponsesPossibles.addAll(question.lesChoix.split(";"))
                if(question.estUnique != null && question.estUnique){
                    Log.e("affichageQuestion", "groupe choix unique")
                    LancementQuestionChoixUnique(question, listeReponsesPossibles)
                } else if (question.estUnique != null) {
                    listeReponsesPossibles.addAll(question.lesChoix.split(";"))
                    LancementQuestionChoixMultiple(question, listeReponsesPossibles)
                }
            }
        }
*/




            /*
               for (question in listeQuestions) {
                   var questionItem: QuestionItem?
                   questionItem = convertisseurQuestion(question)
                   if (questionItem != null) {
                       Log.e("affichageQuestion", "On essaye d'afficher une question")
                       when (questionItem.typeQuestion) {
                           TypeQuestion.QUESTION_REPONSE_LIBRE -> LancementQuestionTexteLibre(
                               questionItem
                           )
                           TypeQuestion.QUESTION_A_POINTS -> LancementQuestionAPoints(questionItem)
                           TypeQuestion.QUESTION_CHOIX_UNIQUE -> LancementQuestionChoixUnique(
                               questionItem
                           )
                           TypeQuestion.QUESTION_CHOIX_MULTIPLE -> LancementQuestionChoixMultiple(
                               questionItem
                           )
                           else -> Log.e(
                               "affichageQuestions",
                               "Le type du questionItem n'est pas répertorié."
                           )
                       }
                   } else {
                       Log.e(
                           "affichageQuestions",
                           "Attention, un questionItem null est reçu de ConvertisseurQuestion"
                       )
                   }

               }
    }*/



    private fun convertisseurQuestion(question: Question): QuestionItem?{
        val listParametres = question.intitule.split(";")
        val intitule = listParametres.get(0)
        val arrayListParametres = ArrayList<String>()
        for(i in 1 until (listParametres.size-1)){
            arrayListParametres.add(listParametres.get(i))
        }
        val typeQuestion = getTypeQuestion(question.questionType)
        if(typeQuestion != null){
            val questionItem = QuestionItem(this, intitule, question.ordre, typeQuestion, arrayListParametres)
            return questionItem
        } else {
            Log.e("convertisseurQuestion", "Attention, une tentative de convertissement de typeQuestion à renvoyé null.")
        }
        return null
    }



    suspend fun getReponses(){
        val reponses = db.myDAO().loadAllReponses()
        for (reponse in reponses) {
//            Log.e(
//                "getReponses",
//                "Réponse  :   " + reponse.reponseId + "  " + reponse.sondageId + "  " + reponse.sondageIdWeb + "  " + reponse.listeReponses
//            )
        }
    }





    suspend fun getQuestionFromSondageID(sondageId: Int): Array<Question>{
        val questions = db.myDAO().loadOneQuestionFromSondageID(sondageId)
        for (quest in questions) {
            Log.e(
                "testestQFS",
                "Attention normalement que un resultat 1  :   " + quest.id_question + "  " + quest.sondage_id + "  " + quest.intitule + "  " + quest.questionType + "  " + quest.ordre
            )
        }
        return questions
    }


    fun AffichageSondageFromBDD(sondage: Sondage){
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        lateinit var questions : Array<Question>
        coroutineScope.launch {
            questions = getQuestionFromSondageID(sondage.id_sondage)
        }
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
        deleteAllQuestions()
        deleteAllSondages()
//        putQuestions()
//        putSondages()
//        getAllSondages()
//        getAllQuestions()
//        AffichageSondageFromBDD()
//        gettingSondageFromBDD(7)
    }

    suspend fun putSondages(){
        val sondage = Sondage(id_sondage = 2, intituleSondage = "sondage num 2", descriptionSondage = "description", administrateur_id = 1)
        db.myDAO().insertSondages(sondage)
    }

    suspend fun putQuestions(){
//        val question1 = Question(id_question = 2, id_sondage = 9, sondageIdWeb = 9, questionNumero = 1, questionType = "Question à choix multiple", questionIntitule =  "intitule de la pifitude;test reponse 1;test reponse 2; test reponse 3")
//        val question2 = Question(questionIdWeb = 3, sondageId = 9, sondageIdWeb = 9, questionNumero = 2, questionType = "Question à choix unique", questionIntitule =  "intitule de la pifitude version 2;test reponse 11; test reponse 222;test reponse 333")
//        val question3 = Question(questionIdWeb = 4, sondageId = 9, sondageIdWeb = 9, questionNumero = 3, questionType = "Question à points", questionIntitule =  "intitule de la pifitude version 2;test reponse 11; test reponse 222;test reponse 333")
//        val question4 = Question(questionIdWeb = 5, sondageId = 9, sondageIdWeb = 9, questionNumero = 4, questionType = "Question à réponse libre", questionIntitule =  "intitule de la pifitude version 2;test reponse 11; test reponse 222;test reponse 333")

//        db.myDAO().insertQuestions(question1, question2, question3, question4)
    }

    suspend fun getAllSondages(){
        val sondages = db.myDAO().loadAllSondages()
        for (sond in sondages) {
            Log.e(
                "testest",
                "" + sond.id_sondage + "  " + sond.intituleSondage + "  " + sond.descriptionSondage
            )
        }
    }

    suspend fun getAllQuestions(){
        val questions = db.myDAO().loadAllQuestions()
        for (quest in questions) {
            Log.e(
                "testest",
                "" + quest.id_question + "  " + quest.sondage_id + "  " + quest.intitule + "  " + quest.ordre + "  " + quest.questionType
            )
        }
    }




    suspend fun getQuestionFromQuestionID(){
        val questions = db.myDAO().loadOneQuestionFromQuestionId(2)
        for (quest in questions) {
            Log.e(
                "testestQFQ",
                "Attention normalement que un resultat 2  :   " + quest.id_question + "  " + quest.sondage_id + "  " + quest.intitule + "  " + quest.ordre + "  " + quest.questionType
            )
        }
    }

    suspend fun deleteAllQuestions(){
        db.myDAO().deleteAllQuestions()
//        db.myDAO().deleteQuestions()
    }

    suspend fun  deleteAllSondages(){
        db.myDAO().deleteAllSondages()
//        db.myDAO().deleteSondages()
    }



    fun findSondageforNom(nomSondage : String): Sondage?{
        sondagesDisponibles.forEach {
            if(it.intituleSondage.equals(nomSondage)){
                return it
            }
        }
        return null
    }



    fun RechercheQuestionIdDansListeReponsesTemporairePourIndice(question: Question): Int{
        for(ligneReponses in listeReponsesTemporaires){
            if(ligneReponses.contains(";") && ligneReponses.substring(0, ligneReponses.indexOf(";")).toInt() == question.id_question){
                return listeReponsesTemporaires.indexOf(ligneReponses)
            }
        }
        return -1
    }



    fun RechercheQuestionIdDansListeReponsesTemporaire(question: Question): String{
        for(ligneReponses in listeReponsesTemporaires){
            if(ligneReponses.contains(";") && ligneReponses.substring(0, ligneReponses.indexOf(";")).toInt() == question.id_question){
                return ligneReponses.substring(ligneReponses.indexOf(";")+1, ligneReponses.length)
            }
        }
        return ""
    }

    private fun LancementGenerique(question: Question, linearLayoutBase: LinearLayout) : LinearLayout{
        val linearLayout = findViewById<LinearLayout>(R.id.LayoutLigne)

        listLayoutQuestion.add(linearLayout)

        // Créé le LinearLayout qui contiendra la question et les réponses.
        val linearLayoutHorizontal = Ajout_LinearLayout_Question_Reponse(linearLayoutBase)

        // Créé le TextView de la question.
        Ajout_TextView_Question(linearLayoutHorizontal, question.intitule,
            question.ordre)

        return linearLayoutHorizontal
    }


    fun LancementChoix(question: Question, linearLayoutBase: LinearLayout){
        Log.e("Passage lanc choix", "On y pase")
        var listeStringTexteChoix = ArrayList<String>()
        for(choix in listeChoix){
            if(choix.question_id == question.id_question){
                listeStringTexteChoix.add(choix.intituleChoix)
            }
        }

        if(question.questionType.equals("QuestionChoix") && question.estUnique != null && question.estUnique){
            LancementQuestionChoixUnique(question, listeStringTexteChoix, linearLayoutBase)
        } else if(question.questionType.equals("QuestionChoix") && question.estUnique != null){
            LancementQuestionChoixMultiple(question, listeStringTexteChoix, linearLayoutBase)
        }
    }

    private fun LancementQuestionChoixMultiple(question: Question, listeReponsesPossibles : ArrayList<String>, linearLayoutBase: LinearLayout) {

        val linearLayoutHorizontal = LancementGenerique(question, linearLayoutBase)


        // Créé ce qu'il faut pour la réponse.
        Ajout_QCM_reponses(question, linearLayoutHorizontal, listeReponsesPossibles)
    }


    private fun LancementQuestionChoixUnique(question : Question, listeReponsesPossibles : ArrayList<String>, linearLayoutBase: LinearLayout) {

        val linearLayoutHorizontal = LancementGenerique(question, linearLayoutBase)

        // Créé ce qu'il faut pour la réponse.
        Ajout_QCU_reponses(question, linearLayoutHorizontal, listeReponsesPossibles)
    }


    private fun LancementQuestionAPoints(question: Question, linearLayoutBase: LinearLayout, reponseGroupe : String?) {

        val linearLayoutHorizontal = LancementGenerique(question, linearLayoutBase)

        val editText = EditText(this)
        editText.id = View.generateViewId()
        editText.setTag("Reponse_" + indiceQuestionAfficher + "_" + 0)
        test++

        val reponse = RechercheQuestionIdDansListeReponsesTemporaire(question)
        if(!reponse.equals("")){
            editText.setText(reponse)
        } else if(reponseGroupe != null){
            editText.setText(reponseGroupe)
        }

        // On ajoute le radioGroup à la nouvelle ligne du listView.
        val params = LinearLayout.LayoutParams(param)
        params.gravity = Gravity.CENTER_HORIZONTAL
        linearLayoutHorizontal.addView(editText, 1, LinearLayout.LayoutParams(params))

        linearLayoutPourQuestionPoint = linearLayoutHorizontal

        //        editText.setInputType();
    }


    private fun LancementQuestionTexteLibre(question : Question, linearLayoutBase: LinearLayout, reponseGroupe: String?) {

        val linearLayoutHorizontal = LancementGenerique(question, linearLayoutBase)

        val editText = EditText(this)
        editText.id = View.generateViewId()
        editText.setTag("Reponse_" + indiceQuestionAfficher + "_" + 0)

        val reponse = RechercheQuestionIdDansListeReponsesTemporaire(question)
        if(!reponse.equals("")){
            editText.setText(reponse)
        } else if(reponseGroupe != null){
            editText.setText(reponseGroupe)
        }

        test++

        // On ajoute le radioGroup à la nouvelle ligne du listView.
        val params = LinearLayout.LayoutParams(param)
        params.gravity = Gravity.CENTER_HORIZONTAL
        linearLayoutHorizontal.addView(editText, 1, LinearLayout.LayoutParams(params))

        linearLayoutPourQuestionTexteLibre = linearLayoutHorizontal

        //        editText.setInputType();
    }


    fun LancementQuestionGroupe(question: Question){
        // Créé le LinearLayout qui contiendra la question et la réponse.
        val linearLayoutVertical = Ajout_LinearLayout_Question_Groupe()

        listLayoutQuestion.add(linearLayoutVertical)

        // Créé le TextView de la question.
        Ajout_TextView_Question(linearLayoutVertical, question.intitule,
            question.ordre)

        linearLayoutPourQuestionGroup = linearLayoutVertical


//        val reponse = RechercheQuestionIdDansListeReponsesTemporaire(question)
//        var listeReponsesTemporaires: List<String>? = null
//        var indiceReponse = 0
//        if(!reponse.equals("")){
//            listeReponsesTemporaires = reponse.split(";")
//        }

        if(question.questionType == "GroupeQuestion" && question.numerosDeQuestionsGroupe != null){
            val listeSousQuestionsIdTemporaire = question.numerosDeQuestionsGroupe.split(";")
            for (sousQuestionId in listeSousQuestionsIdTemporaire){
                val sousQuestion = RechercheSousQuestionParId(sousQuestionId.toInt())
                if(sousQuestion != null) {
                    var reponse = RechercheQuestionIdDansListeReponsesTemporaire(sousQuestion)
                    if(reponse != "") {
                        if (sousQuestion.questionType.equals("QuestionOuverte")) {
                            LancementQuestionTexteLibre(
                                sousQuestion,
                                linearLayoutVertical,
                                reponse
                            )
                        } else if(sousQuestion.questionType.equals("QuestionChoix")) {

                        } else if(sousQuestion.questionType.equals("QuestionPoint")) {
                            LancementQuestionAPoints(
                                sousQuestion,
                                linearLayoutVertical,
                                reponse
                            )
                        }
                    } else {
                        if (sousQuestion.questionType.equals("QuestionOuverte")) {
                            LancementQuestionTexteLibre(
                                sousQuestion,
                                linearLayoutVertical,
                                null
                            )
                        } else if(sousQuestion.questionType.equals("QuestionChoix")) {

                        } else if(sousQuestion.questionType.equals("QuestionPoint")) {
                            LancementQuestionAPoints(
                                sousQuestion,
                                linearLayoutVertical,
                                null
                            )
                        }
                    }
                }

                /*
                for(question in listeQuestions){
                    if(question.id_question == sousQuestionId.toInt()) {
                        if(question.questionType.equals("QuestionOuverte")){
                            Log.e("affichageQuestion", "groupe ouverte")
                            if(listeReponsesTemporaires != null && indiceReponse < listeReponsesTemporaires.size) {
                                Log.e("test test test test","                                " + indiceReponse)
                                LancementQuestionTexteLibre(
                                    question,
                                    linearLayoutVertical,
                                    listeReponsesTemporaires.get(indiceReponse)
                                )
                                indiceReponse++
                            } else {
                                LancementQuestionTexteLibre(
                                    question,
                                    linearLayoutVertical,
                                    null
                                )
                            }
                        } else if(question.questionType.equals("QuestionChoix")){
//                            RequeteChoixPourQuestionEtSondage(question, linearLayoutVertical, 0)

                        } else if(question.questionType.equals("QuestionPoint")){
                            Log.e("test test test test","                                " + indiceReponse)
                            if(listeReponsesTemporaires != null && indiceReponse < listeReponsesTemporaires.size) {
                                LancementQuestionAPoints(
                                    question,
                                    linearLayoutVertical,
                                    listeReponsesTemporaires.get(indiceReponse)
                                )
                                indiceReponse++
                            } else {
                                LancementQuestionAPoints(
                                    question,
                                    linearLayoutVertical,
                                    null
                                )
                            }
                        }
                    }
                }*/
            }
        }



    }

    fun Ajout_LinearLayout_Question_Reponse(linearLayoutBase: LinearLayout): LinearLayout {
        val linearLayoutHorizontal = LinearLayout(this)
        linearLayoutHorizontal.orientation = LinearLayout.HORIZONTAL
        linearLayoutHorizontal.id = View.generateViewId()
        linearLayoutHorizontal.setPadding(0, 50, 0, 50)
        linearLayoutBase.addView(linearLayoutHorizontal, linearLayoutBase.childCount, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        return linearLayoutHorizontal
    }



    fun Ajout_LinearLayout_Question_Reponse_Dans_Question_Groupe(linearLayoutParent: LinearLayout): LinearLayout {
        val linearLayoutHorizontal = LinearLayout(this)
        linearLayoutHorizontal.orientation = LinearLayout.HORIZONTAL
        linearLayoutHorizontal.id = View.generateViewId()
        linearLayoutHorizontal.setPadding(0, 50, 0, 50)
        linearLayoutParent.addView(linearLayoutHorizontal, linearLayoutParent.childCount, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        return linearLayoutHorizontal
    }


    fun Ajout_LinearLayout_Question_Groupe(): LinearLayout {
        val linearLayoutVertical = LinearLayout(this)
        linearLayoutVertical.orientation = LinearLayout.VERTICAL
        linearLayoutVertical.id = View.generateViewId()
        linearLayoutVertical.setPadding(0, 50, 0, 50)
        linearLayout!!.addView(linearLayoutVertical, linearLayout!!.childCount, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT))
        return linearLayoutVertical
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


    fun Ajout_QCU_reponses(question: Question, linearLayoutParent: LinearLayout, reponsesPossibles: ArrayList<String>) {
        // On créé le radioGroup qui contiendra les différentes réponses possibles.
        val radioGroup = RadioGroup(this)
        // On donne une ID au radioGroup.
        radioGroup.id = View.generateViewId()
        // On ajoute le radioGroup à la nouvelle ligne du listView.
        val params = LinearLayout.LayoutParams(param)
        params.gravity = Gravity.CENTER_HORIZONTAL
        linearLayoutParent.addView(radioGroup, 1, LinearLayout.LayoutParams(params))
        radioGroupPourQuestionChoixUnique = radioGroup


        val reponseGlobal = RechercheQuestionIdDansListeReponsesTemporaire(question)
        val listReponses = reponseGlobal.split(";")

        // Pour chaque réponse possible.
        for (i in reponsesPossibles.indices) {
            // On créé le radioButton qui correspond à la réponse possible.
            val radioButton = RadioButton(context)
            // On donne une ID au radioButton.
            radioButton.id = View.generateViewId()
            radioButton.text = reponsesPossibles[i]
            radioButton.tag = "Reponse_" + indiceQuestionAfficher + "_" + i

            if(listReponses.size > i){
                radioButton.isChecked = listReponses[i].toBoolean()
            }

            test++
            //On ajoute le radioButton au radiogroup précédant.
            var indice = linearLayoutParent.childCount - 1
            (linearLayoutParent.getChildAt(indice) as RadioGroup).addView(radioButton, (linearLayoutParent.getChildAt(indice) as RadioGroup).childCount, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        }
    }

    fun Ajout_QCM_reponses(question: Question, linearLayoutParent: LinearLayout, reponsesPossibles: ArrayList<String>) {
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
        linearLayoutPourQuestionChoixMultiples = linearLayout

        val reponseGlobal = RechercheQuestionIdDansListeReponsesTemporaire(question)
        val listReponses = reponseGlobal.split(";")

        // Pour chaque réponse possible.
        for (i in reponsesPossibles.indices) {
            // On créé le radioButton qui correspond à la réponse possible.
            val checkBox = CheckBox(context)
            // On donne une ID au radioButton.
            checkBox.id = View.generateViewId()
            checkBox.text = reponsesPossibles[i]
            checkBox.tag = "Reponse_" + indiceQuestionAfficher + "_" + i

            if(listReponses.size > i){
                checkBox.isChecked = listReponses[i].toBoolean()
            }

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
        sondage = SelectionSondageVoulueParNom(nom)
        nomSondage = nom
        for (i in linearLayout!!.childCount downTo 2) {
            linearLayout!!.removeViewAt(i - 1)
        }
        if (nom != "Choissisez un sondage") {
            Log.e("ijoijoij", "     pas choissisez")
            linearLayout!!.getChildAt(0).visibility = View.VISIBLE
            if(testConnexion){
                Log.e("uhiuhihu", "iojoijoijoij")
                RequeteSondageVoulu(sondage!!.id_sondage)
                RecuperationQuestionsDuSondage(sondage!!.id_sondage.toString())
//                RequeteChoixPourQuestions()

            } else {
                Log.e("onItemSelected", "On a séléctionner un item")
                sondage = findSondageforNom(nom)
                if(sondage != null){
                    gettingSondageFromBDD(sondage!!.id_sondage)
                }
            }
        } else {
            Log.e("ijoijoij", "     choissisez")
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


        var testReponseComplete = true
        for(question in listeQuestions){
            if(question.estObligatoire && !listeSousQuestions.contains(question.id_question) && RechercheQuestionIdDansListeReponsesTemporaire(question) == ""){
                testReponseComplete = false
            }
        }

        if(testReponseComplete) {
            for(question in listeQuestions){
//                Log.e("validationSondage", "Etape 1       " + listeQuestions.size)
//                if(!listeSousQuestions.contains(question.id_question)){
//                    Log.e("validationSondage", "Etape 2       " + listeQuestions.size)
                    if(question.questionType == "QuestionGroupe") {
//                        Log.e("validationSondage", "Etape 3       " + listeQuestions.size)
                        val listeSousQuestionsId = question.numerosDeQuestionsGroupe!!.split(";")
                        for(sousQuestionId in listeSousQuestionsId){
//                            Log.e("validationSondage", "Etape 4       " + listeQuestions.size)
                            val sousQuestion = RechercheSousQuestionParId(sousQuestionId.toInt())
                            val reponseTemporaire = Reponse(
                                id_utilisateur = 1,
                                id_sondage = sousQuestion!!.sondage_id,
                                question_id = sousQuestion.id_question,
                                reponse = RechercheQuestionIdDansListeReponsesTemporaire(sousQuestion!!)
                            )
//                            Log.e("validationSondage", "Reponse à question groupe envoyé")
                            RequeteEnvoieReponse(reponseTemporaire)
                        }
                    } else {
                        var reponseTemporaire : Reponse? = null
                        if(question.questionType == "QuestionChoix"){
                            val listeStringReponses = RechercheQuestionIdDansListeReponsesTemporaire(question)
                            var listeBooleanReponses = listeStringReponses.split(";")
                            var listeIndiceReponses = ArrayList<Int>()
                            var indice = 0
                            for(bool in listeBooleanReponses){
                                if(bool.toBoolean()){
                                  listeIndiceReponses.add(indice)
                                }
                                indice++
                            }
                            var listeChoixPourQuestion = ArrayList<Choix>()
                            for(choix in listeChoix){
                                if(choix.question_id == question.id_question){
                                    listeChoixPourQuestion.add(choix)
                                }
                            }
                            var reponseString = ""
                            for(indiceTempo in listeIndiceReponses){
                                if(listeIndiceReponses.get(listeIndiceReponses.size-1) == indiceTempo){
                                    reponseString = reponseString + listeChoixPourQuestion.get(indiceTempo).id_choix
                                } else {
                                    reponseString = reponseString + listeChoixPourQuestion.get(indiceTempo).id_choix + ";"
                                }
                            }
                            reponseTemporaire = Reponse(
                                id_utilisateur = 1,
                                id_sondage = question.sondage_id,
                                question_id = question.id_question,
                                reponse = reponseString
                            )
                        } else {
                            reponseTemporaire = Reponse(
                                id_utilisateur = 1,
                                id_sondage = question.sondage_id,
                                question_id = question.id_question,
                                reponse = RechercheQuestionIdDansListeReponsesTemporaire(question)
                            )
                        }
                        RequeteEnvoieReponse(reponseTemporaire)
                    }
//                }
            }
/*
            for(question in listeQuestions){
                if(!listeSousQuestions.contains(question.id_question)){
                    if(question.questionType == "QuestionGroupe") {
                        val listeSousQuestionsId = question.numerosDeQuestionsGroupe!!.split(";")
                        for(sousQuestionId in listeSousQuestionsId){
                            val sousQuestion = RechercheSousQuestionParId(sousQuestionId.toInt())
                            val reponseTemporaire = Reponse(
                                id_utilisateur = 1,
                                id_sondage = sousQuestion!!.id_sondage,
                                question_id = sousQuestion!!.id_question,
                                reponse = RechercheQuestionIdDansListeReponsesTemporaire(sousQuestion!!)
                            )
                            Log.e("validationSondage", "Reponse à question groupe envoyé")
                        }
                    } else {
                        val reponseTemporaire = Reponse(
                            id_utilisateur = 1,
                            id_sondage = question.id_sondage,
                            question_id = question.id_question,
                            reponse = RechercheQuestionIdDansListeReponsesTemporaire(question)
                        )
                        Log.e("validationSondage", "Reponse à question non groupe envoyé")
                    }
                }
            }*/
        } else {
            Log.e("validationSondage", "Toutes les questions n'ont pas étés répondus.")
            Toast.makeText(
                this,
                "Vous n'avez pas répondu à toute sles questions.",
                Toast.LENGTH_LONG
            ).show()
        }

//        RequeteEnvoieReponse()
        /*
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
        */

        /*
        launch{
            if(sondage != null) {
                val reponse = Reponse(
                    sondageId = sondage!!.id_sondage,
                    sondageIdWeb = sondage!!.id_sondage,
                    listeReponses = message
                )
                db.myDAO().insertReponses(reponse)
            }
        }*/

//
//        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java)
//        val call_Post = serverApiService.getTestConnexion()
//
//        Log.e("tets", "ted-tddt")
//
//        call_Post.enqueue(object : Callback<ReponseRequete> {
//            @Override
//            override fun onResponse(call : Call<ReponseRequete>, response : Response<ReponseRequete>) {
//                //Test si la requête a réussi ( code http allant de 200 à 299).
//                if (response.isSuccessful()) {
//                    Log.e("TAG", "La personne a été enlevée")
//                    //Met à jour l'affichage.
//                } else {
//                    //Affiche le code de la reponse, soit le code http de la requête.
//                    Log.e("blah", "Status code : " /*+ response.code()*/)
//                }
//            }
//            @Override
//            override fun onFailure(call : Call<ReponseRequete>, t : Throwable ) {
//                //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
//                Log.e("yhuygutcy", "pfkspfkdspckpkpsd :        " + t.localizedMessage)
////                ServiceGenerator.Message(this, "blah", t)
//            }
//        })
    }




    object Foo{
        fun GestionVersionAvecConnexion(){
            Log.e("eeeeee","Test ultima.")

        }
    }

    fun GestionVersionSansConnexion(){

    }




    override fun onDestroy() {
        super.onDestroy()
        coroutineContext[Job]!!.cancel()
//        unregisterReceiver(networkChangeReceiver)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(sondage != null){
            outState.putInt(TAG_ID_SONDAGE,sondage!!.id_sondage)
            outState.putStringArrayList(TAG_LISTE_REPONSES, listeReponsesTemporaires)
            outState.putInt(TAG_INDICE_QUESTION_MAX, indiceQuestionMax)
        } else {
            outState.putInt(TAG_ID_SONDAGE, -1)

        }
        /*
        if(sondage != null){
            outState.putInt(TAG_ID_SONDAGE,sondage!!.id_sondage)
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
        */
    }





    fun RecuperationQuestionsDuSondage(id_sondage : String){

        listeQuestions = ArrayList()

        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java)
        val call_Post = serverApiService.getQuestionsDuSondage(id_sondage)

        call_Post.enqueue(object : Callback<List<ModeleQuestion>> {
            override fun onResponse(call : Call<List<ModeleQuestion>>, response : Response<List<ModeleQuestion>>) {
                if (response.isSuccessful()) {
                    var arrayList : ArrayList<ModeleQuestion> = ArrayList()
                    val allQuestions = response.body()
                    if (allQuestions != null){
                        for(question in allQuestions)
                            arrayList.add(question)
                    }
                    for(question in arrayList) {
                        Log.e(
                            "La question  :  ",
                            "id_question " + question.id_question + "\n"
                                    + "id_sondage " + question.sondage_id + "\n"
                                    + "intitule " + question.intitule + "\n"
                                    + "est_obligatoire " + question.estObligatoire + "\n"
                                    + "nombreChoix " + question.nombreChoix + "\n"
                                    + "estUnique " + question.estUnique + "\n"
                                    + "nombreDeCaractere " + question.nombreDeCaractere + "\n"
                                    + "ordre " + question.ordre + "\n"
                                    + "type " + question.type + "\n"

                        )
                    }
                    for(question in arrayList) {
                        listeQuestions.add(Transformation_ModeleQuestion_Question(question))
                        if(question.type == "GroupeQuestion"){
                            val listeSousQuestionsTemporaire = question.numerosDeQuestionsGroupe.split(";")
                            for (sousQuestion in listeSousQuestionsTemporaire){
                                listeSousQuestions.add(sousQuestion.toInt())
                            }
                        }
                    }
                    RequeteChoixParQuestionsPourSondage()
//                    affichageQuestions()
                    //Met à jour l'affichage.
                } else {
                    //Affiche le code de la reponse, soit le code http de la requête.
                    Log.e("blah", "Status code : " + response.raw() + "  " + response.code()/*+ response.code()*/)
                }
            }
            @Override
            override fun onFailure(call : Call<List<ModeleQuestion>>, t : Throwable ) {
                //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                Log.e("yhuygutcy", "pfkspfkdspckpkpsd :        " + t.localizedMessage)
//                ServiceGenerator.Message(this, "blah", t)
            }
        })
    }




    fun Transformation_ModeleQuestion_Question(modeleQuestion : ModeleQuestion): Question{
        return Question(modeleQuestion.id_question, modeleQuestion.sondage_id, modeleQuestion.intitule, modeleQuestion.estObligatoire, modeleQuestion.nombreChoix, modeleQuestion.estUnique, modeleQuestion.nombreDeCaractere, modeleQuestion.numerosDeQuestionsGroupe, modeleQuestion.ordre, modeleQuestion.type)
    }


    fun Transformation_ModeleChoix_Choix(modeleChoix: ModeleChoix): Choix{
        return Choix(modeleChoix.id_choix, modeleChoix.intituleChoix, modeleChoix.question_id)
    }


    fun LancementRequeteTestConnexion(){

        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java)
        val call_Post = serverApiService.getTestConnexion()

        call_Post.enqueue(object : Callback<ModeleSuccessConnexion> {
            override fun onResponse(call : Call<ModeleSuccessConnexion>, response : Response<ModeleSuccessConnexion>){
                if (response.isSuccessful()) {
                    val successConnexion = response.body() as ModeleSuccessConnexion
                    Toast.makeText(context, "Résultat du test de connexion : " + successConnexion.message + "  " + successConnexion.status, Toast.LENGTH_SHORT).show()
                    Log.e("oujoijoij", "Résultat du test de connexion :  " + successConnexion.message + "  " + successConnexion.status)
                } else {
                    //Affiche le code de la reponse, soit le code http de la requête.
                    Log.e("blah", "Status code : " + response.raw() + response.code()/*+ response.code()*/)
                }
                ResultatTestConnexion(true)
            }
            @Override
            override fun onFailure(call : Call<ModeleSuccessConnexion>, t : Throwable ) {
                //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                Log.e("yhuygutcy", "pfkspfkdspckpkpsd :        " + t.localizedMessage)
//                ServiceGenerator.Message(this, "blah", t)
                ResultatTestConnexion(false)
            }
        })
    }


    fun ReceptionSondagesPublics(){

//        sondagesDisponibles = ArrayList()
//        nomSondagesDisponibles = ArrayList()
//        nomSondagesDisponibles.add("Choissisez un sondage")
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java)
        val call_Post = serverApiService.getSondagesPublics()

//        Log.e("tets", "ted-tddt")

        call_Post.enqueue(object : Callback<List<ModeleSondagePossible>> {
            override fun onResponse(call : Call<List<ModeleSondagePossible>>, response : Response<List<ModeleSondagePossible>>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
//                    Log.e("TAG", "La personne a été enlevée")
                    val allSondages = response.body()
                    if (allSondages != null) {
                        for (sondage in allSondages) {
                            sondagesDisponibles.add(Transformation_ModeleSondage_Sondage(sondage))
                            nomSondagesDisponibles.add(sondage.intituleSondage)
                        }
                    }
                    //Met à jour l'affichage.
                } else {
                    //Affiche le code de la reponse, soit le code http de la requête.
                    Log.e("blah", "Status code : " + response.raw() + response.code()/*+ response.code()*/)
                }
            }
            @Override
            override fun onFailure(call : Call<List<ModeleSondagePossible>>, t : Throwable ) {
                //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                Log.e("yhuygutcy", "pfkspfkdspckpkpsd :        " + t.localizedMessage)
//                ServiceGenerator.Message(this, "blah", t)
            }
        })
    }

    fun PassageQuestionPrecedente(view: View) {
        affichageListeReponsesTemporaires()
        Log.e("PassageQuestPrec","test indice : " + indiceQuestionAfficher.toString())
        if(indiceQuestionAfficher > 0) {
            var deplacement = 1
            for (i in linearLayout!!.childCount downTo 2) {
                linearLayout!!.removeViewAt(i - 1)
            }

            while (listeSousQuestions.contains(indiceQuestionAfficher - deplacement + 1)) {
                deplacement++
            }

            sauvegardeReponsesAvantPassage()

            indiceQuestionAfficher = indiceQuestionAfficher - deplacement
            affichageQuestion()
        }
    }


    fun PassageQuestionSuivante(view: View) {
        affichageListeReponsesTemporaires()
        Log.e("PassageQuestSuiv","test indice : " + indiceQuestionAfficher.toString())
        Log.e("PassageQuestSuiv","test indice fin : " + listeQuestions.size.toString())
        if(indiceQuestionAfficher < listeQuestions.size-1) {
            var deplacement = 1
            for (i in linearLayout!!.childCount downTo 2) {
                linearLayout!!.removeViewAt(i - 1)
            }

            while (listeSousQuestions.contains(indiceQuestionAfficher + deplacement + 1)) {
                deplacement++
            }

            sauvegardeReponsesAvantPassage()

            if(deplacement > 0 && indiceQuestionAfficher == indiceQuestionMax){
                for(indice in 1 .. deplacement){
//                    listeReponsesTemporaires.add("")
                }
                indiceQuestionMax = indiceQuestionMax + deplacement
            }

            indiceQuestionAfficher = indiceQuestionAfficher + deplacement
            Log.e("yguygygigu", "ioouiuiouiouiouùiupio ipioioipisc sdc iscsipsi o iaoi çeiçriaçrierçe")
            affichageQuestion()
        }
    }


    fun sauvegardeReponsesAvantPassage(){
        Log.e("sauvegardeAVP","test indice : " + indiceQuestionAfficher.toString() + "    " + indiceQuestionMax.toString() + "   " + listeReponsesTemporaires.size)
        val questionTemporaire = listeQuestions.get(indiceQuestionAfficher)
        if(indiceQuestionAfficher == indiceQuestionMax && RechercheQuestionIdDansListeReponsesTemporairePourIndice(questionTemporaire) == -1 && !listeSousQuestions.contains(indiceQuestionAfficher+1)){
            val question = listeQuestions.get(indiceQuestionAfficher)

            if(question.questionType.equals("GroupeQuestion")){
                var reponses = ""
                var indice = 1
                val sousQuestionsId = question.numerosDeQuestionsGroupe!!.split(";")
                for(sousQuestionId in sousQuestionsId){
                    val sousQuestion = RechercheSousQuestionParId(sousQuestionId.toInt())
                    if(sousQuestion != null && sousQuestion.questionType == "QuestionOuverte"){
                        val linearLayoutTemporaire =
                            linearLayoutPourQuestionGroup!!.getChildAt(indice) as LinearLayout
                        val view = linearLayoutTemporaire.getChildAt(1) as EditText
                        listeReponsesTemporaires.add(sousQuestion.id_question.toString() + ";" + view.text.toString())
                        indice++
                    }
                }
                listeReponsesTemporaires.add(question.id_question.toString() + ";")
/*
                for(i in 1 until linearLayoutPourQuestionGroup!!.childCount){
                    if(i != 1){
                        reponses = reponses.plus(";")
                    }
                    Log.e("jjj","kkkklll    " + i)
                    val linearLayoutTemporaire =
                        linearLayoutPourQuestionGroup!!.getChildAt(i) as LinearLayout
                    val view = linearLayoutTemporaire.getChildAt(1) as EditText
                    reponses = reponses.plus(view.text.toString() + "")
                }
                listeReponsesTemporaires.add("sq" + question.id_question.toString() + ";" + reponses)*/
            } else if(question.questionType.equals("QuestionOuverte")
                && linearLayoutPourQuestionTexteLibre != null
                && linearLayoutPourQuestionTexteLibre!!.childCount == 2){
                val viewTest = linearLayoutPourQuestionTexteLibre!!.getChildAt(1) as EditText
                listeReponsesTemporaires.add(question.id_question.toString() + ";" + viewTest.text.toString())
            } else if(question.questionType.equals("QuestionChoix")
                && question.nombreChoix != null){
                if(question.estUnique != null
                    && question.estUnique
                    && radioGroupPourQuestionChoixUnique != null){
                    var reponse = question.id_question.toString()
                    for(i in 0 until radioGroupPourQuestionChoixUnique!!.childCount){
                        val view = radioGroupPourQuestionChoixUnique!!.getChildAt(i) as RadioButton
                        reponse = reponse.plus(";" + view.isChecked.toString())
                    }
                    listeReponsesTemporaires.add(reponse)
                } else if(question.estUnique != null
                    && linearLayoutPourQuestionChoixMultiples != null) {
                    var reponse = question.id_question.toString()
                    for(i in 0 until linearLayoutPourQuestionChoixMultiples!!.childCount){
                        val view = linearLayoutPourQuestionChoixMultiples!!.getChildAt(i) as CheckBox
                        reponse = reponse.plus(";" + view.isChecked.toString())
                    }
                    listeReponsesTemporaires.add(reponse)
                }
//                RequeteChoixPourQuestionEtSondage(question, null, 1)

            } else if(question.questionType.equals("QuestionPoint")
                && linearLayoutPourQuestionPoint != null
                && linearLayoutPourQuestionPoint!!.childCount == 2){
                val viewTest = linearLayoutPourQuestionPoint!!.getChildAt(1) as EditText
                listeReponsesTemporaires.add(question.id_question.toString() + ";" + viewTest.text.toString())
            }
        } else if (!listeSousQuestions.contains(indiceQuestionAfficher+1)){

            val question = listeQuestions.get(indiceQuestionAfficher)
            val indiceDansListeReponses = RechercheQuestionIdDansListeReponsesTemporairePourIndice(question)

            if(question.questionType.equals("GroupeQuestion")){
                var reponses = ""
                var indice = 1
                val sousQuestionsId = question.numerosDeQuestionsGroupe!!.split(";")
                for(sousQuestionId in sousQuestionsId){
                    val sousQuestion = RechercheSousQuestionParId(sousQuestionId.toInt())
                    val indiceSousQuestionDansListeReponses = RechercheQuestionIdDansListeReponsesTemporairePourIndice(sousQuestion!!)
                    if(sousQuestion != null && sousQuestion.questionType == "QuestionOuverte"){
                        val linearLayoutTemporaire =
                            linearLayoutPourQuestionGroup!!.getChildAt(indice) as LinearLayout
                        val view = linearLayoutTemporaire.getChildAt(1) as EditText
                        Log.e("fjzeoifjfjfoj", "isdjdjvodjvidjvd                          " + indiceDansListeReponses + "   " + indice + "   " + sousQuestionsId.size)
                        listeReponsesTemporaires.set(indiceDansListeReponses + indice - sousQuestionsId.size-1,sousQuestion.id_question.toString() + ";" + view.text.toString())
                        indice++
                    }
                }
                listeReponsesTemporaires.set(indiceDansListeReponses, question.id_question.toString() + ";")
/*
                for(i in 1 until linearLayoutPourQuestionGroup!!.childCount){
                    if(i != 1){
                        reponses = reponses.plus(";")
                    }
                    Log.e("jjj","kkkklll    " + i)
                    val linearLayoutTemporaire =
                        linearLayoutPourQuestionGroup!!.getChildAt(i) as LinearLayout
                    val view = linearLayoutTemporaire.getChildAt(1) as EditText
                    reponses = reponses.plus(view.text.toString() + "")
                }
                listeReponsesTemporaires.add("sq" + question.id_question.toString() + ";" + reponses)*/
            } else if(question.questionType.equals("QuestionOuverte")
                && linearLayoutPourQuestionTexteLibre != null
                && linearLayoutPourQuestionTexteLibre!!.childCount == 2){
                val viewTest = linearLayoutPourQuestionTexteLibre!!.getChildAt(1) as EditText
                listeReponsesTemporaires.set(indiceDansListeReponses, question.id_question.toString() + ";" + viewTest.text.toString())
            } else if(question.questionType.equals("QuestionChoix")
                && question.nombreChoix != null){
                if(question.estUnique != null
                    && question.estUnique
                    && radioGroupPourQuestionChoixUnique != null){
                    var reponse = question.id_question.toString()
                    for(i in 0 until radioGroupPourQuestionChoixUnique!!.childCount){
                        val view = radioGroupPourQuestionChoixUnique!!.getChildAt(i) as RadioButton
                        reponse = reponse.plus(";" + view.isChecked.toString())
                    }
                    listeReponsesTemporaires.set(indiceDansListeReponses, reponse)
                } else if(question.estUnique != null
                    && linearLayoutPourQuestionChoixMultiples != null) {
                    var reponse = question.id_question.toString()
                    for(i in 0 until linearLayoutPourQuestionChoixMultiples!!.childCount){
                        val view = linearLayoutPourQuestionChoixMultiples!!.getChildAt(i) as CheckBox
                        reponse = reponse.plus(";" + view.isChecked.toString())
                    }
                    listeReponsesTemporaires.set(indiceDansListeReponses, reponse)
                }
//                RequeteChoixPourQuestionEtSondage(question, null, 1)

            } else if(question.questionType.equals("QuestionPoint")
                && linearLayoutPourQuestionPoint != null
                && linearLayoutPourQuestionPoint!!.childCount == 2){
                val viewTest = linearLayoutPourQuestionPoint!!.getChildAt(1) as EditText
                listeReponsesTemporaires.set(indiceDansListeReponses, question.id_question.toString() + ";" + viewTest.text.toString())
            }
        }
    }






    fun RequeteChoixPourQuestions(){

        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java)
        val call_Post = serverApiService.getChoixPArSondage(sondage!!.id_sondage.toString())


        call_Post.enqueue(object : Callback<List<ModeleChoix>> {
            override fun onResponse(call : Call<List<ModeleChoix>>, response : Response<List<ModeleChoix>>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
//                    Log.e("TAG", "La personne a été enlevée")
                    val allChoix = response.body()
                    if (allChoix != null) {
                        for (choix in allChoix)
                            listeChoix.add(Transformation_ModeleChoix_Choix(choix))
                    }
                }
            }
            override fun onFailure(call : Call<List<ModeleChoix>>, t : Throwable ) {
                //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                Log.e("yhuygutcy", "pfkspfkdspckpkpsd :        " + t.localizedMessage)
//              ServiceGenerator.Message(this, "blah", t)
            }
        })
    }


    fun Transformation_ModeleSondage_Sondage(modeleSondage: ModeleSondagePossible): Sondage{
        return Sondage(modeleSondage.id_sondage, modeleSondage.intituleSondage, modeleSondage.descriptionSondage, modeleSondage.administrateur_id)
    }




    fun RequeteChoixParQuestionsPourSondage(){
        listeChoix = ArrayList()
        val indiceDerniereQuestionChoix = VerificationDerniereQuestionChoix()
        val derniereQuestionChoix = listeQuestions[indiceDerniereQuestionChoix]
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java)
        for(question in listeQuestions){
            if(question.questionType.equals("QuestionChoix")){

                val call_Post = serverApiService.getChoixParQuestionParSondage(question.sondage_id.toString(), question.id_question.toString())


                call_Post.enqueue(object : Callback<List<ModeleChoix>> {
                    override fun onResponse(call : Call<List<ModeleChoix>>, response : Response<List<ModeleChoix>>) {
                        //Test si la requête a réussi ( code http allant de 200 à 299).
                        if (response.isSuccessful()) {
                            val listChoixTemporaire = response.body()
                            if (listChoixTemporaire != null) {
                                for(choix in listChoixTemporaire) {
                                    listeChoix.add(Transformation_ModeleChoix_Choix(choix))
                                }
                            }
                            if(question == derniereQuestionChoix) {
                                affichageQuestion()
                            }
                        }
                    }
                    override fun onFailure(call : Call<List<ModeleChoix>>, t : Throwable ) {
                        //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                        Log.e("yhuygutcy", "pfkspfkdspckpkpsd :        " + t.localizedMessage)
//              ServiceGenerator.Message(this, "blah", t)
                    }
                })
            }
        }
    }


    fun RequeteSondageVoulu(sondage_id: Int){
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java)
        val call_Post = serverApiService.getSondage(sondage_id.toString())


        call_Post.enqueue(object : Callback<ModeleSondagePossible> {
            override fun onResponse(call : Call<ModeleSondagePossible>, response : Response<ModeleSondagePossible>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
                    val sondageVoulu = response.body()
                    if (sondageVoulu != null) {
                        sondage = Transformation_ModeleSondage_Sondage(sondageVoulu)
                    }
                }
            }
            override fun onFailure(call : Call<ModeleSondagePossible>, t : Throwable ) {
                //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                Log.e("yhuygutcy", "pfkspfkdspckpkpsd :        " + t.localizedMessage)
//              ServiceGenerator.Message(this, "blah", t)
            }
        })
    }


    fun SelectionChoixPourReponses(question: Question): Choix?{
        for(choix in listeChoix){
        }
        return null
    }


/*
    fun RequeteChoixPourQuestionEtSondage(question : Question, linearLayoutBase: LinearLayout?, indiceUtilisation: Int?){

        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java)
        val call_Post = serverApiService.getChoixParQuestionParSondage("" + question.id_sondage, "" + question.id_question)


        call_Post.enqueue(object : Callback<List<ModeleChoix>> {
            override fun onResponse(call : Call<List<ModeleChoix>>, response : Response<List<ModeleChoix>>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
//                    Log.e("TAG", "La personne a été enlevée")
                    var arrayList : ArrayList<ModeleChoix> = ArrayList()
                    val allStringChoix = ArrayList<String>()
                    val allChoix = response.body()
                    if (allChoix != null){
                        for(choix in allChoix)
                            arrayList.add(choix)
                    }

                    for(choix in arrayList) {
                        allStringChoix.add(choix.intituleChoix)
                        /*
                        Log.e(
                            "Le choix  :  ",
                            "id_choix " + choix.id_choix + "\n  question_id   " + choix.question_id + "\n  intituleChoix  " + choix.intituleChoix
                        )
                        */
                    }
                    if(indiceUtilisation == 0) {
                        if (question.estUnique != null && question.estUnique) {
                            LancementQuestionChoixUnique(question, allStringChoix, linearLayoutBase!!)
                        } else if (question.estUnique != null) {
                            LancementQuestionChoixMultiple(
                                question,
                                allStringChoix,
                                linearLayoutBase!!
                            )
                        }
                    } else {
                        var totalReponsesQuestion = ""
                        if(question.estUnique != null && question.nombreChoix != null && question.estUnique) {
                            for (i in 0 until question.nombreChoix) {
                                val view = linearLayout!!.findViewWithTag<View>("Reponse_" + indiceQuestionAfficher + "_" + i) as RadioButton
                                totalReponsesQuestion = totalReponsesQuestion.plus(";" + view.isChecked.toString())
                            }
                        } else if (question.estUnique != null && question.nombreChoix != null) {
                            for (i in 0 until question.nombreChoix) {
                                val view = linearLayout!!.findViewWithTag<View>("Reponse_" + indiceQuestionAfficher + "_" + i) as CheckBox
                                totalReponsesQuestion = totalReponsesQuestion.plus(";" + view.isChecked.toString())
                            }
                        }
                        listeReponsesTemporaires.add(question.id_question.toString() + totalReponsesQuestion)
                    }
                } else {
                    //Affiche le code de la reponse, soit le code http de la requête.
                    Log.e("blah", "Status code : " + response.raw() + response.code()/*+ response.code()*/)
                }
            }
            @Override
            override fun onFailure(call : Call<List<ModeleChoix>>, t : Throwable ) {
                //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                Log.e("yhuygutcy", "pfkspfkdspckpkpsd :        " + t.localizedMessage)
//                ServiceGenerator.Message(this, "blah", t)
            }
        })
    }*/



    fun SelectionSondageVoulueParNom(nomsondage: String): Sondage?{
        for(sondage in sondagesDisponibles){
            if(sondage.intituleSondage.equals(nomsondage)){
                return sondage
            }
        }
        return null
    }



    fun VerificationDerniereQuestionChoix(): Int{
        var indiceDerniereQuestionChoix = -1
        for(question in listeQuestions){
            if(question.questionType.equals("QuestionChoix")){
                indiceDerniereQuestionChoix = listeQuestions.indexOf(question)
            }
        }
        return indiceDerniereQuestionChoix
    }



    fun RequeteEnvoieReponse(reponse: Reponse){
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java)
        val call_Post = serverApiService.EnvoieReponse(reponse.id_utilisateur, reponse.id_sondage, reponse.question_id, reponse.reponse)

        Log.e("RequeteEnvoieReponse", "La reponse a envoyé : " + reponse.question_id + "  " + reponse.id_utilisateur + " "+ reponse.reponse)

        call_Post.enqueue(object : Callback<Reponse> {
            override fun onResponse(call : Call<Reponse>, response : Response<Reponse>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
                    val reponseRecu = response.body()
                    if (reponseRecu != null) {
                        Log.e("RequeteEnvoieReponse: ", reponseRecu.id_utilisateur.toString() + "  " + reponseRecu.id_sondage.toString()
                                + "  " + reponseRecu.question_id.toString() + "  " + reponseRecu.reponse)
                    } else {
                        Log.e("RequeteEnvoieReponse", "La reponse recu est null")
                    }
                }
            }
            override fun onFailure(call : Call<Reponse>, t : Throwable ) {
                //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                Log.e("RequeteEnvoieReponse", "Erreur de connexion" + t.localizedMessage)
//              ServiceGenerator.Message(this, "blah", t)
            }
        })
    }


    fun RechercheSousQuestionParId(sousQuestionId : Int): Question?{
        for(question in listeQuestions){
            if(question.id_question == sousQuestionId){
                return question
            }
        }
        return null
    }




    fun affichageListeReponsesTemporaires(){
        Log.e("ListeTemporaire : ", " \n\n\n\n ")
        for(reponseTempo in listeReponsesTemporaires){
            Log.e("ListeTemporaire : ", "                                                        " + reponseTempo + " \n ")
        }
        Log.e("ListeTemporaire : ", " \n\n\n ")
    }










}