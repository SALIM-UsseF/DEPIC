require 'singleton'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class QuestionChoixService

    include Singleton

    # selectionner que les QuestionChoix non supprimés (etat=false)
    def listeDesQuestions
        questions = QuestionChoix.where(etat: false).order('sondage_id ASC, ordre ASC');
    end

    # Afficher une QuestionChoix par ID
    def afficherQuestionParId(id_question)
        questions = QuestionChoix.find_by(id_question: id_question, etat: false);
    end

    # Creer une QuestionChoix
    def creerQuestionChoix(intitule, estObligatoire, estUnique, nombreChoix, ordre, sondage_id)

        question = QuestionChoix.new(:intitule => intitule,
                                        :estObligatoire => estObligatoire,
                                        :estUnique => estUnique,
                                        :nombreChoix => nombreChoix,
                                        :ordre => ordre,
                                        :sondage_id => sondage_id)

        if question.save
            newQuestion = question
        else
            newQuestion = nil
        end

    end

    # Modifier une QuestionChoix
    def modifierQuestion(id_question, intitule, estObligatoire, estUnique, nombreChoix, ordre)
        question = QuestionChoix.find_by(id_question: id_question, etat: false);

        if question != nil && question.update_attributes(:intitule => intitule,
                                                        :estObligatoire => estObligatoire,
                                                        :estUnique => estUnique,
                                                        :lesChoix => nombreChoix,
                                                        :ordre => ordre)
            modifier = question
        else
            modifier = nil
        end

    end

    # Supprimer une QuestionChoix par ID
    def supprimerQuestion(id_question, etat)
        question = QuestionChoix.find_by(id_question: id_question, etat: false);

        if question != nil && question.update_attributes(:etat => etat)
            supprimer = true
        else
            supprimer = false
        end

    end


    def questionsChoixUnique(id_sondage)

        questionsChoixUnique=QuestionChoix.where(sondage_id: id_sondage, estUnique: true, etat: false)


    end


end