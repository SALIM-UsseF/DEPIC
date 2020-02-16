package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Retrofit

import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.*
import retrofit2.Call
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
    // Pour la connexion
    @GET(".")
    fun getTestConnexion(): Call<ModeleSuccessConnexion>



    // Pour les sondages
    @GET("sondagesPublies")
    fun getAllSondages() : Call<ArrayList<Sondage>>

    @GET("sondagePublie/{sondage}")
    fun getSondage(@Path("sondage") Sondage: String) : Call<Sondage>



    @GET("questionsDuSondage/{temp}")
    fun getQuestionsDuSondage(@Path("temp") Variable: String): Call<ArrayList<Question>>


    // Pour les sous-questions
    @GET("questionsDuGroupe/{idQuestionGroupe}")
    fun getSousQuestionsDeQuestionGroupe(@Path("idQuestionGroupe") Variable: String): Call<ArrayList<Question>>


    // Pour les choix
    @GET("TRUC")
    fun getAllChoix(): Call<ArrayList<Choix>>

    @GET("lesChoixParQuestion/{sondage}/{question}")
    fun getChoixParQuestionParSondage(@Path("sondage") Sondage: String, @Path("question") Question: String) : Call<List<ModeleChoix>>

    @GET("lesChoixParSondage/{sondage}")
    fun getChoixParSondage(@Path("sondage") Sondage: String) : Call<ArrayList<Choix>>

    @GET("categories")
    fun getAllCategories() : Call<ArrayList<Categorie>>


    // Les envoies de données
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
    ) : Call<Utilisateur>


    @FormUrlEncoded
    @POST("authenticate")
    fun authentification(@Field("email") email: String,
                        @Field("password") password: String) : Call<TokenAuthentification>

}