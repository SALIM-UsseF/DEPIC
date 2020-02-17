package com.android.application.applicationmobiledepic.Retrofit

import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.*
import retrofit2.Call
import retrofit2.http.*


/**
 * Classe utilisé pour contenir tous les corps de requête.
 * Ce sont ces méthodes qui doivent être appellés pour l'instantion du Call pour les requêtes.
 * Méthode type :
 * @ précède le type de requête voulu.
 * Peut-être utilisé avec pour exemple : GET, POST, PUT, PATCH.
 * Suit ensuite soit :
 * - Une partie de l'URL, le début étant donnée dans l'instanciation de retrofit.
 * - La partie entière de l'URL si une partie n'ai pas donnée lors de l'instanciation de retrofit.
 * - Rien si @Url est utilisé comme paramètre de la méthode.
 *
 * La réponse attendu de la requête, pouvant aller de Call<void>; à Call<List<T>>
 * en passant par Call<T>;
 * Avec T représentant une classe.
 *
 * Nom de la méthode :
 * Paramètre(s) : @Path, @Body, @Field ou autres.
 *
 */
interface ServerApiService {
    // Pour tester la connexion au serveur
    @GET(".")
    fun getTestConnexion(): Call<ModeleSuccessConnexion>



    // Pour les sondages
    // Demande tous les sondages
    @GET("sondagesPublies")
    fun getAllSondages() : Call<ArrayList<Sondage>>

    // Demande un sondage
    @GET("sondagePublie/{sondage}")
    fun getSondage(@Path("sondage") Sondage: String) : Call<Sondage>



    // Pour les question et sous-questions
    // Demande les questions d'un sondage
    @GET("questionsDuSondage/{temp}")
    fun getQuestionsDuSondage(@Path("temp") Variable: String): Call<ArrayList<Question>>

    // Demande les sous-question d'une question
    @GET("questionsDuGroupe/{idQuestionGroupe}")
    fun getSousQuestionsDeQuestionGroupe(@Path("idQuestionGroupe") Variable: String): Call<ArrayList<Question>>


    // Les choix
    // Demande tout les choix
    @GET("TRUC")
    fun getAllChoix(): Call<ArrayList<Choix>>

    // Demande le choix pour un question et un sondage
    @GET("lesChoixParQuestion/{sondage}/{question}")
    fun getChoixParQuestionParSondage(@Path("sondage") Sondage: String, @Path("question") Question: String) : Call<List<Choix>>

    // Demande les choix d'un sondage.
    @GET("lesChoixParSondage/{sondage}")
    fun getChoixParSondage(@Path("sondage") Sondage: String) : Call<ArrayList<Choix>>


    // Les catégories
    // Demande toutes les catégories
    @GET("categories")
    fun getAllCategories() : Call<ArrayList<Categorie>>


    // L'envoi de données
    @FormUrlEncoded
    @POST("repondre")
    fun EnvoieReponse(@Field("id_utilisateur") id_utilisateur: Int,
                      @Field("id_sondage") id_sondage: Int,
                      @Field("id_question") id_question: Int,
                      @Field("reponse") reponse: String): Call<Reponse>


    // Pour la création/enregistrement de l'utilisateur
    @FormUrlEncoded
    @POST("newUser")
    fun EnvoieNouveauUtilisateur(@Field("email")email: String,
                                 @Field("adresseIP")adresseIP : String
    ) : Call<Utilisateur>


    // Pour l'authentification
    @FormUrlEncoded
    @POST("authenticate")
    fun authentification(@Field("email") email: String,
                        @Field("password") password: String) : Call<TokenAuthentification>

}