require 'singleton'
require 'ParticiperService'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class QuestionPointService

    include Singleton

    # selectionner que les QuestionPoint non supprimés (etat=false)
    def listeDesQuestions
        questions = QuestionPoint.where(etat: false).order('sondage_id ASC, ordre ASC')
    end

    # Afficher une QuestionPoint par ID
    def afficherQuestionParId(id_question)
        questions = QuestionPoint.find_by(id_question: id_question, etat: false)
    end

    # Creer une QuestionPoint
    def creerQuestionPoint(intitule, estObligatoire, minPoints, maxPoints, ordre, sondage_id)

        question = QuestionPoint.new(:intitule => intitule,
                                        :estObligatoire => estObligatoire,
                                        :minPoints => estUnique,
                                        :maxPoints => lesChoix,
                                        :ordre => ordre,
                                        :sondage_id => sondage_id)

        if question.save
            newQuestion = question
        else
            newQuestion = nil
        end

    end

    # Modifier une QuestionPoint
    def modifierQuestion(id_question, intitule, estObligatoire, minPoints, maxPoints, ordre)
        question = QuestionPoint.find_by(id_question: id_question, etat: false);

        if question != nil && question.update_attributes(:intitule => intitule,
                                                        :estObligatoire => estObligatoire,
                                                        :minPoints => minPoints,
                                                        :maxPoints => maxPoints,
                                                        :ordre => ordre)
            modifier = question
        else
            modifier = nil
        end

    end

    # Supprimer une QuestionPoint par ID
    def supprimerQuestion(id_question, etat)
        question = QuestionPoint.find_by(id_question: id_question, etat: false)

        supprimer = (question != nil && question.update_attributes(:etat => etat))

    end


    def questionsPoints(id_sondage)
        arry = Array.new
        questionsPoints=QuestionPoint.where(sondage_id: id_sondage, etat: false)
        questionsPoints.each do |question|

        moy=ParticiperService.instance.moyenneParticipationsParQuestionEtParSondage(question.id_question, id_sondage)
        arry << moy   

        end


    end

end