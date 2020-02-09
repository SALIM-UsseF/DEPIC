require 'singleton'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class SondageService

    include Singleton

    # selectionner tout les sondages
    def listeDesSondages
        sondages = Sondage.where(etat: false).order('created_at ASC')
    end

    # selectionner tout les sondages selon une categorie
    def listeDesSondagesParCategorie(categorie_id)
        sondages = Sondage.where(etat: false, categorie_id: categorie_id).order('created_at ASC')
    end

    # selectionner les sondages publiés (publier=true)
    def listeDesSondagesPublies
        sondages = Sondage.where(etat: false, publier: true).order('created_at DESC')
    end

    # selectionner les sondages publiés (publier=true) par categorie
    def listeDesSondagesPubliesParCategorie(categorie_id)
        sondages = Sondage.where(etat: false, publier: true, categorie_id: categorie_id).order('created_at DESC')
    end

    # Afficher un Sondage par ID
    def afficherSondageParId(id_sondage)
        sondage = Sondage.find_by(id_sondage: id_sondage, etat: false)
    end

    # Afficher un Sondage publié
    def afficherSondagePublie(id_sondage)
        sondage = Sondage.find_by(id_sondage: id_sondage, etat: false, publier: true)
    end

    # Creer un nouveau Sondage
    def creerNouveauSondage(intituleSondage, descriptionSondage, categorie_id, administrateur_id)

            sondage = Sondage.new(:intituleSondage => intituleSondage, :descriptionSondage => descriptionSondage, :categorie_id => categorie_id, :administrateur_id => administrateur_id)

            if sondage.save
                newSondage = sondage
            else
                newSondage = nil
            end

    end

    # Modifier un Sondage
    # si resultats = true => les utilisateurs ont le droit de voir les resultats sondage
    def modifierSondage(id_sondage, intituleSondage, descriptionSondage, publier, resultats, categorie_id)
        sondage = Sondage.find_by(id_sondage: id_sondage, etat: false);

        if sondage != nil && sondage.update_attributes(:intituleSondage => intituleSondage, :descriptionSondage => descriptionSondage, :publier => publier, :resultats => resultats, :categorie_id => categorie_id)
            modifier = sondage
        else
            modifier = nil
        end

    end

    # Publier un Sondage
    def publierSondage(id_sondage, publier)
        sondage = Sondage.find_by(id_sondage: id_sondage, etat: false)

        if sondage != nil && sondage.update_attributes(:publier => publier)
            modifier = sondage
        else
            modifier = nil
        end

    end

    # Activer les resultats d'un Sondage
    def activerResultats(id_sondage, resultats)
        sondage = Sondage.find_by(id_sondage: id_sondage, etat: false, publier: true)

        if sondage != nil && sondage.update_attributes(:resultats => resultats)
            modifier = sondage
        else
            modifier = nil
        end

    end

    # Vérifier si le sondage est publié
    def estPublie(id_sondage)
        ((Sondage.find_by(id_sondage: id_sondage, etat: false, publier: true)) != nil) ? (publie = true) : (publie = false)
    end

    # Vérifier si les résultats d'un sondage publié sont disponible
    def checkResultats(id_sondage)
        ((Sondage.find_by(id_sondage: id_sondage, etat: false, publier: true, resultats: true)) != nil) ? (resultats = true) : (resultats = false)
    end
    
    # Supprimer un Sondage par ID
    def supprimerSondage(id_sondage)
        sondage = Sondage.find_by(id_sondage: id_sondage, etat: false)
        supprimer = (sondage != nil && sondage.update_attributes(:etat => true))
    end

end