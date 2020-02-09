require 'singleton'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class UtilisateurService

    include Singleton

    # selectionner tout les Utilisateurs
    def listeDesUtilisateurs
        utilisateurs = Utilisateur.where(etat: false).order('created_at ASC')
    end

    # Afficher un Utilisateur par ID
    def afficherUtilisateurParId(id_utilisateur)
        utilisateur = Utilisateur.find_by(id_utilisateur: id_utilisateur, etat: false)
    end

    # Creer un nouveau Utilisateur
    def creerNouveauUtilisateur(email, adresseIp)

            utilisateur = Utilisateur.new(:email => email, :adresseIp => adresseIp)

            if utilisateur.save
                newUtilisateur = utilisateur
            else
                newUtilisateur = nil
            end

    end

    # Modifier un Utilisateur
    def modifierUtilisateur(id_utilisateur, email, adresseIp)
        utilisateur = Utilisateur.find_by(id_utilisateur: id_utilisateur, etat: false)

        if utilisateur != nil && utilisateur.update_attributes(:email => email, :adresseIp => adresseIp)
            modifier = utilisateur
        else
            modifier = nil
        end

    end

    # Supprimer un Utilisateur par ID
    def supprimerUtilisateur(id_utilisateur)
        utilisateur = Utilisateur.find_by(id_utilisateur: id_utilisateur, etat: false)
        supprimer = (utilisateur != nil && utilisateur.update_attributes(:etat => true))
    end

end