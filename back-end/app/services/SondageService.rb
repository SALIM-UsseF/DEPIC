require 'singleton'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class SondageService

    include Singleton

    # selectionner que les sondages non supprimés (etat=false)
    def listeDesSondages
        sondages = Sondage.where(etat: false).order('created_at ASC');
    end

    # selectionner les sondages publiés (publier=true)
    def listeDesSondagesPublies
        sondages = Sondage.where(etat: false, publier: true).order('created_at DESC');
    end

    # Afficher un Sondage par ID
    def afficherSondageParId(id_sondage)
        sondage = Sondage.find_by(id_Sondage: id_sondage, etat: false);
    end

    # Afficher un Sondage publié
    def afficherSondagePublie(id_sondage)
        sondage = Sondage.find_by(id_Sondage: id_sondage, etat: false, publier: true);
    end

    # Creer un nouveau Sondage
    def creerNouveauSondage(intituleSondage, descriptionSondage, administrateur_id)

            sondage = Sondage.new(:intituleSondage => intituleSondage, :descriptionSondage => descriptionSondage, :administrateur_id => administrateur_id)

            if Sondage.save
                newSondage = Sondage
            else
                newSondage = nil
            end

    end

    # Modifier un Sondage
    # resultats = true => les utilisateurs ont le droit de voir les resultats sondage
    def modifierSondage(id_sondage, intituleSondage, descriptionSondage, publier, resultats)
        sondage = Sondage.find_by(id_Sondage: id_sondage, etat: false);

        if sondage != nil && Sondage.update_attributes(:intituleSondage => intituleSondage, :descriptionSondage => descriptionSondage, :publier => publier, :resultats => resultats)
            modifier = sondage
        else
            modifier = nil
        end

    end

    # Publier un Sondage
    def publierSondage(id_sondage, publier)
        sondage = Sondage.find_by(id_Sondage: id_sondage, etat: false);

        if sondage != nil && Sondage.update_attributes(:publier => publier)
            modifier = sondage
        else
            modifier = nil
        end

    end

    # Activer les resultats d'un Sondage
    def activerResultats(id_sondage, resultats)
        sondage = Sondage.find_by(id_Sondage: id_sondage, etat: false, publier: true);

        if sondage != nil && Sondage.update_attributes(:resultats => resultats)
            modifier = sondage
        else
            modifier = nil
        end

    end

    # Supprimer un Sondage par ID
    def supprimerSondage(id_sondage, etat)
        sondage = Sondage.find_by(id_sondage: id_sondage, etat: false);

        if sondage != nil && Sondage.update_attributes(:etat => etat)
            supprimer = true
        else
            supprimer = false
        end

    end

end