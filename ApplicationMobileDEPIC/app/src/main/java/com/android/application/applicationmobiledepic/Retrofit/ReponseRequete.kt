package com.android.application.applicationmobiledepic.Retrofit

class ReponseRequete
/**
 * Constructeur initialisant la reponse.
 * @param reponse : Le résultat informatif de la requête.
 */
    (/*
     * Résultat de la requête formulé explicitement par la base de données,
     * N'a un but qu'informatif.
     */
    private val reponse: String?
) {

    /**
     * Méthode renvoyant reponse.
     * @return : résultat informatif de la requête.
     */
    fun getReponse(): String {
        try {
            if (this.reponse != null) {
                return this.reponse
            }
        } catch (e: Exception) {
            return "null"
        }

        return "null"
    }

}