require 'singleton'
require 'SondageService'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class ChoixService

    include Singleton

    # selectionner que les Choix non supprimés (etat=false)
    def listeDesChoix
        choix = Choix.where(etat: false).order('created_at ASC')
    end

    # Afficher un Choix par ID
    def afficherChoixParId(id_choix)
        choix = Choix.find_by(id_choix: id_choix, etat: false)
    end

    # Afficher les Choix pour une Question donnée
    def afficherLesChoixParQuestion(id_question)
        choix = Choix.where(question_id: id_question, etat: false).order('id_choix ASC')
    end

    # Afficher les Choix pour une Question donnée d'un sondage publié
    def afficherLesChoixParQuestionPublie(id_sondage, id_question)
        if SondageService.instance.estPublie(id_sondage)
            choix = Choix.where(question_id: id_question, etat: false).order('id_choix ASC')
        else
            choix = nil
        end
    end

    # Creer un nouveau Choix
    def creerNouveauChoix(intituleChoix, question_id)

            choix = Choix.new(:intituleChoix => intituleChoix, :question_id => question_id)

            if choix.save
                newChoix = choix
            else
                newChoix = nil
            end

    end

    # Modifier un Choix
    def modifierChoix(id_Choix, intituleChoix)
        choix = Choix.find_by(id_choix: id_choix, etat: false);

        if choix != nil && choix.update_attributes(:intituleChoix => intituleChoix)
            modifier = choix
        else
            modifier = nil
        end

    end

    # Supprimer un Choix par ID
    def supprimerChoix(id_choix, etat)
        choix = Choix.find_by(id_choix: id_choix, etat: false)

        if choix != nil && choix.update_attributes(:etat => etat)
            supprimer = true
        else
            supprimer = false
        end
        
    end

end