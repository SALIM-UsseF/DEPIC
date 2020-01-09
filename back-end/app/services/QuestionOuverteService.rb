require 'singleton'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class QuestionOuverteService

    include Singleton

    # selectionner que les QuestionOuverte non supprimés (etat=false)
    def listeDesQuestions
        questions = QuestionOuverte.where(etat: false).order('sondage_id ASC, ordre ASC')
    end

    # Afficher une QuestionOuverte par ID
    def afficherQuestionParId(id_question)
        questions = QuestionOuverte.find_by(id_question: id_question, etat: false)
    end

    # Creer une QuestionOuverte
    def creerQuestionOuverte(intitule, estObligatoire, nombreCaracteres, ordre, sondage_id)

        question = QuestionOuverte.new(:intitule => intitule,
                                        :estObligatoire => estObligatoire,
                                        :nombreDeCaractere => nombreCaracteres,
                                        :ordre => ordre,
                                        :sondage_id => sondage_id)

        if question.save
            newQuestion = question
        else
            newQuestion = nil
        end

    end

    # Modifier une QuestionOuverte
    def modifierQuestion(id_question, intitule, estObligatoire, nombreCaracteres, ordre)
        question = QuestionOuverte.find_by(id_question: id_question, etat: false);

        if question != nil && question.update_attributes(:intitule => intitule,
                                                        :estObligatoire => estObligatoire,
                                                        :nombreDeCaractere => nombreCaracteres,
                                                        :ordre => ordre)
            modifier = question
        else
            modifier = nil
        end

    end

    # Supprimer une QuestionOuverte par ID
    def supprimerQuestion(id_question, etat)
        question = QuestionOuverte.find_by(id_question: id_question, etat: false)
        supprimer = (question != nil && question.update_attributes(:etat => etat))
    end

end