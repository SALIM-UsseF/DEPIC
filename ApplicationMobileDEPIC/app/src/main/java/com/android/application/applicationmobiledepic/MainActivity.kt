package com.android.application.applicationmobiledepic

import android.app.ActionBar
import android.app.Activity
import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_NUMBER_VARIATION_NORMAL
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
import com.android.application.applicationmobiledepic.Retrofit.ServerApiService
import com.android.application.applicationmobiledepic.Retrofit.ServiceGenerator
import com.android.application.applicationmobiledepic.Util.InputFilterMinMax
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList

class MainActivity : AppCompatActivity(), CoroutineScope, AdapterView.OnItemSelectedListener {

    private var m_actionBar: ActionBar? = null
    //Le coroutinecontext pour pouvoir utiliser les coroutines
    override val coroutineContext = Dispatchers.Main + SupervisorJob()

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

    //L'accès à la base de données
    private lateinit var db : AppDatabase

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

    // ArrayList pour le stockage
    private var listeTotaleSondages : ArrayList<Sondage> = ArrayList()
    private var listeTotaleQuestions : ArrayList<Question> = ArrayList()
    private var listeTotaleQuestionGroupe : ArrayList<Question> = ArrayList()
    private var listeTotaleQuestionChoix : ArrayList<Question> = ArrayList()

    //String keys for bundle
    private val TAG_LISTE_REPONSES = "listeReponses"
    private val TAG_ID_SONDAGE = "sondageId"
    private val TAG_INDICE_QUESTION_MAX = "indiceQuestionMax"
    private val TAG_INDICE_QUESTION_A_AFFICHER = "indiceQuestionAAfficher"
    private val TAG_POSITION_SPINNER = "positionSpinnerItem"
    private val TAG_UTILISATEUR = "utlisateur"
    private val TAG_INTENT_ACTIVITY_CATEGORIES = 1
    private val TAG_MESSAGE_ERREUR = "MessageErreur"
    private val TAG_EMAIL_AUTHENTIFICATION = "mobileapp@gmail.com"
    private val TAG_PASSWORD_AUTHENTIFICATION = "mobileapptest"

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
    private var boutonReinitialiser : Button? = null

    private var radioGroupPourQuestionChoixUnique: RadioGroup? = null
    private lateinit var linearLayoutButtons : LinearLayout


    //private lateinit var linearLayoutValiderReinit : LinearLayout
    private var dialogUtilisateur: AlertDialog? = null

    private var viewLinearLayoutAlertDialog:LinearLayout? = null
    private var viewConfirmationReinitialisation:LinearLayout? = null
    private var viewConfirmationEnvoiDonnees:LinearLayout? = null


    private var positionSpinner : Int? = 0

    private var debutTestConnexion = true

    private var listeReponsesEnProbation : ArrayList<Reponse> = ArrayList()

    private var listeCategories = ArrayList<Categorie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initilise les boutons et leur layout
        InitialisationValeurs()
        context = this
        savedInstanceStateBundle = savedInstanceState
        // Le listener de changement de connexion
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        this.registerReceiver(networkChangeReceiver, filter)

        Toast.makeText(context, "Veuillez patienter, la connexion au serveur va être testée.", Toast.LENGTH_SHORT).show()
        // Initialise la base de données interne.
        InitialisationBD()
        // Test la présence d'authentification, et s'il n'y en a pas, s'authentifie au serveur.
        InitialisationAuthentification(savedInstanceState)
    }

    /**
     * Ajout du menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflaterOptions : MenuInflater = menuInflater
        menuInflaterOptions.inflate(R.menu.menu_acces_categories, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Fonction comparant deux catégories,
     * si elles n'ont pas la même id renvoie la deuxième,
     * sinon renvoie la première en remplaçant son intituler par celui de la deuxième.
     */
    fun VerificationMemeCategorie(categorieDB: Categorie, categorieServeur: Categorie): Categorie{
        if(categorieDB.id_categorie == categorieServeur.id_categorie){
            categorieDB.intitule = categorieServeur.intitule
            return categorieDB
        } else {
            return categorieServeur
        }
    }

    /**
     * Fonction appelé lors du retour de la deuxième activité.
     * Reçoit les nouvelles catégories qui remplacent les anciennes.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        launch {
            if (requestCode == TAG_INTENT_ACTIVITY_CATEGORIES && resultCode == Activity.RESULT_OK && data != null) {
                listeCategories = ArrayList()
                // On récupère les valeurs des catégories.
                val listeIdTemp = data.getIntegerArrayListExtra("ListeIdCategorie")
                val listeIntituleTemp = data.getStringArrayListExtra("ListeIntituleCategorie")
                val listeActiveTemp = data.getIntegerArrayListExtra("ListeActiveCategorie")
                for (i in 0..listeIdTemp.size - 1) {
                    var categorieTemporaire: Categorie
                    if (listeActiveTemp[i] == 1) {
                        categorieTemporaire = Categorie(listeIdTemp[i], listeIntituleTemp[i], true)
                    } else {
                        categorieTemporaire = Categorie(listeIdTemp[i], listeIntituleTemp[i], false)
                    }
                    listeCategories.add(categorieTemporaire)
                }
                AjoutAllCategoriesDansBDPerso()
                InitialisationListeSondagesDisponibles()
            }
        }
    }

    /**
     * Méthode appeler si l'option Categories du menu est sélectionnée.
     * Lance la deuxième activité en charge de l'abonnment aux catégories.
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.Menu_Acces_Categories){
            // L'intent qui contiendra les données pour les catégories.
            var intent = Intent(context, CategoriesActivity::class.java)
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
            return true
        } else {
            return false
        }
    }

    /**
     * Fonction initialisant la base de données interne.
     */
    fun InitialisationBD(){
        db = Room.databaseBuilder(this, AppDatabase::class.java, "database-name").build()
    }

    /**
     * Fonction permettant de tester si l'authentification est obtenu et dans le cas négatif de la demander.
     * A la fin, on passe à la gestion de l'utilisateur.
     */
    fun InitialisationAuthentification(savedInstanceState: Bundle?){
        launch {
            withContext(Dispatchers.IO) {
                // On charge la liste de tokens sauvegardés.
                val tokens = db.myDAO().loadTokens()
                if (tokens.size == 1) {
                    // Si il y en a un, on l'utilise
                    tokenAuthentification = tokens[0].auth_token
                    InitialisationUtilisateur()
                } else if (tokens.size == 0) {
                    //Si il n'y en a pas, on demande le token au serveur.
                    var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
                    val call_Post = serverApiService.authentification(email = TAG_EMAIL_AUTHENTIFICATION, password = TAG_PASSWORD_AUTHENTIFICATION)
                    call_Post.enqueue(object : Callback<TokenAuthentification> {
                        override fun onResponse(call: Call<TokenAuthentification>, response: Response<TokenAuthentification>) {
                            //Test si la requête a réussi ( code http allant de 200 à 299).
                            if (response.isSuccessful() && response.body() != null) {
                                var tokenAuthentificationTemporaire = response.body()!!
                                tokenAuthentification = tokenAuthentificationTemporaire.auth_token
                                sauvegardeToken(tokenAuthentificationTemporaire)
                            } else {
                                Toast.makeText(context,"Une erreur est arrivé, avertissez le support : il y a une connexion au serveur mais il y eu a un problème durant la communication", Toast.LENGTH_LONG).show()
                                Toast.makeText(context,"L'application n'a pas pu s'authentifier, aucune action ne peut être fait.", Toast.LENGTH_LONG).show()
                                Log.e("AUTHENTIFICATION", "Token ratée : " + response.raw())
                            }
                        }
                        override fun onFailure(call: Call<TokenAuthentification>, t: Throwable) {
                            Log.e("AUTHENTIFICATION", "Erreur de connexion : " + t.localizedMessage)
                            Toast.makeText(context,"L'application n'a pas pu s'authentifier, aucune action ne peut être fait.", Toast.LENGTH_LONG).show()
                            testConnexion = false
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
                // On demande à l'utilisateur de s'enregistrer si il n'y en a pas d'enregistrer ou que celui qui l'est est anonyme (vaut "").
                CreationAlertDialogPourUtilisateur()
            } else if (listeUtilisateurs.size == 1 && !listeUtilisateurs[0].email.equals("")){
                // Si il y a un utilisateur non anonyme, on l'utilise
                utilisateur = listeUtilisateurs[0]
                idUtilisateur = utilisateur!!.id_utilisateur
                LancementRequeteTestConnexion()
            } else if(listeUtilisateurs[0].email == ""){
                // Il y a trop d'utilisateur, on utilise le premier utilisateur qui est anonyme avec une tentative de création d'utilisateur.
                CreationAlertDialogPourUtilisateur()
            } else {
                // Il y a trop d'utilisateur, on utilise le premier qui n'est pas anonyme.
                utilisateur = listeUtilisateurs[0]
                idUtilisateur = utilisateur!!.id_utilisateur
                LancementRequeteTestConnexion()
            }
        }
    }

    /**
     * Fonction créant l'alerte pour enregistrer l'utilisateur.
     * Il peut être enregistré comme anonyme.
     */
    fun CreationAlertDialogPourUtilisateur(){
        // L'alerte
        viewLinearLayoutAlertDialog = layoutInflater.inflate(R.layout.layout_edittext_alertdialog, null) as LinearLayout
        val builderDialogUtilisateur = AlertDialog.Builder(this)
        builderDialogUtilisateur.setMessage(R.string.Message_Utilisateur)
            .setView(viewLinearLayoutAlertDialog)
            .setPositiveButton(R.string.Button_Accepter,
                DialogInterface.OnClickListener { dialog, id ->
                    val editText = viewLinearLayoutAlertDialog!!.getChildAt(0) as EditText
                    // On vérifie que c'est bien un email.
                    val regex = ".+@.+\\..+".toRegex()
                    if(regex.matches(editText.text)){
                        // enregistrement sans anonymisation
                        RequeteEnvoieNouveauUtilisateur(editText.text.toString())
                    } else {
                        // Anonymisation
                        RequeteEnvoieNouveauUtilisateur("")
                    }
                })
            .setNegativeButton(R.string.Button_Refuser) { dialog, id ->
                // L'utilisateur veut être anonymiser.
                RequeteEnvoieNouveauUtilisateur("")
            }
        dialogUtilisateur = builderDialogUtilisateur.create()
        dialogUtilisateur!!.show()
    }



    /**
     * Fonction créant l'alerte pour confirmation de réinitialisation des réponses.
     */
    fun CreationAlertDialogConfirmationReinitialisation(view: View){
        viewConfirmationReinitialisation = layoutInflater.inflate(R.layout.layout_confirmation_reinitialisation, null) as LinearLayout
        val builderDialogUtilisateur = AlertDialog.Builder(this)
        builderDialogUtilisateur.setMessage(R.string.Message_Confirmation_Reinitialisation)
            .setView(viewConfirmationReinitialisation)
            .setPositiveButton(R.string.Button_Accepter,
                DialogInterface.OnClickListener { dialog, id ->
                    ReinitialiserLayoutSondage()
                })
        dialogUtilisateur = builderDialogUtilisateur.create()
        dialogUtilisateur!!.show()
    }

    /**
     * Fonction créant l'alerte pour confirmation d'envoi des réponses.
     */
    fun CreationAlertDialogConfirmationEnvoiDonnees(view: View){
        viewConfirmationEnvoiDonnees = layoutInflater.inflate(R.layout.layout_confirmation_reinitialisation, null) as LinearLayout
        val builderDialogUtilisateur = AlertDialog.Builder(this)
        builderDialogUtilisateur.setMessage(R.string.Message_Confirmation_Envoi_Donnees)
            .setView(viewConfirmationEnvoiDonnees)
            .setPositiveButton(R.string.Button_Accepter,
                DialogInterface.OnClickListener { dialog, id ->
                    validationSondage()
                })
        dialogUtilisateur = builderDialogUtilisateur.create()
        dialogUtilisateur!!.show()
    }


    /**
     * Fonction envoyant la requête à la base de données pour enregistrer l'utilisateur.
     * On envoie l'email donnée ("" si l'utilisateur n'a pas voulu être enregistré)
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
                        Log.e("RequeteNouvUtilisateur", "La réponse reçue est nulle")
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


    /**
     * Fonction initialisant :
     *      les boutons et leur layout
     *      le spinner et son adapter
     */
    fun InitialisationValeurs(){

        // On initialise les boutons et leur layout.
        linearLayout = findViewById(R.id.layout_general)
        boutonPrecedent = findViewById(R.id.Button_Precedent)
        boutonSuivant = findViewById(R.id.Button_Suivant)
        boutonValider = findViewById(R.id.Button_Validation_Enregistrement)
        boutonReinitialiser = findViewById(R.id.Button_Reinitialisation)
        linearLayoutButtons = findViewById(R.id.Layout_Buttons)
        GestionAffichageBoutons()


        // Recherche du spinner pour choix de sondages et son initialisation
        spinner = findViewById<View>(R.id.spinner) as Spinner
        nomSondagesDisponibles.add("Choissisez un sondage")
        this.spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nomSondagesDisponibles)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = spinnerAdapter
        spinner!!.onItemSelectedListener = this
    }

    /**
     * Fonction reprenant toutes les données enregistrées dans le savedInstanceState
     * Si il y avait un sondage de sélectionné, alors il y a :
     *      - l'indice de la question visitée la plus loin,
     *      - l'indice de la question à afficher,
     *      - la position du spinner,
     *      - les réponses temporairement enregistrées.
     */
    fun UtilisationBundleSauvegarde(){
        if(savedInstanceStateBundle != null) {
            // On récupère l'id du dernier sondage utilisé et non envoyé
            val sondageId = savedInstanceStateBundle!!.getInt(TAG_ID_SONDAGE)
            // sondageId == -1 s'il n'y avait pas de sondage précédemment utilisé
            if (sondageId != -1) {
                var listeTemporaire = savedInstanceStateBundle!!.getStringArrayList(TAG_LISTE_REPONSES)
                if(listeTemporaire != null) {
                    listeReponsesTemporaires = listeTemporaire
                }
                indiceQuestionMax = savedInstanceStateBundle!!.getInt(TAG_INDICE_QUESTION_MAX)
                indiceQuestionAfficher = savedInstanceStateBundle!!.getInt(TAG_INDICE_QUESTION_A_AFFICHER)
                positionSpinner = savedInstanceStateBundle!!.getInt(TAG_POSITION_SPINNER)
                spinner!!.setSelection(positionSpinner!!)
            } else {
                // Il n'y avait pas de sondage de sélectionné, on revient donc à l'état initial de l'application
                spinner!!.setSelection(0)
            }
        }
    }


    private var networkChangeReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val connectivityManager =
                context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected) {
                //Il y a une connexion
                Toast.makeText(context, "Il y a de la connexion à un réseau, tentative d'accès au serveur.", Toast.LENGTH_SHORT).show()
                if(!testConnexion && !debutTestConnexion) {
                    TestConnexion()
                }

            } else {
                // il n'y a pas de connexion
                Toast.makeText(context, "Il n'y a pas de connexion.", Toast.LENGTH_SHORT).show()
                TestConnexion()
            }
        }
    }

    fun TestConnexion(){
        // On test la connexion avec une requête vers le serveur.
        LancementRequeteTestConnexion()
    }


    /**
     * Fonction commançant l'initialisation des sondages.
     */
    fun ResultatTestConnexion(resultat : Boolean){
        if(resultat){
            // On peut accéder au serveur
            testConnexion = true
            ReceptionCategories()
            ReceptionSondagesPublics()
        } else {
            // On ne peut pas accéder au serveur
            testConnexion = false
            debutTestConnexion = false
            // On récupère les catégories de sondages de la base de données interne.
            RecuperationCategoriesDeBDPerso()
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

    /**
     * Récupère les sondages de la base de données interne
     */
    fun InitialisationListeSondagesDisponibles(){
        launch{
            // On met à jour la liste de sondages pour le spinner.
            nomSondagesDisponibles = ArrayList()
            sondagesDisponibles = ArrayList()
            nomSondagesDisponibles.add("Choissisez un sondage")
            var listeSondagesDisponibles = db.myDAO().loadAllSondages()
            listeSondagesDisponibles.forEach {
                if(VerificationSondageDeCategorieAutorisee(it)){
                    nomSondagesDisponibles.add(it.intituleSondage + "  :  " + it.etat)
                    sondagesDisponibles.add(it)
                }
            }
            UtilisationBundleSauvegarde()
            spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, nomSondagesDisponibles)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner!!.adapter = spinnerAdapter
        }
    }


    /**
     * Récupère les catégories de sondages de la base de données interne.
     */
    fun RecuperationCategoriesDeBDPerso(){
        launch{
            withContext(Dispatchers.IO){
                listeCategories = ArrayList()
                var listeCategoriesTemporaire = db.myDAO().loadAllCategories()
                listeCategories.addAll(listeCategoriesTemporaire)
                // On récupère les sondages de la base de données interne
                InitialisationListeSondagesDisponibles()
            }
        }
    }


    suspend fun getSondageFromSondageID(sondageId: Int): Sondage?{
        val sondages = db.myDAO().loadOneSondageFromSondageId(sondageId)
        for (sond in sondages) {
            if(sond.id_sondage == sondageId){
                return sond
            }
        }
        return null
    }

    /**
     * Fonction récupérant les questions et sous-questions pour le sondage donnée de la base de données interne.
     */
    fun GettingSondageFromBDD(sondageId: Int){
        launch {
            sondage = getSondageFromSondageID(sondageId)
            listeQuestions = ArrayList()
            listeSousQuestions = ArrayList()
            var questions = getQuestionFromSondageID(sondage!!.id_sondage)
            for (question in questions) {
                if (question.idQuestionDeGroupe != null) {
                    listeSousQuestions.add(question)
                } else {
                    listeQuestions.add(question)
                }
            }
            // On récupère les choix pour le sondage.
            gettingChoixPourSondage()
        }
    }



    fun AffichageQuestion(){
        GestionAffichageBoutons()
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
                return ligneReponses.substring(ligneReponses.indexOf(";")+1, ligneReponses.length)
            } else if(question.type == "QuestionPoint" && ligneReponses.length > 0 && ligneReponses.substring(0, ligneReponses.indexOf(";")).toInt() == question.id_question) {
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


    /**
     * Méthode ajoutant les vues des questions pour les question de type "QuestionPoint"
     * On peut y répondre dans un EditText et la réponse doit être entre Question.minPoints et question.maxPoints
     */
    private fun LancementQuestionAPoints(question: Question, linearLayoutBase: LinearLayout, reponseGroupe : String?) {
        val linearLayoutHorizontal = LancementGenerique(question, linearLayoutBase)
        val editText = EditText(this)
        editText.id = View.generateViewId()
        editText.setTag("Reponse_" + listeQuestions[indiceQuestionAfficher].id_question + "_" + 0)
        editText.inputType = TYPE_CLASS_NUMBER
        if(question.minPoints != null && question.maxPoints != null){
            var inputFilter = InputFilterMinMax(question.minPoints, question.maxPoints)
            var inputFilters : Array<InputFilter> = arrayOf(inputFilter)
            editText.filters = inputFilters
        }
        test++
        val reponse = RechercheQuestionIdDansListeReponsesTemporaire(question)
        if(!reponse.equals("")){
            editText.setText(reponse)
        } else if(reponseGroupe != null){
            editText.setText(reponseGroupe)
        }
        val params = LinearLayout.LayoutParams(param)
        params.gravity = Gravity.CENTER_HORIZONTAL
        linearLayoutHorizontal.addView(editText, 1, LinearLayout.LayoutParams(params))
        linearLayoutPourQuestionPoint = linearLayoutHorizontal
    }


    /**
     * Méthode ajoutant les vues des questions pour les question de type "QuestionLibre"
     * On peut y répondre dans un EditText et avec une limite de caractère de Question.nombreDeCaractere
     */
    private fun LancementQuestionTexteLibre(question : Question, linearLayoutBase: LinearLayout, reponseGroupe: String?) {
        val linearLayoutHorizontal = LancementGenerique(question, linearLayoutBase)
        val editText = EditText(this)
        editText.id = View.generateViewId()
        editText.setTag("Reponse_" + listeQuestions[indiceQuestionAfficher].id_question + "_" + 0)
        // Ajout d'un filtre pour limiter le nombre de caractères pouvant être rentré
        if(question.nombreDeCaractere != null){
            var inputFilter = InputFilter.LengthFilter(question.nombreDeCaractere)
            var inputFilters : Array<InputFilter> = arrayOf(inputFilter)
            editText.filters = inputFilters
        }
        val reponse = RechercheQuestionIdDansListeReponsesTemporaire(question)
        if(!reponse.equals("")){
            editText.setText(reponse)
        } else if(reponseGroupe != null){
            editText.setText(reponseGroupe)
        }
        test++
        val params = LinearLayout.LayoutParams(param)
        params.gravity = Gravity.CENTER_HORIZONTAL
        linearLayoutHorizontal.addView(editText, 1, LinearLayout.LayoutParams(params))
        linearLayoutPourQuestionTexteLibre = linearLayoutHorizontal
        //        editText.setInputType();
    }


    /**
     * Méthode débutant l'affichage d'une question de type GroupeQuestion et ses sous-questions.
     */
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

    /**
     * Méthode créant le LinearLayout qui contiendra le LinearLayout de la question et celui pour les réponses
     */
    fun Ajout_LinearLayout_Question_Reponse(linearLayoutBase: LinearLayout): LinearLayout {
        val linearLayoutHorizontal = LinearLayout(this)
        linearLayoutHorizontal.orientation = LinearLayout.HORIZONTAL
        linearLayoutHorizontal.id = View.generateViewId()
        linearLayoutHorizontal.setPadding(0, 50, 0, 50)
        linearLayoutBase.addView(linearLayoutHorizontal, linearLayoutBase.childCount, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        return linearLayoutHorizontal
    }

    /**
     * Méthode créant le LinearLayout qui contiendra les LinearLayout des sous-questions pour l'affichage des questions de type GroupeQuestion
     */
    fun Ajout_LinearLayout_Question_Groupe(): LinearLayout {
        val linearLayoutVertical = LinearLayout(this)
        linearLayoutVertical.orientation = LinearLayout.VERTICAL
        linearLayoutVertical.id = View.generateViewId()
        linearLayoutVertical.setPadding(0, 50, 0, 50)
        linearLayout!!.addView(linearLayoutVertical, linearLayout!!.childCount, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT))
        return linearLayoutVertical
    }

    /**
     * Méthode créant la vue qui contiendra l'intitulé de la question à afficher
     */
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

    /**
     * Méthode ajoutant les vues des questions pour les question de type "QuestionChoix" avec question.estUnique à vrai
     */
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

    /**
     * Méthode ajoutant les vues des questions pour les question de type "QuestionChoix" avec question.estUnique à faux
     */
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

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        nomSondage = parent.getItemAtPosition(pos) as String
        sondage = SelectionSondageVoulueParNom(nomSondage)
        positionSpinner = pos
        for (i in linearLayout!!.childCount downTo 2) {
            linearLayout!!.removeViewAt(i - 1)
        }
        if (nomSondage != "Choissisez un sondage" && positionSpinner != 0) {
            linearLayout!!.getChildAt(0).visibility = View.VISIBLE
            if(testConnexion){
                RecuperationQuestionsDuSondage(sondage!!.id_sondage.toString())
            } else {
                sondage = SelectionSondageVoulueParNom(nomSondage)
                GettingSondageFromBDD(sondage!!.id_sondage)
            }
        } else {
            // On n'est à l'acceuil de l'application.
            linearLayout!!.getChildAt(0).visibility = View.INVISIBLE
            GestionAffichageBoutons()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        Log.e("Spinner  : ", "On a cliqué sur rien.")

    }

    fun ReinitialiserLayoutSondage() {
        //Reinit vues
        linearLayoutPourQuestionTexteLibre = null
        linearLayoutPourQuestionPoint = null
        linearLayoutPourQuestionChoixMultiples = null
        linearLayoutPourQuestionGroup = null
        radioGroupPourQuestionChoixUnique = null
        listLayoutQuestion = ArrayList()
        GestionAffichageBoutons()
        //reinit reponses
        listeReponsesTemporaires = ArrayList()
        //reinit progression
        indiceQuestionAfficher = 0
        indiceQuestionMax = 0
        for (i in linearLayout!!.childCount downTo 2) {
            linearLayout!!.removeViewAt(i - 1)
        }
        if(sondage != null){
            AffichageQuestion()
        }
    }


    /**
     * Méthode prenant toutes les réponses temporairement enregistrées et appellant une autre méthode pour l'envoi à la base de données
     */
    fun validationSondage(){
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
                    if(question.type != "QuestionGroupe") {
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

                    }
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
    }

    fun RecuperationQuestionsDuSondage(id_sondage : String){
        listeQuestions = ArrayList()
        var listeQuestionGroupeTemporaire = ArrayList<Question>()
        var listeQuestionChoixTemporaire = ArrayList<Question>()

        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.getQuestionsDuSondage(id_sondage)
        call_Post.enqueue(object : Callback<ArrayList<Question>> {
            override fun onResponse(call : Call<ArrayList<Question>>, response : Response<ArrayList<Question>>) {
                if (response.isSuccessful()) {
                    val allQuestions = response.body()
                    if (allQuestions != null) {
                        for (question in allQuestions) {
                            if (question.idQuestionDeGroupe == null && question.type.equals("GroupeQuestion")) {
                                listeQuestionGroupeTemporaire.add(question)
                            }
                            if (question.idQuestionDeGroupe == null && question.type.equals("QuestionChoix")) {
                                listeQuestionChoixTemporaire.add(question)
                            }
                            if(question.idQuestionDeGroupe == null){
                                listeQuestions.add(question)
                            }
                        }
                        if(listeQuestionGroupeTemporaire.size != 0){
                            RecuperationSousQuestionsDeSondage(listeQuestionGroupeTemporaire, listeQuestionChoixTemporaire)
                        } else if(listeQuestionChoixTemporaire.size != 0){
                            RequeteChoixParQuestionsPourSondage(listeQuestionChoixTemporaire)
                        } else {
                            AffichageQuestion()
                        }
                    }
                } else {
                    //Affiche le code de la reponse, soit le code http de la requête.
                    Log.e("RecupQuestionsSondage", "Status code : " + response.raw() + "  " + response.code()/*+ response.code()*/)
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

    fun RecuperationSousQuestionsDeSondage(listeQuestionGroupeTemporaire: ArrayList<Question>, listeQuestionChoixTemporaire: ArrayList<Question>){
        var compteurValeurEntrante = 0
        listeSousQuestions = ArrayList()
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        for(question in listeQuestionGroupeTemporaire){
            if(question.type.equals("GroupeQuestion")) {
                val call_Post = serverApiService.getSousQuestionsDeQuestionGroupe(question.id_question.toString())
                call_Post.enqueue(object : Callback<ArrayList<Question>> {
                    override fun onResponse(call: Call<ArrayList<Question>>, response: Response<ArrayList<Question>>) {
                        compteurValeurEntrante++
                        if (response.isSuccessful()) {
                            var listeSousQuestionsTemporaires = response.body()
                            if (listeSousQuestionsTemporaires != null) {
                                for (sousQuestion in listeSousQuestionsTemporaires) {
                                    sousQuestion.idQuestionDeGroupe = question.id_question
                                    if(sousQuestion.type.equals("QuestionChoix")){
                                        listeQuestionChoixTemporaire.add(sousQuestion)
                                    }
                                    listeSousQuestions.add(sousQuestion)
                                }
                            }
                            if(compteurValeurEntrante == listeQuestionGroupeTemporaire.size){
                                // On demande les choix pour les questions du sondage.
                                RequeteChoixParQuestionsPourSondage(listeQuestionChoixTemporaire)
                            }
                        } else {
                            //Affiche le code de la reponse, soit le code http de la requête.
                            Log.e("RecupSousQuestSondage", "Status code : " + response.raw() + "  " + response.code())
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

    /**
     * On teste la connexion au serveur.
     */
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
                    //Affiche le code de la reponse, soit le code http de la requête.
                    Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + response.errorBody())
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

    /**
     * Méthode récupérant les sondages du serveur
     */
    fun ReceptionSondagesPublics(){
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        val call_Post = serverApiService.getAllSondages()
        call_Post.enqueue(object : Callback<ArrayList<Sondage>> {
            override fun onResponse(call : Call<ArrayList<Sondage>>, response : Response<ArrayList<Sondage>>) {
                //Test si la requête a réussi ( code http allant de 200 à 299).
                if (response.isSuccessful()) {
                    val allSondages = response.body()
                    if (allSondages != null) {
                        listeTotaleSondages = allSondages
                        // On insert les sondages obtenues dans la base de données interne
                        AjoutSondagesDansBDDPerso()
                    }
                } else {
                    //Affiche le code de la reponse, soit le code http de la requête.
                    Log.e("ReceptionSondages", "Status code : " + response.raw() + response.code())
                    InitialisationListeSondagesDisponibles()
                }
            }
            override fun onFailure(call : Call<ArrayList<Sondage>>, t : Throwable ) {
                Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + t.localizedMessage)
                testConnexion = false
                InitialisationListeSondagesDisponibles()
            }
        })
    }

    /**
     * Méthode récupérant les catégories du serveur.
     * Modifie celles existant déjà si besoin (intituler différent)
     */
    fun ReceptionCategories(){
        launch {
            listeCategories = ArrayList()
            listeCategories.addAll(db.myDAO().loadAllCategories())
            var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
            val call_Post = serverApiService.getAllCategories()
            call_Post.enqueue(object : Callback<ArrayList<Categorie>> {
                override fun onResponse(call: Call<ArrayList<Categorie>>, response: Response<ArrayList<Categorie>>) {
                    //Test si la requête a réussi ( code http allant de 200 à 299).
                    if (response.isSuccessful()) {
                        var listeCategoriesTemporaireDB = listeCategories
                        listeCategories = ArrayList()
                        val listeCategoriesTemporaire = response.body()
                        if (listeCategoriesTemporaire != null) {
                            for(i in 0..listeCategoriesTemporaire.size-1){
                                if (listeCategoriesTemporaireDB.size > i) {
                                    listeCategories.add(
                                        VerificationMemeCategorie(listeCategoriesTemporaireDB[i], listeCategoriesTemporaire[i])
                                    )
                                } else {
                                    listeCategories.add(listeCategoriesTemporaire[i])
                                }
                            }
                            AjoutAllCategoriesDansBDPerso()
                        }
                    } else {
                        //Affiche le code de la reponse, soit le code http de la requête.
                        Log.e("ReceptionCategories", "Status code : " + response.raw() + response.code())
                        RecuperationCategoriesDeBDPerso()
                    }
                }
                override fun onFailure(call: Call<ArrayList<Categorie>>, t: Throwable) {
                    Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + t.localizedMessage)
                    testConnexion = false
                    RecuperationCategoriesDeBDPerso()
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

    /**
     * Fonction retournant si la question est une sous-question.
     */
    fun TestPresenceQuestionDansSousQuestion(question: Question): Boolean{
        for(sousQuestion in listeSousQuestions){
            if(sousQuestion.id_question == question.id_question){
                return true
            }
        }
        return false
    }

    /**
     * Fonction appelé lorsqu'on appuie sur le bouton Précédent.
     * Elle enlève toutes les vues temporaires de questions,
     * puis elle appelle la fonction de sauvegarde temporaires de réponses
     * puis elle change de question
     * puis elle appelle la fonction mettant à jour les boutons
     * puis elle appelle la fonction d'affichage de question
     */
    fun PassageQuestionPrecedente(view: View) {
        for (i in linearLayout!!.childCount downTo 2) {
            linearLayout!!.removeViewAt(i - 1)
        }
        sauvegardeReponsesAvantPassage()
        // On se déplace en arrière une fois puis tant que l'on est sur une sous-question
        indiceQuestionAfficher = indiceQuestionAfficher - 1
        while (TestPresenceQuestionDansSousQuestion(listeQuestions[indiceQuestionAfficher])) {
            indiceQuestionAfficher = indiceQuestionAfficher - 1
        }
        GestionAffichageBoutons()
        AffichageQuestion()
    }

    /**
     * Fonction appelé lorsqu'on appuie sur le bouton Suivant.
     * Elle enlève toutes les vues temporaires de questions,
     * puis elle appelle la fonction de sauvegarde temporaires de réponses
     * puis elle change de question
     * puis elle appelle la fonction mettant à jour les boutons
     * puis elle appelle la fonction d'affichage de question
     */
    fun PassageQuestionSuivante(view: View) {
        for (i in linearLayout!!.childCount downTo 2) {
            linearLayout!!.removeViewAt(i - 1)
        }
        sauvegardeReponsesAvantPassage()
        if(indiceQuestionAfficher == indiceQuestionMax){
            indiceQuestionMax++
        }
        var deplacementSupplementaires = 0
        // On se déplace en arrière une fois puis tant que l'on est sur une sous-question
        indiceQuestionAfficher = indiceQuestionAfficher + 1
        while (TestPresenceQuestionDansSousQuestion(listeQuestions[indiceQuestionAfficher])) {
            indiceQuestionAfficher = indiceQuestionAfficher + 1
            deplacementSupplementaires++
        }
        if(indiceQuestionMax == indiceQuestionAfficher - deplacementSupplementaires){
            indiceQuestionMax += deplacementSupplementaires
        }
        GestionAffichageBoutons()
        AffichageQuestion()
    }

    /**
     * Méthode gérant la visibilité des boutons selon la position de la question actuellement affichée.
     */
    fun GestionAffichageBoutons(){
        if (positionSpinner != 0) {
            linearLayoutButtons.visibility = View.VISIBLE
            boutonReinitialiser!!.isEnabled = true
            boutonReinitialiser!!.setTextColor(resources.getColor(R.color.btn_txt_color))
            if(indiceQuestionAfficher == 0){
                boutonPrecedent!!.isEnabled = false
                boutonPrecedent!!.setTextColor(resources.getColor(R.color.btn_txt_color_hide))
            } else {
                boutonPrecedent!!.isEnabled = true
                boutonPrecedent!!.setTextColor(resources.getColor(R.color.btn_txt_color))
            }
            if(indiceQuestionAfficher == listeQuestions.size-1){
                boutonSuivant!!.isEnabled = false
                boutonSuivant!!.setTextColor(resources.getColor(R.color.btn_txt_color_hide))
                boutonValider!!.isEnabled = true
                boutonValider!!.setTextColor(resources.getColor(R.color.btn_txt_color))
            } else {
                boutonSuivant!!.isEnabled = true
                boutonSuivant!!.setTextColor(resources.getColor(R.color.btn_txt_color))
                boutonValider!!.isEnabled = false
                boutonValider!!.setTextColor(resources.getColor(R.color.btn_txt_color_hide))
            }

        } else {
            boutonPrecedent!!.isEnabled = false
            boutonPrecedent!!.setTextColor(resources.getColor(R.color.btn_txt_color_hide))
            boutonSuivant!!.isEnabled = false
            boutonSuivant!!.setTextColor(resources.getColor(R.color.btn_txt_color_hide))
            boutonValider!!.isEnabled = false
            boutonValider!!.setTextColor(resources.getColor(R.color.btn_txt_color_hide))
            boutonReinitialiser!!.isEnabled = false
            boutonReinitialiser!!.setTextColor(resources.getColor(R.color.btn_txt_color_hide))
            // Si on est à l'acceuil de l'application (postionSpinner == 0), on n'affiche pas les boutons
            linearLayoutButtons.visibility = View.INVISIBLE
        }
    }

    fun sauvegardeReponsesAvantPassage(){
        Log.e("sauvegardeAVP","test indice : " + indiceQuestionAfficher.toString() + "    " + indiceQuestionMax.toString() + "   " + listeReponsesTemporaires.size)
        val questionTemporaire = listeQuestions.get(indiceQuestionAfficher)

        //Si on est à la plus récente question et qu'elle n'a pas encore de réponse
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
        } else if(RechercheQuestionIdDansListeReponsesTemporairePourIndice(questionTemporaire) != -1){
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

    fun RequeteChoixParQuestionsPourSondage(listeQuestionsChoixTemporaire: ArrayList<Question>){
        var compteurValeurEntrante = 0
        listeChoix = ArrayList()
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        for(question in listeQuestionsChoixTemporaire){
            if(question.type.equals("QuestionChoix")){
                val call_Post = serverApiService.getChoixParQuestionParSondage(question.sondage_id.toString(), question.id_question.toString())
                call_Post.enqueue(object : Callback<List<Choix>> {
                    override fun onResponse(call : Call<List<Choix>>, response : Response<List<Choix>>) {
                        //Test si la requête a réussi ( code http allant de 200 à 299).
                        compteurValeurEntrante++
                        if (response.isSuccessful()) {
                            val listChoixTemporaire = response.body()
                            if (listChoixTemporaire != null) {
                                for(choix in listChoixTemporaire) {
                                    listeChoix.add(choix)
                                }
                            }
                            if(compteurValeurEntrante == listeQuestionsChoixTemporaire.size){
                                AffichageQuestion()
                            }
                        } else {
                            Log.e("RequeteChoixSondage", "Status code : " + response.raw() + response.code())
                        }
                    }
                    override fun onFailure(call : Call<List<Choix>>, t : Throwable ) {
                        Log.e(TAG_MESSAGE_ERREUR, "Message d'erreur : " + t.localizedMessage)
                        testConnexion = false
                    }
                })
            }
        }
    }

    fun SelectionSondageVoulueParNom(nomsondage: String): Sondage?{
        for(sondage in sondagesDisponibles){
            if((sondage.intituleSondage).equals(nomsondage.split("  :  ").get(0))){
                return sondage
            }
        }
        return null
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

    fun DeleteChoixEtQuestionDeSondageDeBDDPerso(sondageId: Int){
        launch {
            val listeQuestionsDeSondage = db.myDAO().loadQuestionsFromSondageID(sondageId)
            for(questionDeSondage in listeQuestionsDeSondage){
                db.myDAO().deleteChoixDeQuestion(questionDeSondage.id_question)
            }
            db.myDAO().deleteQuestionsDeSondage(sondageId)
        }
    }

    /**
     * Méthode ajoutant les sondages reçues du serveur dans la base de données interne.
     * On fait attention à ne pas modifier un sondage déjà enregistré.
     */
    fun AjoutSondagesDansBDDPerso(){
        launch{
            var listeSondagesDansBDDPerso = db.myDAO().loadAllSondages()
            var listeSondagesAAjouter = ArrayList<Sondage>()
            // Les sondages n'étant pas dans la base de données interne sont ajoutés
            for(sondage in listeTotaleSondages){
                if(!listeSondagesDansBDDPerso.contains(sondage)){
                    sondage.etat = EtatSondage.DISPONIBLE.toString()
                    listeSondagesAAjouter.add(sondage)
                }
            }
            // Les sondages n'étant plus dans le serveur et n'ayant pas été répondu sont supprimés de la base de données interne
            for(sondage in listeSondagesDansBDDPerso){
                if(!listeTotaleSondages.contains(sondage) && sondage.etat != "REPONDU"){
                    DeleteChoixEtQuestionDeSondageDeBDDPerso(sondage.id_sondage)
                }
            }
            db.myDAO().insertAllSondages(listeSondagesAAjouter)
            // On met à jour les sondages du spinner
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
            // On récupère toutes les questions du serveur
            RequeteToutesLesQuestionsAvecSondage(listeSondagesAAjouter)
        }
    }


    /**
     * Fonction récupèrant toutes les questions des sondages passés en paramètre du serveur.
     */
    fun RequeteToutesLesQuestionsAvecSondage(listeSondageAjoutees : ArrayList<Sondage>){
        launch {
            listeTotaleQuestions = ArrayList()
            var compteurValeurEntrante = 0
            for (sondageTemporaire in listeSondageAjoutees) {
                var serverApiService =
                    ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
                val call_Post =
                    serverApiService.getQuestionsDuSondage(sondageTemporaire.id_sondage.toString())

                call_Post.enqueue(object : Callback<ArrayList<Question>> {
                    override fun onResponse(call: Call<ArrayList<Question>>, response: Response<ArrayList<Question>>) {
                        //Test si la requête a réussi ( code http allant de 200 à 299).
                        compteurValeurEntrante++
                        if (response.isSuccessful() && response.body() != null) {
                            val questions = response.body()
                            listeTotaleQuestions.addAll(questions!!.toList())
                            // Si on est au dernier sondage, on ajoute les questions à la base de données interne
                            if(compteurValeurEntrante == listeSondageAjoutees.size){
                                AjoutQuestionsDansBDDPerso()
                            }
                        } else {
                            Log.e("RequeteTousQuestSondage", "La reponse recu est null")
                        }
                    }
                    override fun onFailure(call: Call<ArrayList<Question>>, t: Throwable) {
                        Log.e("RequeteTousQuestSondage", "Erreur de connexion" + t.localizedMessage)
                        testConnexion = false
                    }
                })
            }
        }
    }

    /**
     * Méthode sauvegardant toutes les question dans la base de données interne.
     */
    fun AjoutQuestionsDansBDDPerso(){
        launch{
            db.myDAO().insertAllQuestions(listeTotaleQuestions)
            listeTotaleQuestionGroupe = ArrayList()
            listeTotaleQuestionChoix = ArrayList()
            for(question in listeTotaleQuestions){
                if(question.type.equals("GroupeQuestion")){
                    listeTotaleQuestionGroupe.add(question)
                }
                if(question.type.equals("QuestionChoix")){
                    listeTotaleQuestionChoix.add(question)
                }
            }
            if(listeTotaleQuestionGroupe.size != 0){
                RequeteSousQuestionsDeQuestion()
            } else if(listeTotaleQuestionChoix.size != 0){
                RequeteTousLesChoixAvecSondage()
            } else {
                GestionAffichageBoutons()
                UtilisationBundleSauvegarde()
            }
        }
    }

    /**
     * Méthode récupèrant toutes les sous-questions du serveur.
     */
    fun RequeteSousQuestionsDeQuestion() {
        var compteurValeurEntrante = 0
        var listeTotaleSousQuestion = ArrayList<Question>()
        var serverApiService = ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
        for(question in listeTotaleQuestionGroupe) {
            val call_Post = serverApiService.getSousQuestionsDeQuestionGroupe(question.id_question.toString())
            call_Post.enqueue(object : Callback<ArrayList<Question>> {
                override fun onResponse(call: Call<ArrayList<Question>>, response: Response<ArrayList<Question>>) {
                    compteurValeurEntrante++
                    if (response.isSuccessful() && response.body() != null) {
                        var listeSousQuestionsTemporaires = response.body()
                        for (sousQuestion in listeSousQuestionsTemporaires!!) {
                            sousQuestion.idQuestionDeGroupe = question.id_question
                            listeTotaleSousQuestion.add(sousQuestion)
                        }
                        if(compteurValeurEntrante == listeTotaleQuestionGroupe.size){
                            AjoutSousQuestionsDansBD(listeTotaleSousQuestion)
                        }
                    } else {
                        Log.e("RequêteToutesSousQuest", "Status code : " + response.raw() + response.code())
                    }
                }
                override fun onFailure(call: Call<ArrayList<Question>>, t: Throwable) {
                    Log.e("RequêteToutesSousQuest", "Erreur de connexion" + t.localizedMessage)
                    testConnexion = false
                }
            })
        }
    }

    /**
     * Fonction ajoutant les sous-questions passsés en paramètre à la base de données interne.
     */
    fun AjoutSousQuestionsDansBD(listeSousQuestions : ArrayList<Question>){
        launch {
            withContext(Dispatchers.IO){
               db.myDAO().insertAllQuestions(listeSousQuestions)
                RequeteTousLesChoixAvecSondage()
            }
        }
    }

    /**
     * Méthode récupérant tous les choix des sondages du serveur.
     */
    fun RequeteTousLesChoixAvecSondage(){
        launch {
            var compteurValeurEntrante = 0
            var listeTotaleChoix = ArrayList<Choix>()
            for (sondageTemporaire in listeTotaleSondages) {
                Log.e("RequeteTousChoixSondage", "On passe dans RequeteTousLesChoixAvecSondage")
                var serverApiService =
                    ServiceGenerator().createService(ServerApiService::class.java, tokenAuthentification)
                val call_Post =
                    serverApiService.getChoixParSondage(sondageTemporaire.id_sondage.toString())
                call_Post.enqueue(object : Callback<ArrayList<Choix>> {
                    override fun onResponse(call: Call<ArrayList<Choix>>, response: Response<ArrayList<Choix>>) {
                        //Test si la requête a réussi ( code http allant de 200 à 299).
                        compteurValeurEntrante++
                        if (response.isSuccessful() && response.body() != null) {
                            listeTotaleChoix.addAll(response.body()!!.toList())
                        } else {
                            Log.e("RequeteTousChoixSondage", "Status code : " + response.raw() + response.code())
                        }
                        if(compteurValeurEntrante == listeTotaleSondages.size){
                            // On est au dernier sondage, on ajoute les choix dans la base de données interne
                            AjoutChoixDansBDDPerso(listeTotaleChoix)
                        }
                    }
                    override fun onFailure(call: Call<ArrayList<Choix>>, t: Throwable) {
                        Log.e("RequeteTousChoixSondage", "Erreur de connexion" + t.localizedMessage)
                        testConnexion = false
                    }
                })
            }
        }
    }

    /**
     * Fonction rajoutant tous les choix passés en paramètre à la base de données interne.
     */
    fun AjoutChoixDansBDDPerso(choixArrayList: ArrayList<Choix>){
        launch{
            db.myDAO().insertAllChoix(choixArrayList)
//            AffichageQuestion()
            GestionAffichageBoutons()
            UtilisationBundleSauvegarde()
        }
    }

    /**
     * Méthode récupérant les choix pour un sondage dans la base de données interne
     */
    fun gettingChoixPourSondage(){
        launch {
            listeChoix = ArrayList()
            for (question in listeQuestions) {
                if(question.type == "QuestionChoix") {
                    val listeChoixTemporaire = db.myDAO().loadAllChoixPourQuestion(question.id_question)
                    listeChoix.addAll(listeChoixTemporaire)
                }
            }
            // On affiche la question en cours.
            AffichageQuestion()
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