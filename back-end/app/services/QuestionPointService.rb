require 'singleton'
require 'ParticiperService'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class QuestionPointService

    include Singleton

    # selectionner tout les QuestionPoint
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
                                        :minPoints => minPoints,
                                        :maxPoints => maxPoints,
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
    def supprimerQuestion(id_question)
        question = QuestionPoint.find_by(id_question: id_question, etat: false)
        supprimer = (question != nil && question.update_attributes(:etat => true))
    end

    # afficher toutes les moyennes des question à point d'un sondage 
    def questionsPointsMoyennes(id_sondage)

        # associer pour chaque question à point sa moyenne générale
        hash = Hash.new

        # récupérer la liste des QuestionPoint par sondage
        # et traiter chaque QuestionPoint
        QuestionPoint.where(sondage_id: id_sondage, etat: false).find_each do |question|
            moy = ParticiperService.instance.moyenneParticipationsParQuestionAPointsEtParSondage(question.id_question, id_sondage)
            hash[question.id_question.to_s] = moy
        end
        
        arr = hash

    end

end