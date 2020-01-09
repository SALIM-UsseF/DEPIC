require 'singleton'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class GroupeQuestionService

    include Singleton

    # selectionner que les GroupeQuestion non supprimés (etat=false)
    def listeDesQuestions
        questions = GroupeQuestion.where(etat: false).order('sondage_id ASC, ordre ASC')
    end

    # Afficher un GroupeQuestion par ID
    def afficherQuestionParId(id_question)
        questions = GroupeQuestion.find_by(id_question: id_question, etat: false);
    end

    # Creer un nouveau GroupeQuestion
    def creerGroupeQuestion(intitule, estObligatoire, numerosDeQuestionsGroupe, ordre, sondage_id)

        question = GroupeQuestion.new(:intitule => intitule,
                                        :estObligatoire => estObligatoire,
                                        :numerosDeQuestionsGroupe => numerosDeQuestionsGroupe,
                                        :ordre => ordre,
                                        :sondage_id => sondage_id)

        if question.save
            newQuestion = question
        else
            newQuestion = nil
        end

    end

    # Modifier un GroupeQuestion
    def modifierQuestion(id_question, intitule, estObligatoire, numerosDeQuestionsGroupe, ordre)
        question = GroupeQuestion.find_by(id_question: id_question, etat: false);

        if question != nil && question.update_attributes(:intitule => intitule,
                                                        :estObligatoire => estObligatoire,
                                                        :numerosDeQuestionsGroupe => numerosDeQuestionsGroupe,
                                                        :ordre => ordre)
            modifier = question
        else
            modifier = nil
        end

    end

    # Supprimer un GroupeQuestion par ID
    def supprimerQuestion(id_question, etat)
        question = GroupeQuestion.find_by(id_question: id_question, etat: false)
        supprimer = (question != nil && question.update_attributes(:etat => etat))
    end

end