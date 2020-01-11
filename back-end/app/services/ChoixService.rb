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

    # Afficher les Choix d'un sondage publié
    def afficherLesChoixParSondagePublie(id_sondage)
        if SondageService.instance.estPublie(id_sondage)

            sql = 'SELECT id_choix, "intituleChoix", questions.id_question
                   FROM choixes
                   INNER JOIN questions ON (choixes.question_id = questions.id_question)
                   INNER JOIN sondages ON (questions.sondage_id = sondages.id_sondage)
                   WHERE questions.sondage_id = '"#{id_sondage}"'
                   AND choixes.etat = false
                   AND questions.etat = false;'
                   
            choix = ActiveRecord::Base.connection.execute(sql)

        else
            choix = nil
        end
    end

    # selectionner tout les choix des sondages publies (publier = true)
    def listeDesChoixPublies

        # array contient la liste des choix publies
        choixPublies = Array.new

        # récupérer la liste des sondages publies
        # et traiter chaque sondage
        Sondage.where(publier: true, etat: false).order('id_sondage DESC').find_each do |sondage|

            # récupérer la liste des questions
            # et traiter chaque question
            Question.where(sondage_id: sondage.id_sondage, etat: false).order('sondage_id ASC, ordre ASC').find_each do |question|

                # récupérer la liste des choix
                # et traiter chaque choix
                Choix.where(question_id: question.id_question, etat: false).order('id_choix ASC').find_each do |choix|
                    choixPublies << choix
                end

            end

        end
        
        lesChoix = choixPublies

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
        supprimer = (choix != nil && choix.update_attributes(:etat => etat)) 
    end

end