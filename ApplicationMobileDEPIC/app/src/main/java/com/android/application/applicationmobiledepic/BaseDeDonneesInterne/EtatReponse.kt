package com.android.application.applicationmobiledepic.BaseDeDonneesInterne

/**
 * Classe d'énumération utilisé pour les différents états possibles d'une réponse.
 * Elle est créée "A_REPONDRE"
 * Une fois qu'elle est enregistrée mais pas envoyée, elle passe à "ENREGISTRER"
 * Une fois qu'elle est envoyée, elle passe à "ENVOYER"
 */
enum class EtatReponse {
    ENVOYER,
    ENREGISTRER,
    A_REPONDRE
}