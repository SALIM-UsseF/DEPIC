package com.android.application.applicationmobiledepic

import android.app.Activity
import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcel
//import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.room.Room
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.AppDatabase
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.*
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.EtatReponse
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.EtatSondage
import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.TokenAuthentification

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
    private var listLayoutQuestion = ArrayList<View>()

    //Le sondage actuellement en cours d'utilisation
    private var sondage : Sondage? = null

    //L'arraylist de questions du sondage actuellement affiché
    private var listeQuestions : ArrayList<Question> = ArrayList()

    // L'arrayList de sousQuestions des questions groupes du sondage actuellement affiché
    private var listeSousQuestions = ArrayList<Question>()

    //L'arraylist de choix du sondage actuellement affiché
    private var listeChoix : ArrayList<Choix> = ArrayList()

    //Le nom du sondage actuellement utilise
    private var nomSondage = "Choissisez un sondage"

    //L'arraylist contenant les noms des sondages disponibles à l'utilisateur
    private var nomSondagesDisponibles = ArrayList<String>()

    //L'arraylist conenant les sondages disponibles à l'utilisateur
    private var sondagesDisponibles = ArrayList<Sondage>()

    private lateinit var spinnerAdapter : ArrayAdapter<String>

    private var listeTAGReponse = ArrayList<String>()

    private var sondageId: Int? = null

    // List pour le stockage
    private var listeTotaleSondages : ArrayList<Sondage> = ArrayList()

    private var listeTotaleQuestions : ArrayList<Question> = ArrayList()


    private var listeTotaleChoix : ArrayList<Choix> = ArrayList()
    //String keys for bundle
    private val TAG_LISTE_REPONSES = "listeReponses"
    private val TAG_ID_SONDAGE = "sondageId"
    private val TAG_REPONSES = "reponses"
    private val TAG_INDICE_QUESTION_MAX = "indiceQuestionMax"
    private val TAG_INDICE_QUESTION_A_AFFICHER = "indiceQuestionAAfficher"
    private val TAG_POSITION_SPINNER = "positionSpinnerItem"
    private val TAG_UTILISATEUR = "utlisateur"
    private val TAG_TOKEN_AUTHENTIFICATION = "tokenAuthentification"
    private val TAG_INTENT_ACTIVITY_CATEGORIES = 1
    private val TAG_MESSAGE_ERREUR = "MessageErreur"

    private var idUtilisateur: Int? = null

    private var tokenAuthentification : String? = null

    /**
     * L'utilisateur qui utilise l'application, seulement un peut exister, a un id de -1 si l'utilisateur ne s'est pas enregistré.
     */
    private var utilisateur : Utilisateur? = null


    private var testConnexion = false

    private var savedInstanceStateBundle: Bundle? = Bundle()
    private var indiceQuestionMax = 0

    private var indiceQuestionAfficher = 0

    private var listeReponsesTemporaires = ArrayList<String>()

//    private var listeSousQuestions = ArrayList<Int>()
    private var linearLayoutPourQuestionGroup: LinearLayout? = null
    private var linearLayoutPourQuestionPoint: LinearLayout? = null
    private var linearLayoutPourQuestionTexteLibre: LinearLayout? = null
    private var linearLayoutPourQuestionChoixMultiples: LinearLayout? = null

    private var boutonPrecedent : Button? = null
    private var boutonSuivant : Button? = null
    private var boutonValider : Button? = null

    private var radioGroupPourQuestionChoixUnique: RadioGroup? = null
    private lateinit var linearLayoutPrecedentSuivant : LinearLayout


    private lateinit var linearLayoutValiderReinit : LinearLayout
    private var dialogUtilisateur: AlertDialog? = null

    private var viewLinearLayoutAlertDialog:LinearLayout? = null

    private var positionSpinner : Int? = 0

    private var debutTestConnexion = true

    private var listeReponsesEnProbation : ArrayList<Reponse> = ArrayList()

    private var fragmentCategories : Fragment? = null

    private var fragmentManager: FragmentManager? = null

    private var listeCategories = ArrayList<Categorie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        Toast.makeText(context, "Veuillez patienter, la connexion au serveur est en train d'être testée.", Toast.LENGTH_SHORT).show()
        savedInstanceStateBundle = savedInstanceState
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        this.registerReceiver(networkChangeReceiver, filter)
        Log.e("onCreate", "On lance l'application")
        // Initialise la base de données interne.
        InitialisationBD()
        // Test la présence d'authentification, et s'il n'y en a pas, s'authentifie au serveur.
        InitialisationAuthentification(savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflaterOptions : MenuInflater = menuInflater
        menuInflaterOptions.inflate(R.menu.menu_acces_categories, menu)
        return super.onCreateOptionsMenu(menu)
    }


    fun VerificationMemeCategorie(categorieDB: Categorie, categorieServeur: Categorie): Categorie{
        if(categorieDB.id_categorie == categorieServeur.id_categorie){
            if(categorieDB.intitule == categorieServeur.intitule){
                return categorieDB
            } else {
                categorieDB.intitule = categorieServeur.intitule
                return categorieDB
            }
        } else {
            return categorieServeur
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        launch {
            if (requestCode == TAG_INTENT_ACTIVITY_CATEGORIES) {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    var listeCategoriesTemporaire = listeCategories
                    listeCategories = ArrayList()
                    // On récupère les valeurs des catégories.
                    val listeIdTemp = data.getIntegerArrayListExtra("ListeIdCategorie")
                    val listeIntituleTemp = data.getStringArrayListExtra("ListeIntituleCategorie")
                    val listeActiveTemp = data.getIntegerArrayListExtra("ListeActiveCategorie")
                    if (listeIdTemp.size != 0) {
                        for (i in 0..listeIdTemp.size - 1) {
                            var categorieTemporaire: Categorie
                            if (listeActiveTemp[i] == 1) {
                                categorieTemporaire =
                                    Categorie(listeIdTemp[i], listeIntituleTemp[i], true)
                            } else {
                                categorieTemporaire =
                                    Categorie(listeIdTemp[i], listeIntituleTemp[i], false)
                            }
                                listeCategories.add(categorieTemporaire)
                        }
                        AjoutAllCategoriesDansBDPerso()
                    }
                    InitialisationListeSondagesDisponibles()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.Menu_Acces_Categories){
            var intent : Intent = Intent(context, CategoriesActivity::class.java)


            Log.e("oicsdqjsviodjdsqoij", "pocjpovkpockwdpokcpoksqpokc                              : " + listeCategories.size)


            val listeIdCategorie = ArrayList<Int>()
            val listeIntituleCategorie = ArrayList<String>()
            val listeActiveCategorie = ArrayList<Int>()
            for(categorie in listeCategories) {
                listeIdCategorie.add(categorie.id_categorie)
                listeIntituleCategorie.add(categorie.intitule)
                if(categorie.active){
                    listeActiveCategorie.add(1)
                } else {
                    listeActiveCategorie.add(0)
                }
            }
            intent.putIntegerArrayListExtra("ListeIdCategorie",listeIdCategorie)
            intent.putStringArrayListExtra("ListeIntituleCategorie",listeIntituleCategorie)
            intent.putIntegerArrayListExtra("ListeActiveCategorie",listeActiveCategorie)
            startActivityForResult(intent,TAG_INTENT_ACTIVITY_CATEGORIES)

        }
        return true
    }


    fun Categorie(`in`: Parcel, categorie : Categorie) {
        categorie.id_categorie = `in`.readInt()
        var texteTemp = `in`.readString()
        if(texteTemp != null){
            categorie.intitule = texteTemp
        }
        var activeInt = `in`.readInt()
        categorie.active = (activeInt == 1)
    }


    fun writeToParcel(dest: Parcel?, categorie: Categorie) {
        if(dest != null){
            dest.writeInt(categorie.id_categorie)
            dest.writeString(categorie.intitule)
            if(categorie.active){
                dest.writeInt(1)
            } else {
                dest.writeInt(0)
            }
        }
    }



    /**
     * Fonction initialisant la base de données interne.
     */
    fun InitialisationBD(){
        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).build()
        Log.e("InitialisationBD", "On initialise la BD interne à l'application")
    }

    /**
     * Fonction permettant de tester si l'authentification est obtenu et dans le cas négatif de la demander.
     * A la fin, on passe à la gestion de l'utilisateur.
     */
    fun InitialisationAuthentification(savedInstanceState: Bundle?){
        launch {
            withContext(Dispatchers.IO) {
                // On charge la liste de tokens sauvergadés.
                val tokens = db.myDAO().loadTokens()
                if (tokens.size == 1) {
                    // Si il y en a un, on l'utilise
                    tokenAuthentification = tokens[0].auth_token
                    InitialisationUtilisateur()
                } else if (tokens.size == 0) {
                    //Si il n'y en a pas, on demande le token au serveur.
                    var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
                    val call_Post = serverApiService.authentification(email = "mobileapp@gmail.com", password = "mobileapptest")

                    call_Post.enqueue(object : Callback<TokenAuthentification> {
                        override fun onResponse(call: Call<TokenAuthentification>, response: Response<TokenAuthentification>) {
                            //Test si la requête a réussi ( code http allant de 200 à 299).
                            if (response.isSuccessful()) {
                                var tokenAuthentificationTemporaire = response.body()!!
                                tokenAuthentification = tokenAuthentificationTemporaire.auth_token
                                // On sauvegarde le token
                                sauvegardeToken(tokenAuthentificationTemporaire)
                            } else {
                                Toast.makeText(context,"Une erreur est arrivé, avertissez le support : il y a une connexion au serveur mais ", Toast.LENGTH_LONG).show()
                                Log.e("AUTHENTIFICATION", "Token ratée : " + response.raw())
                                InitialisationUtilisateur()
                            }
                        }

                        override fun onFailure(call: Call<TokenAuthentification>, t: Throwable) {
                            Log.e("AUTHENTIFICATION", "Erreur de connexion : " + t.localizedMessage)
                            testConnexion = false
                            InitialisationUtilisateur()
                        }
                    })
                } else {
                    // Si il y en a plus, ce n'est pas censé arriver.
                    Log.e("TOKENS", "Il y a trop de tokens.")
                    Toast.makeText(context, "Une erreur est arrivé, avertissez le support : il y a trop de tokens d'authentification.", Toast.LENGTH_LONG).show()
                    // On essaye avec le premier token
                    tokenAuthentification = tokens[0].auth_token
                    InitialisationUtilisateur()
                }
            }
        }
    }


    /**
     * Fonction sauvegardant le token d'authentification dans la BD
     */
    fun sauvegardeToken(tokenAuthentificationTemporaire : TokenAuthentification){
        launch {
            db.myDAO().insertToken(tokenAuthentificationTemporaire)
            InitialisationUtilisateur()
        }
    }


    /**
     * Fonction initialisant l'utilisateur au lancement de l'application.
     * Si il n'y a pas d'utilisateurs dans la base de données interne, on demande à l'utilisateur s'il veut s'enregistrer.
     * Sinon on prend le premier et unique utilisateur.
     */
    fun InitialisationUtilisateur(){
        launch {
            Log.e("InitUtilisateur", "On initialise l'utilisateur")
            val listeUtilisateurs = db.myDAO().loadUtilisateur()
            if (listeUtilisateurs.size == 0 || (listeUtilisateurs.size == 1 && listeUtilisateurs[0].email.equals(""))) {
                // On demande à l'utilisateur de s'enregistrer si il n'y en a pas d'enregistrer ou que celui qui l'est ne vaut rien (anonyme).
                CreationAlertDialogPourUtilisateur()
            } else if (listeUtilisateurs.size == 1 && !listeUtilisateurs[0].email.equals("")){
                // Si il y a un utilisateur non anonyme, on l'utilise
                utilisateur = listeUtilisateurs[0]
                idUtilisateur = utilisateur!!.id_utilisateur
                LancementRequeteTestConnexion()
            } else if(listeUtilisateurs[0].email == ""){
                // Si il y a trop d'utilisateur, on utilise le premier
                CreationAlertDialogPourUtilisateur()
            } else {
                // Si il y a trop d'utilisateur, on utilise le premier
                utilisateur = listeUtilisateurs[0]
                idUtilisateur = utilisateur!!.id_utilisateur
                LancementRequeteTestConnexion()
            }
        }
    }

    /**
     * Fonction créant l'alerte pour enregistrer l'utilisateur si il veut.
     */
    fun CreationAlertDialogPourUtilisateur(){
        viewLinearLayoutAlertDialog = layoutInflater.inflate(R.layout.layout_edittext_alertdialog, null) as LinearLayout
        val builderDialogUtilisateur = AlertDialog.Builder(this)
        builderDialogUtilisateur.setMessage(R.string.Message_Utilisateur)
            .setView(viewLinearLayoutAlertDialog)
            .setPositiveButton(R.string.Button_Accepter,
                DialogInterface.OnClickListener { dialog, id ->
                    val editText = viewLinearLayoutAlertDialog!!.getChildAt(0) as EditText
                    val regex = ".+@.+\\..+".toRegex()
                    if(regex.matches(editText.text)){
                        RequeteEnvoieNouveauUtilisateur(editText.text.toString())
                    } else {
                        RequeteEnvoieNouveauUtilisateur("")
                    }
                })
            .setNegativeButton(R.string.Button_Refuser,
                DialogInterface.OnClickListener { dialog, id ->
                    // L'utilisateur ne veut pas pour l'instant.
                    RequeteEnvoieNouveauUtilisateur("")
                })

        dialogUtilisateur = builderDialogUtilisateur.create()
        dialogUtilisateur!!.show()
    }


    /**
     * Fonction envoyant la requête à la base de données pour enregistrer l'utilisateur.
     * On envoie l'email donnée ("" si l'utilisateur n'a pas voulu s'enregistrer)
     */
    fun RequeteEnvoieNouveauUtilisateur(email: String){

        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.EnvoieNouveauUtilisateur(email= email, adresseIP = "")

        call_Post.enqueue(object : Callback<Utilisateur> {
            override fun onResponse(call : Call<Utilisateur>, response : Response<Utilisateur>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
                    val utilisateur = response.body()
                    if (utilisateur != null) {
                        idUtilisateur = utilisateur.id_utilisateur
                        utilisateur.id_utilisateur = idUtilisateur!!
                        launch {
                            var utilisateurTemp = Utilisateur(utilisateur.id_utilisateur, email, "")
                            val listeUtilisateursDansBDDPerso = db.myDAO().loadUtilisateur()
                            if(listeUtilisateursDansBDDPerso.isNotEmpty()) {
                                db.myDAO().updateUtilisateur(listeUtilisateursDansBDDPerso.get(0).id_utilisateur, email)
                            } else {
                                db.myDAO().insertUtilisateur(utilisateurTemp)
                            }
                            LancementRequeteTestConnexion()
                        }
                    } else {
                        Log.e("RequeteNouvUtilisateur", "La reponse recu est null")
                        LancementRequeteTestConnexion()
                    }
                }
            }
            override fun onFailure(call : Call<Utilisateur>, t : Throwable ) {
                Log.e("RequeteNouvUtilisateur", "Erreur de connexion" + t.localizedMessage)
                testConnexion = false
                LancementRequeteTestConnexion()
            }
        })
    }



















    fun InitialisationValeurs(){

        //On sauvegarde le linearLayout où les questions doivent être placés, les différents boutons et leurs layouts.
        linearLayout = findViewById(R.id.layout_general)
        boutonPrecedent = findViewById(R.id.Button_Precedent)
        boutonPrecedent!!.visibility = View.INVISIBLE
        boutonSuivant = findViewById(R.id.Button_Suivant)
        boutonSuivant!!.visibility = View.INVISIBLE
        boutonValider = findViewById(R.id.Button_Validation_Enregistrement)
        boutonValider!!.visibility = View.INVISIBLE
        linearLayoutPrecedentSuivant = findViewById(R.id.Layout_Buttons_Precedent_Suivant)
        linearLayoutPrecedentSuivant.visibility = View.INVISIBLE
        linearLayoutValiderReinit = findViewById(R.id.Layout_Buttons_Valider_Reinitialiser)
        linearLayoutValiderReinit.visibility = View.INVISIBLE

        //Recherche du spinner pour choix de sondages
        spinner = findViewById<View>(R.id.spinner) as Spinner
        //Create an ArrayAdapter using the string array and a default spinner layout
        this.spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nomSondagesDisponibles)
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner!!.adapter = spinnerAdapter

        spinner!!.onItemSelectedListener = this

        if(savedInstanceStateBundle != null) {

//            for (i in linearLayout!!.childCount downTo 2) {
//                linearLayout!!.removeViewAt(i - 1)
//            }

            // On récupère l'id du dernier sondage utilisé et non envoyé
            val sondageId = savedInstanceStateBundle!!.getInt(TAG_ID_SONDAGE)
            // sondageId == -1 s'il n'y avait pas de sondage précédemment utilisé
            if (sondageId != -1) {
                positionSpinner = savedInstanceStateBundle!!.getInt(TAG_POSITION_SPINNER)
                spinner!!.setSelection(positionSpinner!!)
                var listeTemporaire = savedInstanceStateBundle!!.getStringArrayList(TAG_LISTE_REPONSES)
                if(listeTemporaire != null) {
                    listeReponsesTemporaires = listeTemporaire
                }

                indiceQuestionMax = savedInstanceStateBundle!!.getInt(TAG_INDICE_QUESTION_MAX)
                indiceQuestionAfficher = savedInstanceStateBundle!!.getInt(TAG_INDICE_QUESTION_A_AFFICHER)
                linearLayoutValiderReinit.visibility = View.VISIBLE
                linearLayoutPrecedentSuivant.visibility = View.VISIBLE
            }
//            idUtilisateur = savedInstanceState.getInt(TAG_UTILISATEUR)
//            // idUtilisateur vaut -1 si il a appuyé sur refuser la dernière fois.
//            if(idUtilisateur == null || idUtilisateur == -1){
//                launch{
//                    val utilisateur = db.myDAO().loadUtilisateur()
//                    if(utilisateur != null){
//                        idUtilisateur = utilisateur.id_utilisateur
//                    } else {
//                        CreationAlertDialogPourUtilisateur()
//                    }
//                }
//            }
        } else {
//            Log.e("LOGLOG", "Il n'y a pas de savedInstace")
//            launch{
//                val utilisateur = db.myDAO().loadUtilisateur()
//                if(utilisateur != null){
//                    idUtilisateur = utilisateur.id_utilisateur
//                } else {
//                    CreationAlertDialogPourUtilisateur()
//                }
//            }
        }
//        //Ajout de l'option annulatrice pour le choix du sondage
//        nomSondagesDisponibles.add("Choissisez un sondage")
    }


    private var networkChangeReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val connectivityManager =
                context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected) {
//                //Il y a une connexion
                Toast.makeText(context, "Il y a de la connexion à un réseau, tentative d'accès au serveur.", Toast.LENGTH_SHORT).show()
                if(!testConnexion && !debutTestConnexion) {
                    TestConnexion()
                }

            } else {
//                // il n'y a pas de connexion
                Toast.makeText(context, "Il n'y a pas de connexion.", Toast.LENGTH_SHORT).show()
                TestConnexion()
            }
        }
    }

    fun TestConnexion(){
        // On test la connexion avec une requête vers le serveur.
        LancementRequeteTestConnexion()
    }


    fun ResultatTestConnexion(resultat : Boolean){
        if(resultat){
            Log.e("Resultat Test Connexion","On à accès à Internet")
            testConnexion = true
            Log.e("ResultatTestConnexion", "Il y a une connexion, on demande les sondages, questions, sous-questions et choix dans le serveur.")
            ReceptionSondagesPublics()
            ReceptionCategories()

            /*
            if(savedInstanceState != null && savedInstanceState!!.javaClass == Bundle::class.java) {
                val sondageId = savedInstanceState!!.getInt(TAG_ID_SONDAGE)
                launch{
                    var listeSondagesTempos : Array<Sondage>?
                    listeSondagesTempos = db.myDAO().loadOneSondageFromSondageId(sondageId)
                    if(listeSondagesTempos != null && listeSondagesTempos.size != 0 && listeSondagesTempos != null){
                        sondage = listeSondagesTempos.get(0)
                    }
                    positionSpinner = savedInstanceState!!.getInt(TAG_POSITION_SPINNER)

                    var listeSondagesDisponibles = db.myDAO().loadAllSondages()
                    Log.e("testestest", "ajout dans liste nmom sondages                     :            " + listeSondagesDisponibles.size)
                    listeSondagesDisponibles.forEach {
                        Log.e("testestest", "ajout dans liste nmom sondages dans boucle")
                        nomSondagesDisponibles.add(it.intituleSondage + "  :  " + it.etat)
                        sondagesDisponibles.add(it)
                    }

                    spinner!!.setSelection(positionSpinner!!)
                }
//                RequeteSondageVoulu(sondageId)
//                RecuperationQuestionsDuSondage(sondageId.toString())
//                RequeteEnvoieReponsesEnregistrer()
                debutTestConnexion = false
            }

             */
        } else {
            testConnexion = false
            debutTestConnexion = false
            Log.e("Resultat Test Connexion","On n'à pas accès à Internet, on prend les sondages dans la BD.")
            RecuperationCategoriesDeBDPerso()
        }
    }




    fun InitialisationSansConnexion(){

    }



    fun RechargementbaseDeDonnees(){
        //Remet à jour la BDD
        launch {
            Log.e("passage passage", "passage passage")
//            db.myDAO().deleteAllQuestions()
//            db.myDAO().deleteAllReponses()
//            db.myDAO().deleteAllSondages()
//            db.myDAO().deleteAllchoix()
            RequeteTousLesSondages()
        }
    }


    fun VerificationSondageDeCategorieAutorisee(sondageTemporaire: Sondage): Boolean{
        for(categorie in listeCategories){
            if(categorie.id_categorie == sondageTemporaire.categorie_id && categorie.active){
                return true
            }
        }
        return false
    }

    //Ajoute au spinner tous les noms de sondages disponibles dans la base de données.
    fun InitialisationListeSondagesDisponibles(){
        launch{
//            var listeNomsSondagesDisponibles = db.myDAO().loadAllNomsSondages()
            nomSondagesDisponibles = ArrayList()
            sondagesDisponibles = ArrayList()
            nomSondagesDisponibles.add("Choissisez un sondage")
            var listeSondagesDisponibles = db.myDAO().loadAllSondages()
            Log.e("plop", "plop    :" + listeSondagesDisponibles.size)
            listeSondagesDisponibles.forEach {
                if(VerificationSondageDeCategorieAutorisee(it)){
                    nomSondagesDisponibles.add(it.intituleSondage + "  :  " + it.etat)
                    sondagesDisponibles.add(it)
                }

            }
            InitialisationValeurs()


//            spinner!!.setSelection(positionSpinner!!)

//            RecapBDD()
//            getReponses()


            /*
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
            }*/

        }
    }


    fun RecuperationCategoriesDeBDPerso(){
        launch{
            withContext(Dispatchers.IO){
                listeCategories = ArrayList()
                var listeCategoriesTemporaire = db.myDAO().loadAllCategories()
                listeCategories.addAll(listeCategoriesTemporaire)
                InitialisationListeSondagesDisponibles()
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
            if(savedInstanceStateBundle != null && savedInstanceStateBundle!!.javaClass == Bundle::class.java) {
                val listeReponses = savedInstanceStateBundle!!.getStringArrayList(TAG_LISTE_REPONSES)
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



    fun GettingSondageFromBDD(sondageId: Int){
        launch {
            sondage = getSondageFromSondageID(sondageId)
            if(sondage != null){
                Log.e("Passage pas co","passage pas co")
                listeQuestions = ArrayList()
                listeSousQuestions = ArrayList()
                var questions = getQuestionFromSondageID(sondage!!.id_sondage)
                for(question in questions){
                    if(question.idQuestionDeGroupe != null){
                        listeSousQuestions.add(question)
                    } else {
                        listeQuestions.add(question)
                    }
                }
                Log.e("ploploplop", "liste nombre       : " + listeSousQuestions.size)
                gettingChoixPourSondage()
            } else {
                Log.e("test sondage", "NULL NULL NULL   : " + sondageId)
            }
        }
    }



    fun affichageQuestion(){
        if(positionSpinner != 0) {
            var question = listeQuestions.get(indiceQuestionAfficher)
            if (question.type.equals("GroupeQuestion")) {
                LancementQuestionGroupe(question)
            } else if (question.type.equals("QuestionOuverte")) {
                LancementQuestionTexteLibre(question, linearLayout!!, null)
            } else if (question.type.equals("QuestionChoix")) {
                LancementChoix(question, linearLayout!!, null)
            } else if (question.type.equals("QuestionPoint")) {
                LancementQuestionAPoints(question, linearLayout!!, null)
            }
        }
    }

    suspend fun getQuestionFromSondageID(sondageId: Int): Array<Question>{
        val questions = db.myDAO().loadQuestionsFromSondageID(sondageId)
        return questions
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
            if(question.type == "QuestionChoix" && ligneReponses.contains("true") && ligneReponses.substring(0, ligneReponses.indexOf(";")).toInt() == question.id_question){
                return ligneReponses.substring(ligneReponses.indexOf(";")+1, ligneReponses.length)
            } else if(question.type == "QuestionOuverte" && ligneReponses.length > 0 && ligneReponses.substring(0, ligneReponses.indexOf(";")).toInt() == question.id_question){
                Log.e("reponseTempo : ", "reponse temp    " + ligneReponses + "   " + question.id_question)
                return ligneReponses.substring(ligneReponses.indexOf(";")+1, ligneReponses.length)
            } else if(question.type == "QuestionPoint" && ligneReponses.length > 0 && ligneReponses.substring(0, ligneReponses.indexOf(";")).toInt() == question.id_question) {
                Log.e(
                    "reponseTempo : ",
                    "reponse temp    " + ligneReponses + "   " + question.id_question
                )
                return ligneReponses.substring(ligneReponses.indexOf(";") + 1, ligneReponses.length)
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
        if(question.estObligatoire){
            Ajout_TextView_Question(linearLayoutHorizontal, question.intitule + "\n***Obligatoire***",
                question.ordre)
        } else {
            Ajout_TextView_Question(linearLayoutHorizontal, question.intitule,
                question.ordre)
        }


        return linearLayoutHorizontal
    }


    fun LancementChoix(question: Question, linearLayoutBase: LinearLayout, reponseGroupe: String?){
        Log.e("Passage lanc choix", "On y pase   :   " + listeChoix.size)
        var listeStringTexteChoix = ArrayList<String>()
        for(choix in listeChoix){
            if(choix.question_id == question.id_question){
                listeStringTexteChoix.add(choix.intituleChoix)
            }
        }

        var reponse = RechercheQuestionIdDansListeReponsesTemporaire(question)
        if(reponse.equals("") && reponseGroupe != null){
            reponse = reponseGroupe
        }


        if(question.type.equals("QuestionChoix") && question.estUnique != null && question.estUnique){
            LancementQuestionChoixUnique(question, listeStringTexteChoix, linearLayoutBase, reponse)
        } else if(question.type.equals("QuestionChoix") && question.estUnique != null){
            LancementQuestionChoixMultiple(question, listeStringTexteChoix, linearLayoutBase, reponse)
        }
    }

    private fun LancementQuestionChoixMultiple(question: Question, listeReponsesPossibles : ArrayList<String>, linearLayoutBase: LinearLayout, reponseGlobal: String) {

        val linearLayoutHorizontal = LancementGenerique(question, linearLayoutBase)


        // Créé ce qu'il faut pour la réponse.
        Ajout_QCM_reponses(question, linearLayoutHorizontal, listeReponsesPossibles, reponseGlobal)
    }


    private fun LancementQuestionChoixUnique(question : Question, listeReponsesPossibles : ArrayList<String>, linearLayoutBase: LinearLayout, reponseGlobal: String) {

        val linearLayoutHorizontal = LancementGenerique(question, linearLayoutBase)

        // Créé ce qu'il faut pour la réponse.
        Ajout_QCU_reponses(question, linearLayoutHorizontal, listeReponsesPossibles, reponseGlobal)
    }


    private fun LancementQuestionAPoints(question: Question, linearLayoutBase: LinearLayout, reponseGroupe : String?) {

        val linearLayoutHorizontal = LancementGenerique(question, linearLayoutBase)

        val editText = EditText(this)
        editText.id = View.generateViewId()
        editText.setTag("Reponse_" + listeQuestions[indiceQuestionAfficher].id_question + "_" + 0)
        test++

        val reponse = RechercheQuestionIdDansListeReponsesTemporaire(question)
        if(!reponse.equals("")){
            Log.e("reponse temp  :" , "idjdoiffjidfj           :    " + reponse)
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
        editText.setTag("Reponse_" + listeQuestions[indiceQuestionAfficher].id_question + "_" + 0)

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
        if(question.estObligatoire){
            Ajout_TextView_Question(linearLayoutVertical, question.intitule + "\n***Obligatoire***", question.ordre)
        } else {
            Ajout_TextView_Question(linearLayoutVertical, question.intitule, question.ordre)
        }

        linearLayoutPourQuestionGroup = linearLayoutVertical

//        val reponse = RechercheQuestionIdDansListeReponsesTemporaire(question)
//        var listeReponsesTemporaires: List<String>? = null
//        var indiceReponse = 0
//        if(!reponse.equals("")){
//            listeReponsesTemporaires = reponse.split(";")
//        }

        val listeSousQuestionsTemporaire = ArrayList<Question>()
        for(sousQuestionTemporaire in listeSousQuestions){
            if(sousQuestionTemporaire.idQuestionDeGroupe == question.id_question){
                listeSousQuestionsTemporaire.add(sousQuestionTemporaire)
            }
        }
        for (sousQuestion in listeSousQuestionsTemporaire){
            var reponse = RechercheQuestionIdDansListeReponsesTemporaire(sousQuestion)
            if (reponse != "") {
                if (sousQuestion.type.equals("QuestionOuverte")) {
                    LancementQuestionTexteLibre(sousQuestion, linearLayoutVertical, reponse)
                } else if (sousQuestion.type.equals("QuestionChoix")) {
                    LancementChoix(sousQuestion, linearLayoutVertical, reponse)
                } else if (sousQuestion.type.equals("QuestionPoint")) {
                    LancementQuestionAPoints(sousQuestion, linearLayoutVertical, reponse)
                }
            } else {
                if (sousQuestion.type.equals("QuestionOuverte")) {
                    LancementQuestionTexteLibre(sousQuestion, linearLayoutVertical,null)
                } else if (sousQuestion.type.equals("QuestionChoix")) {
                    LancementChoix(sousQuestion, linearLayoutVertical, null)
                } else if (sousQuestion.type.equals("QuestionPoint")) {
                    LancementQuestionAPoints(sousQuestion, linearLayoutVertical,null)
                }
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

    fun Ajout_QCU_reponses(question: Question, linearLayoutParent: LinearLayout, reponsesPossibles: ArrayList<String>, reponseGlobal : String) {
        // On créé le radioGroup qui contiendra les différentes réponses possibles.
        val radioGroup = RadioGroup(this)
        // On donne une ID au radioGroup.
        radioGroup.id = View.generateViewId()
        // On ajoute le radioGroup à la nouvelle ligne du listView.
        val params = LinearLayout.LayoutParams(param)
        params.gravity = Gravity.CENTER_HORIZONTAL
        linearLayoutParent.addView(radioGroup, 1, LinearLayout.LayoutParams(params))
        radioGroupPourQuestionChoixUnique = radioGroup

        val listReponses = reponseGlobal.split(";")

        // Pour chaque réponse possible.
        for (i in reponsesPossibles.indices) {
            // On créé le radioButton qui correspond à la réponse possible.
            val radioButton = RadioButton(context)
            // On donne une ID au radioButton.
            radioButton.id = View.generateViewId()
            radioButton.text = reponsesPossibles[i]
            radioButton.tag = "Reponse_" + listeQuestions[indiceQuestionAfficher].id_question + "_" + i

            if(listReponses.size > i){
                radioButton.isChecked = listReponses[i].toBoolean()
            }

            test++
            //On ajoute le radioButton au radiogroup précédant.
            var indice = linearLayoutParent.childCount - 1
            (linearLayoutParent.getChildAt(indice) as RadioGroup).addView(radioButton, (linearLayoutParent.getChildAt(indice) as RadioGroup).childCount, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        }
    }

    fun Ajout_QCM_reponses(question: Question, linearLayoutParent: LinearLayout, reponsesPossibles: ArrayList<String>, reponseGlobal: String) {
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

        val listReponses = reponseGlobal.split(";")

        // Pour chaque réponse possible.
        for (i in reponsesPossibles.indices) {
            // On créé le radioButton qui correspond à la réponse possible.
            val checkBox = CheckBox(context)
            // On donne une ID au radioButton.
            checkBox.id = View.generateViewId()
            checkBox.text = reponsesPossibles[i]
            checkBox.tag = "Reponse_" + listeQuestions[indiceQuestionAfficher].id_question + "_" + i

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
        nomSondage = parent.getItemAtPosition(pos) as String
        sondage = SelectionSondageVoulueParNom(nomSondage)

        positionSpinner = pos
        for (i in linearLayout!!.childCount downTo 2) {
            linearLayout!!.removeViewAt(i - 1)
        }
        if (nomSondage != "Choissisez un sondage" && positionSpinner != 0) {
            linearLayoutPrecedentSuivant.visibility = View.VISIBLE
            linearLayoutValiderReinit.visibility = View.VISIBLE

            linearLayout!!.getChildAt(0).visibility = View.VISIBLE
            if(testConnexion){
//                RequeteSondageVoulu(sondage!!.id_sondage)
                RecuperationQuestionsDuSondage(sondage!!.id_sondage.toString())
//                RequeteChoixPourQuestions()

            } else {
                boutonSuivant!!.visibility = View.VISIBLE
                sondage = SelectionSondageVoulueParNom(nomSondage)
                if(sondage != null){
                    GettingSondageFromBDD(sondage!!.id_sondage)
                }
            }
        } else {
            linearLayoutPrecedentSuivant.visibility = View.INVISIBLE
            linearLayoutValiderReinit.visibility = View.INVISIBLE
            linearLayout!!.getChildAt(0).visibility = View.INVISIBLE
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        Log.e("Spinner  : ", "On a cliqué sur rien.")

    }

    fun ReinitialiserLayoutSondage(view: View) {
        //Reinit vues
        linearLayoutPourQuestionTexteLibre = null
        linearLayoutPourQuestionPoint = null
        linearLayoutPourQuestionChoixMultiples = null
        linearLayoutPourQuestionGroup = null
        radioGroupPourQuestionChoixUnique = null
        listLayoutQuestion = ArrayList()
        boutonPrecedent!!.visibility = View.INVISIBLE
        boutonSuivant!!.visibility = View.VISIBLE
        boutonValider!!.visibility = View.INVISIBLE
        //reinit reponses
        listeReponsesTemporaires = ArrayList()
        //reinit progression
        indiceQuestionAfficher = 0
        indiceQuestionMax = 0
        for (i in linearLayout!!.childCount downTo 2) {
            linearLayout!!.removeViewAt(i - 1)
        }
        if(sondage != null){
            affichageQuestion()
        }
    }


    fun validationSondage(view : View){
        var testReponseComplete = true
        for(question in listeQuestions){
            if(question.estObligatoire && !listeSousQuestions.contains(question) && (RechercheQuestionIdDansListeReponsesTemporaire(question) == "")){
                testReponseComplete = false
            }
        }
        var testSondageDisponibleOuRepondu = true
        if(sondage!!.etat.equals(EtatSondage.ENVOYE.toString())){
            testSondageDisponibleOuRepondu = false
        }
        var listesReponsesAEnregistrer: ArrayList<Reponse> = ArrayList()
        if(testReponseComplete && testSondageDisponibleOuRepondu) {
            listeReponsesEnProbation = ArrayList()
            Log.e("testreponse", "testreponse vrai vrai vrai")
            for(question in listeQuestions){
//                Log.e("validationSondage", "Etape 1       " + listeQuestions.size)
//                if(!listeSousQuestions.contains(question.id_question)){
//                    Log.e("validationSondage", "Etape 2       " + listeQuestions.size)
                    if(question.type == "QuestionGroupe") {
//                        Log.e("validationSondage", "Etape 3       " + listeQuestions.size)
                        val listeSousQuestionsId = question.numerosDeQuestionsGroupe!!.split(";")
                        for(sousQuestionId in listeSousQuestionsId){
//                            Log.e("validationSondage", "Etape 4       " + listeQuestions.size)
                            val sousQuestion = RechercheSousQuestionParId(sousQuestionId.toInt())
                            val reponseTemporaire = Reponse(
                                id_utilisateur = idUtilisateur!!,
                                id_sondage = sousQuestion!!.sondage_id,
                                question_id = sousQuestion.id_question,
                                reponse = RechercheQuestionIdDansListeReponsesTemporaire(sousQuestion!!),
                                etat = EtatReponse.A_REPONDRE.toString()
                            )
//                            Log.e("validationSondage", "Reponse à question groupe envoyé")
                            listeReponsesEnProbation.add(reponseTemporaire)
//                            if(testConnexion) {
//                                RequeteEnvoieReponse(reponseTemporaire)
//                            } else {
//                                listesReponsesAEnregistrer.add(reponseTemporaire)
//                            }
                        }
                    } else {
                        var reponseTemporaire : Reponse? = null
                        if(question.type == "QuestionChoix"){
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
                                id_utilisateur = idUtilisateur!!,
                                id_sondage = question.sondage_id,
                                question_id = question.id_question,
                                reponse = reponseString,
                                etat = EtatReponse.A_REPONDRE.toString()
                            )
                        } else {
                            reponseTemporaire = Reponse(
                                id_utilisateur = idUtilisateur!!,
                                id_sondage = question.sondage_id,
                                question_id = question.id_question,
                                etat = EtatReponse.A_REPONDRE.toString(),
                                reponse = RechercheQuestionIdDansListeReponsesTemporaire(question)
                            )
                        }
                        listeReponsesEnProbation.add(reponseTemporaire)
//
//                        if(testConnexion){
//                            RequeteEnvoieReponse(reponseTemporaire)
//                        } else {
//                            listesReponsesAEnregistrer.add(reponseTemporaire)
//                        }
                    }
//                }
            }
            Log.e("testpassageValidation", "test passage validation")
            launch {
                db.myDAO().insertAllReponses(listeReponsesEnProbation)
                TestConnexionPourEnvoieReponses()
            }

        } else if(!testReponseComplete){
            Toast.makeText(
                this,
                "Vous n'avez pas répondu à toutes les questions  obligatoires.",
                Toast.LENGTH_LONG
            ).show()
        } else if(!testSondageDisponibleOuRepondu){
            Toast.makeText(
                this,
                "Vous avez déjà répondu à ce sondage.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Fonction testant si la connexion est établie puis si oui envoie les réponses en probation et sinon les stocke dans la base de données comme réponses à envoyer.
     */
    fun TestConnexionPourEnvoieReponses(){
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.getTestConnexion()

        call_Post.enqueue(object : Callback<ModeleSuccessConnexion> {
            override fun onResponse(call : Call<ModeleSuccessConnexion>, response : Response<ModeleSuccessConnexion>){
                if (response.isSuccessful()) {
                    for(reponseEnProbation in listeReponsesEnProbation){
                        RequeteEnvoieReponse(reponseEnProbation)
                    }
                    boutonValider!!.visibility = View.INVISIBLE
                    ChangementEtatSondage(sondage!!, EtatSondage.ENVOYE)

                } else {
                    //Affiche le code de la reponse, soit le code http de la requête.
                    Log.e("blah", "Status code : " + response.raw() + response.code()/*+ response.code()*/)
                }
                testConnexion = true
            }
            @Override
            override fun onFailure(call : Call<ModeleSuccessConnexion>, t : Throwable ) {
                testConnexion = false
                if(sondage!!.etat.equals(EtatSondage.DISPONIBLE.toString())){
                    for(reponseEnProbation in listeReponsesEnProbation){
                        EnregistrementReponses(reponseEnProbation)
                    }
                    Toast.makeText(context, "Réponses enregistrées car il n'y a pas de connexion.", Toast.LENGTH_LONG).show()
                    ChangementEtatSondage(sondage!!, EtatSondage.REPONDU)
                } else if(sondage!!.etat.equals(EtatSondage.REPONDU.toString())){
                    Toast.makeText(context, "Réponses modifiées car il y a avait déjà des réponses d'enregistrées.", Toast.LENGTH_LONG).show()
                    launch {
                        db.myDAO().deleteReponsesDeSondage(sondage!!.id_sondage)
                        for (reponseEnProbation in listeReponsesEnProbation) {
                            EnregistrementReponses(reponseEnProbation)
                        }
                    }
                } else if(sondage!!.etat.equals(EtatSondage.ENVOYE.toString())){
                    Toast.makeText(context, "Vous avez déjà répondu au sondage.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("SauvegardeReponses", "Etat du sondage inconnue.")
                }
            }
        })
    }

    fun ChangementEtatSondage(sondage: Sondage, etatSondage: EtatSondage){
        launch {
                Log.e("tespassagechangetat", "test passage changement etat")
                db.myDAO().updateSondage(sondage.id_sondage, etatSondage.toString())
                for (sondageTempo in sondagesDisponibles) {
                    if ((sondageTempo.intituleSondage + "  :  " + sondageTempo.etat).equals(sondage.intituleSondage + "  :  " + sondage.etat)) {
                        sondageTempo.etat = etatSondage.toString()
                    }
                }
                for (nomSondageTempo in nomSondagesDisponibles) {
                    if (nomSondageTempo.split("  :  ").get(0).equals(sondage.intituleSondage)) {
                        Log.e("PassageDansBoucle", "Passage dans boucle")
                        val index = nomSondagesDisponibles.indexOf(nomSondageTempo)
                        nomSondagesDisponibles.set(
                            index,
                            sondage.intituleSondage + "  :  " + etatSondage.toString()
                        )
                    }
                }
                sondage.etat = etatSondage.toString()

                spinnerAdapter.notifyDataSetChanged()
//            spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, nomSondagesDisponibles)
        }
    }

    fun EnregistrementReponses(reponse: Reponse){
        launch {
            db.myDAO().updateReponse(reponse.id_reponse, EtatReponse.ENREGISTRER.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext[Job]!!.cancel()
        unregisterReceiver(networkChangeReceiver)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(linearLayout != null) {
            for (i in linearLayout!!.childCount downTo 2) {
                linearLayout!!.removeViewAt(i - 1)
            }
        }
        if(sondage != null){
            var testInt = spinner!!.selectedItemPosition
            outState.putInt(TAG_POSITION_SPINNER, testInt)
            outState.putInt(TAG_ID_SONDAGE,sondage!!.id_sondage)
            outState.putStringArrayList(TAG_LISTE_REPONSES, listeReponsesTemporaires)
            outState.putInt(TAG_INDICE_QUESTION_MAX, indiceQuestionMax)
            outState.putInt(TAG_INDICE_QUESTION_A_AFFICHER, indiceQuestionAfficher)
        } else {
            outState.putInt(TAG_ID_SONDAGE, -1)
        }
        if(idUtilisateur != null) {
            Log.e("LOGLOG", "Ajout util" + idUtilisateur)
            outState.putInt(TAG_UTILISATEUR, idUtilisateur!!)
        }

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
    }

    fun RecuperationQuestionsDuSondage(id_sondage : String){
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.getQuestionsDuSondage(id_sondage)
        call_Post.enqueue(object : Callback<ArrayList<Question>> {
            override fun onResponse(call : Call<ArrayList<Question>>, response : Response<ArrayList<Question>>) {
                if (response.isSuccessful()) {
                    var arrayList : ArrayList<Question> = ArrayList()
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
                    listeQuestions = ArrayList()
                    listeSousQuestions = ArrayList()
                    for(question in arrayList) {
                        if(question.idQuestionDeGroupe != null){
                            listeSousQuestions.add(question)
                        } else {
                            listeQuestions.add(question)
                        }                    }
                    RecuperationSousQuestionsDeSondage()
//                    affichageQuestions()
                    //Met à jour l'affichage.
                } else {
                    //Affiche le code de la reponse, soit le code http de la requête.
                    Log.e("blah", "Status code : " + response.raw() + "  " + response.code()/*+ response.code()*/)
                    GettingSondageFromBDD(id_sondage.toInt())
                }
            }
            override fun onFailure(call : Call<ArrayList<Question>>, t : Throwable ) {
                Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + t.localizedMessage)
                testConnexion = false
                GettingSondageFromBDD(id_sondage.toInt())
            }
        })
    }

    fun RecuperationSousQuestionsDeSondage(){
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        for(question in listeQuestions){
            if(question.type.equals("GroupeQuestion")) {
                val call_Post = serverApiService.getSousQuestionsDeQuestionGroupe(question.id_question.toString())
                call_Post.enqueue(object : Callback<ArrayList<Question>> {
                    override fun onResponse(call: Call<ArrayList<Question>>, response: Response<ArrayList<Question>>) {
                        if (response.isSuccessful()) {
                            var listeSousQuestionsTemporaires = response.body()
                            if (listeSousQuestionsTemporaires != null) {
                                for (sousQuestion in listeSousQuestionsTemporaires) {
                                    sousQuestion.idQuestionDeGroupe = question.id_question
                                    listeSousQuestions.add(sousQuestion)
                                }
                            }
                            // On demande les choix pour les questions du sondage.
                            RequeteChoixParQuestionsPourSondage()
                        } else {
                            //Affiche le code de la reponse, soit le code http de la requête.
                            Log.e(
                                "blah",
                                "Status code : " + response.raw() + "  " + response.code()/*+ response.code()*/
                            )
                            if (sondage != null) {
                                GettingSondageFromBDD(sondage!!.id_sondage)
                            }
                        }
                    }
                    override fun onFailure(call: Call<ArrayList<Question>>, t: Throwable) {
                        Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + t.localizedMessage)
                        testConnexion = false
                        if (sondage != null) {
                            GettingSondageFromBDD(sondage!!.id_sondage)
                        }
                    }
                })
            }
        }
    }

    fun Transformation_ModeleChoix_Choix(modeleChoix: ModeleChoix): Choix{
        return Choix(modeleChoix.id_choix, modeleChoix.intituleChoix, modeleChoix.question_id)
    }

    fun LancementRequeteTestConnexion(){
        Log.e("RequeteTestConnexion", "On test si il y a une connexion avec le serveur.")
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.getTestConnexion()
        call_Post.enqueue(object : Callback<ModeleSuccessConnexion> {
            override fun onResponse(call : Call<ModeleSuccessConnexion>, response : Response<ModeleSuccessConnexion>){
                if (response.isSuccessful()) {
                    val successConnexion = response.body() as ModeleSuccessConnexion
                    Toast.makeText(context, "Résultat du test de connexion : " + successConnexion.message + "  " + successConnexion.status, Toast.LENGTH_SHORT).show()
                    testConnexion = true
                    ResultatTestConnexion(true)
                } else {
                    val successConnexion = response.body() as ModeleSuccessConnexion
                    //Affiche le code de la reponse, soit le code http de la requête.
                    Toast.makeText(context, "Résultat du test de connexion : impossible de se connecter.", Toast.LENGTH_LONG).show()
                    testConnexion = true
                    ResultatTestConnexion(true)
                }
            }
            @Override
            override fun onFailure(call : Call<ModeleSuccessConnexion>, t : Throwable ) {
                Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + t.localizedMessage)
                Toast.makeText(context, "Résultat du test de connexion : impossible de se connecter.", Toast.LENGTH_LONG).show()
                testConnexion = false
                ResultatTestConnexion(false)
            }
        })
    }

    fun ReceptionSondagesPublics(){
//        sondagesDisponibles = ArrayList()
//        nomSondagesDisponibles = ArrayList()
//        nomSondagesDisponibles.add("Choissisez un sondage")
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.getAllSondages()
//        Log.e("tets", "ted-tddt")
        call_Post.enqueue(object : Callback<ArrayList<Sondage>> {
            override fun onResponse(call : Call<ArrayList<Sondage>>, response : Response<ArrayList<Sondage>>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
//                    Log.e("TAG", "La personne a été enlevée")
//                    nomSondagesDisponibles = ArrayList()
                    val allSondages = response.body()

//                    sondagesDisponibles.clear()
//                    nomSondagesDisponibles.clear()
//                    nomSondagesDisponibles.add("Choissisez un sondage")
                    if (allSondages != null) {
                        listeTotaleSondages = allSondages

                        AjoutSondagesDansBDDPerso()

//                        spinner!!.setSelection(positionSpinner!!)
                        Log.e("jjjjjjjjjjjjjj", "                             : " + positionSpinner)
                    }
                    //Met à jour l'affichage.
                } else {
                    //Affiche le code de la reponse, soit le code http de la requête.
                    InitialisationListeSondagesDisponibles()
                    Log.e("blah", "Status code : " + response.raw() + response.code()/*+ response.code()*/)
                }
            }
            override fun onFailure(call : Call<ArrayList<Sondage>>, t : Throwable ) {
                Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + t.localizedMessage)
                testConnexion = false
                InitialisationListeSondagesDisponibles()
//                ResultatTestConnexion(false)
            }
        })
    }

    fun ReceptionCategories(){
        launch {
            listeCategories = ArrayList()
            listeCategories.addAll(db.myDAO().loadAllCategories())
            var serverApiService = ServiceGenerator().createService(
                ServerApiService::class.java,
                tokenAuthentification
            )
            val call_Post = serverApiService.getAllCategories()
            call_Post.enqueue(object : Callback<ArrayList<Categorie>> {
                override fun onResponse(
                    call: Call<ArrayList<Categorie>>,
                    response: Response<ArrayList<Categorie>>
                ) {
                    //Test si la requête a réussi ( code http allant de 200 à 299).
                    if (response.isSuccessful()) {
                        var listeCategoriesTemporaireDB = listeCategories
                        listeCategories = ArrayList()
                        val listeCategoriesTemporaire = response.body()
                        if (listeCategoriesTemporaire != null) {
                            for(i in 0..listeCategoriesTemporaire.size-1){
                                if (listeCategoriesTemporaireDB.size > i) {
                                    listeCategories.add(
                                        VerificationMemeCategorie(
                                            listeCategoriesTemporaireDB[i],
                                            listeCategoriesTemporaire[i]
                                        )
                                    )
                                } else {
                                    listeCategories.add(listeCategoriesTemporaire[i])
                                }
                            }
                            AjoutAllCategoriesDansBDPerso()
                        }
                    } else {
                        //Affiche le code de la reponse, soit le code http de la requête.
                        Log.e(
                            "blah",
                            "Status code : " + response.raw() + response.code()/*+ response.code()*/
                        )
                    }
                }
                override fun onFailure(call: Call<ArrayList<Categorie>>, t: Throwable) {
                    Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + t.localizedMessage)
                    testConnexion = false
                }
            })
        }
    }

    fun AjoutAllCategoriesDansBDPerso(){
        launch {
            withContext(Dispatchers.IO){
               db.myDAO().insertAllCategories(listeCategories)
            }
        }
    }

    fun PassageQuestionPrecedente(view: View) {
        affichageListeReponsesTemporaires()
        Log.e("PassageQuestPrec", "test indice : " + indiceQuestionAfficher.toString())
//        if(indiceQuestionAfficher > -1) {
//            var deplacement = 1
        for (i in linearLayout!!.childCount downTo 2) {
            linearLayout!!.removeViewAt(i - 1)
        }
//            while (ObtenirIndiceSousQuestionAvecSonId(indiceQuestionAfficher - deplacement + 1) != -1) {
//                deplacement++
//            }
        sauvegardeReponsesAvantPassage()
        // Si on était à la dernière question, on affiche le bouton suivant et on chache le bouton valider.
        if (indiceQuestionAfficher == listeQuestions.size - 1) {
            boutonSuivant!!.visibility = View.VISIBLE
            boutonValider!!.visibility = View.INVISIBLE
        }
        // On se déplace
        indiceQuestionAfficher = indiceQuestionAfficher - 1// - deplacement
        if (indiceQuestionAfficher == 0) {
            boutonPrecedent!!.visibility = View.INVISIBLE
        }
        affichageQuestion()
    }

    fun PassageQuestionSuivante(view: View) {
        affichageListeReponsesTemporaires()
        Log.e("PassageQuestSuiv","test indice : " + indiceQuestionAfficher.toString())
        Log.e("PassageQuestSuiv","test indice fin : " + listeQuestions.size.toString())
//        if(indiceQuestionAfficher < listeQuestions.size-1) {
//            var deplacement = 1
        for (i in linearLayout!!.childCount downTo 2) {
            linearLayout!!.removeViewAt(i - 1)
        }

//            while (ObtenirIndiceSousQuestionAvecSonId(indiceQuestionAfficher - deplacement + 1) != -1) {
//                    deplacement++
//            }

        sauvegardeReponsesAvantPassage()

//            if(deplacement > 0 && indiceQuestionAfficher == indiceQuestionMax){
//                for(indice in 1 .. deplacement){
//                    listeReponsesTemporaires.add("")
//                }
//                indiceQuestionMax = indiceQuestionMax// + deplacement
//            }
            //Si la question que l'on quitte était la première, on affiche le bouton précédent.
        if(indiceQuestionAfficher == 0){
            boutonPrecedent!!.visibility = View.VISIBLE
        }
        if(indiceQuestionAfficher == indiceQuestionMax){
            indiceQuestionMax++
        }
        //On se déplace
        indiceQuestionAfficher = indiceQuestionAfficher + 1// + deplacement
        // Si le bouton est maintenant le dernier, on cache le bouton suivant et on affiche le bouton valider.
        if(indiceQuestionAfficher == listeQuestions.size-1){
            boutonSuivant!!.visibility = View.INVISIBLE
            boutonValider!!.visibility = View.VISIBLE
//               if(sondage != null && sondage!!.etat.equals("")){
//                    sondage!!.etat = EtatSondage.DISPONIBLE.toString()
//                }
//                if(sondage!!.etat.equals(EtatSondage.DISPONIBLE.toString()) || sondage!!.etat.equals(EtatSondage.REPONDU.toString())) {
//                    boutonValider!!.visibility = View.VISIBLE
//                }
        }

        affichageQuestion()
//       }
    }


    fun sauvegardeReponsesAvantPassage(){
        Log.e("sauvegardeAVP","test indice : " + indiceQuestionAfficher.toString() + "    " + indiceQuestionMax.toString() + "   " + listeReponsesTemporaires.size)
        val questionTemporaire = listeQuestions.get(indiceQuestionAfficher)
        if(indiceQuestionAfficher == indiceQuestionMax && RechercheQuestionIdDansListeReponsesTemporairePourIndice(questionTemporaire) == -1){
            Log.e("SauvegardeReponsestempo", "Afficher==Max")
            val question = listeQuestions.get(indiceQuestionAfficher)
            if(question.type.equals("GroupeQuestion")){
                var reponses = ""
                var indice = 1
                val listeSousQuestionsTemporaire = ArrayList<Question>()
                for(sousQuestion in listeSousQuestions){
                    if(sousQuestion.idQuestionDeGroupe == question.id_question){
                        listeSousQuestionsTemporaire.add(sousQuestion)
                    }
                }
                for(sousQuestionTemporaire in listeSousQuestionsTemporaire){
                    if(sousQuestionTemporaire.type == "QuestionOuverte"){
                        val linearLayoutTemporaire = linearLayoutPourQuestionGroup!!.getChildAt(indice) as LinearLayout
                        val view = linearLayoutTemporaire.getChildAt(1) as EditText
                        listeReponsesTemporaires.add(sousQuestionTemporaire.id_question.toString() + ";" + view.text.toString())
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
            } else if(question.type.equals("QuestionOuverte") && linearLayoutPourQuestionTexteLibre != null && linearLayoutPourQuestionTexteLibre!!.childCount == 2){
                val viewTest = linearLayoutPourQuestionTexteLibre!!.getChildAt(1) as EditText
                listeReponsesTemporaires.add(question.id_question.toString() + ";" + viewTest.text.toString())
            } else if(question.type.equals("QuestionChoix")){
                if(question.estUnique != null && question.estUnique && radioGroupPourQuestionChoixUnique != null){
                    var reponse = question.id_question.toString()
                    for(i in 0 until radioGroupPourQuestionChoixUnique!!.childCount){
                        val view = radioGroupPourQuestionChoixUnique!!.getChildAt(i) as RadioButton
                        reponse = reponse.plus(";" + view.isChecked.toString())
                    }
                    listeReponsesTemporaires.add(reponse)
                } else if(question.estUnique != null && linearLayoutPourQuestionChoixMultiples != null) {
                    var reponse = question.id_question.toString()
                    for(i in 0 until linearLayoutPourQuestionChoixMultiples!!.childCount){
                        val view = linearLayoutPourQuestionChoixMultiples!!.getChildAt(i) as CheckBox
                        reponse = reponse.plus(";" + view.isChecked.toString())
                    }
                    listeReponsesTemporaires.add(reponse)
                }
//                RequeteChoixPourQuestionEtSondage(question, null, 1)
            } else if(question.type.equals("QuestionPoint") && linearLayoutPourQuestionPoint != null && linearLayoutPourQuestionPoint!!.childCount == 2){
                val viewTest = linearLayoutPourQuestionPoint!!.getChildAt(1) as EditText
                listeReponsesTemporaires.add(question.id_question.toString() + ";" + viewTest.text.toString())
            }
        } else {
            Log.e("SauvegardeReponsestempo", "Afficher!=Max")
            val question = listeQuestions.get(indiceQuestionAfficher)
            val indiceDansListeReponses = RechercheQuestionIdDansListeReponsesTemporairePourIndice(question)
            if(question.type.equals("GroupeQuestion")){
                var reponses = ""
                var indice = 1



                val listeSousQuestionsTemporaire = ArrayList<Question>()
                for(sousQuestion in listeSousQuestions){
                    if(sousQuestion.idQuestionDeGroupe == question.id_question){
                        listeSousQuestionsTemporaire.add(sousQuestion)
                    }
                }
                for(sousQuestionTemporaire in listeSousQuestionsTemporaire){
                    val indiceSousQuestionDansListeReponses = RechercheQuestionIdDansListeReponsesTemporairePourIndice(sousQuestionTemporaire)
                    if(sousQuestionTemporaire != null && sousQuestionTemporaire.type == "QuestionOuverte"){
                        val linearLayoutTemporaire =
                            linearLayoutPourQuestionGroup!!.getChildAt(indice) as LinearLayout
                        val view = linearLayoutTemporaire.getChildAt(1) as EditText
                        listeReponsesTemporaires.set(indiceDansListeReponses + indice - listeSousQuestionsTemporaire.size-1,sousQuestionTemporaire.id_question.toString() + ";" + view.text.toString())
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
            } else if(question.type.equals("QuestionOuverte") && linearLayoutPourQuestionTexteLibre != null && linearLayoutPourQuestionTexteLibre!!.childCount == 2){
                val viewTest = linearLayoutPourQuestionTexteLibre!!.getChildAt(1) as EditText
                listeReponsesTemporaires.set(indiceDansListeReponses, question.id_question.toString() + ";" + viewTest.text.toString())
            } else if(question.type.equals("QuestionChoix")){
                if(question.estUnique != null && question.estUnique && radioGroupPourQuestionChoixUnique != null){
                    var reponse = question.id_question.toString()
                    for(i in 0 until radioGroupPourQuestionChoixUnique!!.childCount){
                        val view = radioGroupPourQuestionChoixUnique!!.getChildAt(i) as RadioButton
                        reponse = reponse.plus(";" + view.isChecked.toString())
                    }
                    listeReponsesTemporaires.set(indiceDansListeReponses, reponse)
                } else if(question.estUnique != null && linearLayoutPourQuestionChoixMultiples != null) {
                    var reponse = question.id_question.toString()
                    for(i in 0 until linearLayoutPourQuestionChoixMultiples!!.childCount){
                        val view = linearLayoutPourQuestionChoixMultiples!!.getChildAt(i) as CheckBox
                        reponse = reponse.plus(";" + view.isChecked.toString())
                    }
                    listeReponsesTemporaires.set(indiceDansListeReponses, reponse)
                }
//                RequeteChoixPourQuestionEtSondage(question, null, 1)
            } else if(question.type.equals("QuestionPoint") && linearLayoutPourQuestionPoint != null && linearLayoutPourQuestionPoint!!.childCount == 2){
                val viewTest = linearLayoutPourQuestionPoint!!.getChildAt(1) as EditText
                listeReponsesTemporaires.set(indiceDansListeReponses, question.id_question.toString() + ";" + viewTest.text.toString())
            }
        }
    }






    fun RequeteChoixPourQuestions(){

        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.getChoixParSondage(sondage!!.id_sondage.toString())


        call_Post.enqueue(object : Callback<ArrayList<Choix>> {
            override fun onResponse(call : Call<ArrayList<Choix>>, response : Response<ArrayList<Choix>>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
//                    Log.e("TAG", "La personne a été enlevée")
                    val allChoix = response.body()
                    if (allChoix != null) {
                        for (choix in allChoix)
                            listeChoix.add(choix)
                    }
                }
            }
            override fun onFailure(call : Call<ArrayList<Choix>>, t : Throwable ) {
                Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + t.localizedMessage)
                testConnexion = false

            }
        })
    }

    fun RequeteChoixParQuestionsPourSondage(){
        listeChoix = ArrayList()
        val indiceDerniereQuestionChoix = VerificationDerniereQuestionChoix()
        val derniereQuestionChoix = listeQuestions[indiceDerniereQuestionChoix]
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        for(question in listeQuestions){
            if(question.type.equals("QuestionChoix")){
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
                            if(question.id_question == derniereQuestionChoix.id_question) {
                                if (listeQuestions.size - 1 == indiceQuestionAfficher) {
                                    // Si la question à afficher est la dernière, on cache le bouton suivant et on affiche le bouton valider.
                                    boutonSuivant!!.visibility = View.INVISIBLE
                                    boutonValider!!.visibility = View.VISIBLE
                                } else {
                                    // Si la question n'est pas la dernière, on affiche le bouton suivant et on cache le bouton valider.
                                    boutonSuivant!!.visibility = View.VISIBLE
                                    boutonValider!!.visibility = View.INVISIBLE
                                }
                                if (0 == indiceQuestionAfficher) {
                                    // Si la question est la première, on cache le bouton précédent.
                                    boutonPrecedent!!.visibility = View.INVISIBLE
                                } else {
                                    // Si la question n'est pas la première, on affiche le bouton précédent.
                                    boutonPrecedent!!.visibility = View.VISIBLE
                                }
                                affichageQuestion()
                            }
                        } else {
                            if(sondage != null){
                                GettingSondageFromBDD(sondage!!.id_sondage)
                            }
                        }
                    }
                    override fun onFailure(call : Call<List<ModeleChoix>>, t : Throwable ) {
                        Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + t.localizedMessage)
                        testConnexion = false
                        if(sondage != null){
                            GettingSondageFromBDD(sondage!!.id_sondage)
                        }
                    }
                })
            }
        }
    }


    fun RequeteSondageVoulu(sondage_id: Int){
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.getSondage(sondage_id.toString())


        call_Post.enqueue(object : Callback<Sondage> {
            override fun onResponse(call : Call<Sondage>, response : Response<Sondage>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
                    val sondageVoulu = response.body()
                    if (sondageVoulu != null) {
                        sondage = sondageVoulu
                    }
                }
            }
            override fun onFailure(call : Call<Sondage>, t : Throwable ) {
                Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + t.localizedMessage)
                testConnexion = false
                launch {
                    sondage = getSondageFromSondageID(sondage_id)
                }
            }
        })
    }


    fun SelectionChoixPourReponses(question: Question): Choix?{
        for(choix in listeChoix){
        }
        return null
    }

    fun SelectionSondageVoulueParNom(nomsondage: String): Sondage?{
        for(sondage in sondagesDisponibles){
            if((sondage.intituleSondage).equals(nomsondage.split("  :  ").get(0))){
                return sondage
            }
        }
        return null
    }



    fun VerificationDerniereQuestionChoix(): Int{
        var indiceDerniereQuestionChoix = -1
        for(question in listeQuestions){
            if(question.type.equals("QuestionChoix")){
                indiceDerniereQuestionChoix = listeQuestions.indexOf(question)
            }
        }
        return indiceDerniereQuestionChoix
    }


    fun Changement_Etat_Reponse_Dans_BDD(reponse : Reponse, etatReponse: EtatReponse){
        launch {
            db.myDAO().updateReponse(reponse.id_reponse, etatReponse.toString())
        }
    }


    fun RequeteEnvoieReponse(reponse: Reponse){
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.EnvoieReponse(reponse.id_utilisateur, reponse.id_sondage, reponse.question_id, reponse.reponse)

        Log.e("RequeteEnvoieReponse", "La reponse a envoyé : " + reponse.question_id + "  " + reponse.id_utilisateur + " "+ reponse.reponse)

        sondage!!.etat = EtatSondage.ENVOYE.toString()


        call_Post.enqueue(object : Callback<Reponse> {
            override fun onResponse(call : Call<Reponse>, response : Response<Reponse>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
                    val reponseRecu = response.body()
                    if (reponseRecu != null) {
                        Log.e("RequeteEnvoieReponse: ", reponseRecu.id_utilisateur.toString() + "  " + reponseRecu.id_sondage.toString()
                                + "  " + reponseRecu.question_id.toString() + "  " + reponseRecu.reponse)
                        Changement_Etat_Reponse_Dans_BDD(reponse, EtatReponse.ENVOYER)
                        if(listeReponsesEnProbation.indexOf(reponse) == listeReponsesEnProbation.size-1){
                            Toast.makeText(context, "Les réponses ont été envoyés", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Changement_Etat_Reponse_Dans_BDD(reponse, EtatReponse.ENREGISTRER)
                    }
                }
            }
            override fun onFailure(call : Call<Reponse>, t : Throwable ) {
                Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + t.localizedMessage)
                testConnexion = false
                Changement_Etat_Reponse_Dans_BDD(reponse, EtatReponse.ENREGISTRER)
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

    fun RequeteTousLesSondages(){
        Log.e("RequeteTousLesSondages", "On passe dans requeteTousLesSondages")
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.getAllSondages()

        call_Post.enqueue(object : Callback<ArrayList<Sondage>> {
            override fun onResponse(call : Call<ArrayList<Sondage>>, response : Response<ArrayList<Sondage>>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
                    val sondages = response.body()
                    val sondagesArrayList = ArrayList<Sondage>()
                    if (sondages != null) {
                        Log.e("verif sondage : ", sondages.size.toString() + "     " + sondages.get(0).toString())
                        listeTotaleSondages = sondages
                        /*
                        for(sondage in sondages){
                            sondagesArrayList.add(sondage)
                        }
                        */
                        AjoutSondagesDansBDDPerso()
                    } else {
                        Log.e("RequeteTousLesSondages", "La reponse recu est null")
                    }
                }
            }
            override fun onFailure(call : Call<ArrayList<Sondage>>, t : Throwable ) {
                //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                Log.e("RequeteTousLesSondages", "Erreur de connexion" + t.localizedMessage)
//              ServiceGenerator.Message(this, "blah", t)
                testConnexion = false
            }
        })
    }

    fun DeleteChoixEtQuestionDeSondageDeBDDPerso(sondageId: Int){
        launch {
            val listeQuestionsDeSondage = db.myDAO().loadQuestionsFromSondageID(sondageId)
            for(questionDeSondage in listeQuestionsDeSondage){
                db.myDAO().deleteChoixDeQuestion(questionDeSondage.id_question)
            }
            db.myDAO().deleteQuestionsDeSondage(sondageId)
        }
    }

    fun AjoutSondagesDansBDDPerso(){
        launch{
            var listeSondagesDansBDDPerso = db.myDAO().loadAllSondages()
            var listeSondagesAAjouter = ArrayList<Sondage>()
            for(sondage in listeTotaleSondages){
                if(!listeSondagesDansBDDPerso.contains(sondage)){
                    sondage.etat = EtatSondage.DISPONIBLE.toString()
                    listeSondagesAAjouter.add(sondage)
                }
            }
            for(sondage in listeSondagesDansBDDPerso){
                if(!listeTotaleSondages.contains(sondage)){
                    DeleteChoixEtQuestionDeSondageDeBDDPerso(sondage.id_sondage)
                }
            }

            db.myDAO().insertAllSondages(listeSondagesAAjouter)

            Log.e("AjoutSondagesDansBD", "on ajoute les sondages dans la liste de sondages au spinner.")
            listeSondagesDansBDDPerso = db.myDAO().loadAllSondages()
            nomSondagesDisponibles.clear()
            sondagesDisponibles.clear()
            nomSondagesDisponibles.add("Choissisez un sondage")
            for(sondageTempo in listeSondagesDansBDDPerso){
                if(VerificationSondageDeCategorieAutorisee(sondageTempo)) {
                    sondagesDisponibles.add(sondageTempo)
                    nomSondagesDisponibles.add(sondageTempo.intituleSondage + "  :  " + sondageTempo.etat)
                }
            }
            RequeteToutesLesQuestionsAvecSondage(listeSondagesAAjouter)
        }
    }


    fun RequeteToutesLesQuestionsAvecSondage(listeSondageAjoutees : ArrayList<Sondage>){
        launch {
//            val listeSondageDansBDDPerso = db.myDAO().loadAllSondages()

            for (sondageTemporaire in listeSondageAjoutees) {
                var serverApiService =
                    ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
                val call_Post =
                    serverApiService.getQuestionsDuSondage(sondageTemporaire.id_sondage.toString())

                call_Post.enqueue(object : Callback<ArrayList<Question>> {
                    override fun onResponse(call: Call<ArrayList<Question>>, response: Response<ArrayList<Question>>) {
                        //Test si la requête a réussi ( code http allant de 200 à 299).
                        if (response.isSuccessful()) {
                            val questions = response.body()
                            if (questions != null) {
                                listeTotaleQuestions.addAll(questions.toList())
                                AjoutQuestionsDansBDDPerso(questions, sondageTemporaire)
                            } else {
                                Log.e("RequeteTousQuestSondage", "La reponse recu est null")
                            }
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<Question>>, t: Throwable) {
                        //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                        Log.e(
                            "RequeteTousQuestSondage",
                            "Erreur de connexion" + t.localizedMessage
                        )
//              ServiceGenerator.Message(this, "blah", t)
                        testConnexion = false
                    }
                })
            }
        }
    }

    fun AjoutQuestionsDansBDDPerso(questionsArrayList: ArrayList<Question>, sondage: Sondage){
        launch{
            Log.e("AjoutQuestionsDansBDD", "On ajoute les questions dans la BDD du sondage : " + questionsArrayList[0].sondage_id)
            db.myDAO().insertAllQuestions(questionsArrayList)
            for(question in questionsArrayList){
                if(question.type.equals("GroupeQuestion")){
                    RequeteSousQuestionsDeQuestion(question)
                }
                if(listeTotaleSondages.indexOf(sondage) == listeTotaleSondages.size-1 && questionsArrayList.indexOf(question) == questionsArrayList.size-1){
                    RequeteTousLesChoixAvecSondage(listeTotaleSondages)
                }
            }
        }
    }

    fun RequeteSousQuestionsDeQuestion(question : Question) {
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.getSousQuestionsDeQuestionGroupe(question.id_question.toString())
        call_Post.enqueue(object : Callback<ArrayList<Question>> {
            override fun onResponse(call: Call<ArrayList<Question>>,response: Response<ArrayList<Question>>) {
                if (response.isSuccessful()) {
                    var listeSousQuestionsTemporaires = response.body()
                    if (listeSousQuestionsTemporaires != null) {
                        for (sousQuestion in listeSousQuestionsTemporaires) {
                            sousQuestion.idQuestionDeGroupe = question.id_question
                        }
                    }
                } else {
                    //Affiche le code de la reponse, soit le code http de la requête.
                    Log.e("blah", "Status code : " + response.raw() + "  " + response.code()/*+ response.code()*/
                    )
                }
            }
            override fun onFailure(call: Call<ArrayList<Question>>, t: Throwable) {
                //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
//                ServiceGenerator.Message(this, "blah", t)
                testConnexion = false
//                    GettingSondageFromBDD(id_sondage.toInt())
            }
        })
    }

    fun AjoutSousQuestionsDansBD(listeSousQuestions : ArrayList<Question>){
        launch {
            withContext(Dispatchers.IO){
               db.myDAO().insertAllQuestions(listeSousQuestions)
            }
        }
    }

    fun RequeteTousLesChoixAvecSondage(listeSondageAjoutees : ArrayList<Sondage>){
        launch {
//            val listeSondageDansBDDPerso = db.myDAO().loadAllSondages()

            for (sondageTemporaire in listeSondageAjoutees) {

                Log.e("RequeteTousChoixSondage", "On passe dans RequeteTousLesChoixAvecSondage")
                var serverApiService =
                    ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
                val call_Post =
                    serverApiService.getChoixParSondage(sondageTemporaire.id_sondage.toString())

                call_Post.enqueue(object : Callback<ArrayList<Choix>> {
                    override fun onResponse(
                        call: Call<ArrayList<Choix>>,
                        response: Response<ArrayList<Choix>>
                    ) {
                        //Test si la requête a réussi ( code http allant de 200 à 299).
                        if (response.isSuccessful()) {
                            val choixPluriel = response.body()
                            if (choixPluriel != null) {
                                listeTotaleChoix.addAll(choixPluriel)
                                AjoutChoixDansBDDPerso(choixPluriel)
                            } else {
                                Log.e("RequeteTousChoixSondage", "La reponse recu est null")
                            }
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<Choix>>, t: Throwable) {
                        //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                        Log.e("RequeteTousChoixSondage", "Erreur de connexion" + t.localizedMessage)
//              ServiceGenerator.Message(this, "blah", t)
                        testConnexion = false
                    }
                })
            }
        }
    }

    fun RequeteTousLesChoix(){

        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.getAllChoix()

        call_Post.enqueue(object : Callback<ArrayList<Choix>> {
            override fun onResponse(call : Call<ArrayList<Choix>>, response : Response<ArrayList<Choix>>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
                    val choixPluriel = response.body()
                    var choixArrayList = ArrayList<Choix>()
                    if (choixPluriel != null) {
                        for(choix in choixPluriel){
                            choixArrayList.add(choix)
                        }
                        AjoutChoixDansBDDPerso(choixArrayList)
                    } else {
                        Log.e("RequeteTousLesChoix", "La reponse recu est null")
                    }
                }
            }
            override fun onFailure(call : Call<ArrayList<Choix>>, t : Throwable ) {
                //Méthode affichant les messages pour l'utilisateur en cas de onFailure, voir ServiceFenerator pour plus de précision.
                Log.e("RequeteTousLesChoix", "Erreur de connexion" + t.localizedMessage)
//              ServiceGenerator.Message(this, "blah", t)
                testConnexion = false
            }
        })
    }

    fun AjoutChoixDansBDDPerso(choixArrayList: ArrayList<Choix>){
        launch{
            db.myDAO().insertAllChoix(choixArrayList)

            InitialisationValeurs()
        }
    }

    fun gettingChoixPourSondage(){
        launch {
            listeChoix = ArrayList()
            for (question in listeQuestions) {
                if(question.type == "QuestionChoix") {
                    val listeChoixTemporaire =
                        db.myDAO().loadAllChoixPourQuestion(question.id_question)
                    listeChoix.addAll(listeChoixTemporaire)
                }
            }
            affichageQuestion()
        }
    }

    fun RequeteEnvoieReponsesEnregistrer(){
        launch {
            val reponsesEnregistrer = db.myDAO().loadAllReponses()
            for (reponseEnregistrer in reponsesEnregistrer) {
                RequeteEnvoieReponse(reponseEnregistrer)
            }
        }
    }
}