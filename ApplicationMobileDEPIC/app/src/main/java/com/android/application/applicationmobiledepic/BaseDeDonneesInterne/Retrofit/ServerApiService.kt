package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Retrofit

import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.http.*


/**
 * Classe utilisé pour contenir tous les corps de requête.
 * Ce sont ces méthodes qui doivent être appellés pour l'instantion du Call pour les requêtes.
 * Méthode type :
 * @ précède le typ de requête voulu.
 * Peut-être utilisé avec pour exemple : GET, POST, PUT, PATCH.
 * Suit ensuite soit :<br></br>
 * - Une partie de l'URL, le début étant donnée dans l'instanciation de retrofit.<br></br>
 * - La partie entière de l'URL si une partie n'ai pas donnée lors de l'instanciation de retrofit.<br></br>
 * - Rien si @Url est utilisé comme paramètre de la méthode.<br></br><br></br>
 *
 * La réponse attendu de la requête, pouvant aller de Call&lt;void&gt; à Call&lt;List&lt;T&gt;&gt;
 * en passant par Call&lt;T&gt;.<br></br>
 *
 * Nom de la méthode :
 *
 * Paramètre(s) : @Path, @Body, @Field ou autres.
 *
 */
interface ServerApiService {

    @GET("adressse")
    fun getAllSondages(): Call<List<Sondage>>


    @GET(".")
    fun getTestConnexion(): Call<ModeleSuccessConnexion>


    @GET("sondagesPublies")
    fun getSondagesPublics() : Call<List<ModeleSondagePossible>>


    @GET("questionsDuSondage/{temp}")
    fun getQuestionsDuSondage(@Path("temp") Variable: String): Call<List<ModeleQuestion>>


    @GET("lesChoixParQuestion/{sondage}/{question}")
    fun getChoixParQuestionParSondage(@Path("sondage") Sondage: String, @Path("question") Question: String) : Call<List<ModeleChoix>>


    @GET("lesChoixParSondage/{sondage}")
    fun getChoixPArSondage(@Path("sondage") Sondage: String) : Call<List<ModeleChoix>>

    @GET("sondagePublie/{sondage}")
    fun getSondage(@Path("sondage") Sondage: String) : Call<ModeleSondagePossible>

    @FormUrlEncoded
    @POST("repondre")
    fun EnvoieReponse(@Field("id_utilisateur") id_utilisateur: Int,
                      @Field("id_sondage") id_sondage: Int,
                      @Field("id_question") id_question: Int,
                      @Field("reponse") reponse: String): Call<Reponse>


    @FormUrlEncoded
    @POST("newUser")
    fun EnvoieNouveauUtilisateur(@Field("email")email: String,
                                 @Field("adresseIP")adresseIP : String
    )



    /**
     * Requête pour prendre les informations de toutes personnes présentes.
     * Le 1 permet d'indiquer que la requête vient de l'application et non de l'écran.
     *
     * @return réponse de la requête
     */
//    @GET("/controlleur/listePersonne/1")
//    fun RecoitPersonnes(): Call<List<ModeleUtilisateur>>

    /**
     * Requête envoyant les changements d'heures de séances et capacité d'accueil
     * Prend en Path le string commposé de la capacité / heure  / 1
     * Attend un retour de type String pour avoir des informations sur l'intéraction avec la base de données
     *
     *
     * @param capacite Nouvelle capacité à envoyé au serveur
     * @param temps Nouvelle durée minimal pour une séance
     * @param id identifiant de la séance
     * @return Réponse du serveur
     */
//    @FormUrlEncoded
//    @POST("controlleur/setSeance")
//    fun EnvoieTempsCapacite(
//        @Field("capacite") capacite: String,
//        @Field("temps") temps: String,
//        @Field("id"/) id: String
//    ): Call<ReponseRequete>

    /**
     *
     * Requête pour prendre les données de la base de données à propos des paramètres de la séance
     * pour pouvoir les mettre à jour sur l'affichage.
     *
     * @return réponse de la requête
     */
//    @GET("/controlleur/sendSeance")
//    fun RecoitParametre(): Call<List<AuaListeSeance>>

    /**
     * Requête pour enlever quelqu'un selon son numero id
     * Prend en Body et en Path son numéro id
     *
     *
     * @param Variable Contient l'id de l'étudiant pour la séance actuelle (à confirmer)
     * @param IDEtudiant Identifiant de l'étudiant pour le repérer dans la base de données
     * @return réponse de la requête
     */
//    @POST("/controlleur/vuePresenceUpdate/{temp}")
//    fun EnleverPersonne(@Path("temp") Variable: String, @Body IDEtudiant: NumeroIDCarteEtudiant): Call<Void>

    /**
     * Requête pour envoyer une personne badgeant avec son numéro de carte
     * Prend en Field et en Path son numéro de carte
     * Attend un retour de type String pour avoir des informations sur l'intéraction avec la base de données
     *
     * @param numeroCarte numéro MIFARE inverse de la carte qui sert d'identifiant
     * @return réponse de la requête
     */
//    @FormUrlEncoded
//    @POST("/controlleur/badgeage")
//    fun EnvoieNumCarte(@Field("numeroCarte") numeroCarte: String): Call<ReponseRequete>

    /**
     * Requête pour envoyer une personne ajouter manuellement avec son nom
     * Prend en Field et en Path son nom
     *
     * @param nom Nom de l'étudiant
     * @param prenom Prénom de l'étudiant
     * @return réponse de la requête
     */
//    @FormUrlEncoded
//    @POST("/controlleur/addPersonne")
//    fun EnvoieNom(
//        @Field("nom") nom: String,
//        @Field("prenom") prenom: String
//    ): Call<ReponseRequete>

    /**
     * Requête utilisé pour tester si l'adresse IP actuelle est celle du serveur.
     * Ne prend aucun paramètre, ne fais rien d'autre que d'assayer d'atteindre la route pour le test.
     *
     * @return réponse de la requête
     */
//    @GET("/controlleur/connexion")
//    fun TestBonneAdresseIP(): Call<ReponseRequete>

}