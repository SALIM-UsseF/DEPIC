package com.android.application.applicationmobiledepic.BaseDeDonneesInterne

/**
 * Classe d'énumération utilisé pour les différents états possibles d'un sondage.
 * il est créé "DISPONIBLE"
 * Une fois qu'il est enregistré mais pas envoyé, il passe à "REPONDU"
 * Une fois qu'il est envoyé, il passe à "ENVOYE"
 */
enum class EtatSondage {
    ENVOYE,
    REPONDU,
    DISPONIBLE
}