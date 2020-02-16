require 'singleton'
require 'ParticiperService'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class QuestionChoixService

    include Singleton

    # selectionner tout les QuestionChoix
    def listeDesQuestions
        questions = QuestionChoix.where(etat: false).order('sondage_id ASC, ordre ASC')
    end

    # Afficher une QuestionChoix par ID
    def afficherQuestionParId(id_question)
        questions = QuestionChoix.find_by(id_question: id_question, etat: false)
    end

    # Creer une QuestionChoix
    def creerQuestionChoix(intitule, estObligatoire, estUnique, ordre, sondage_id)

        question = QuestionChoix.new(:intitule => intitule,
                                        :estObligatoire => estObligatoire,
                                        :estUnique => estUnique,
                                        :ordre => ordre,
                                        :sondage_id => sondage_id)

        if question.save
            newQuestion = question
        else
            newQuestion = nil
        end

    end

    # Modifier une QuestionChoix
    def modifierQuestion(id_question, intitule, estObligatoire, estUnique, ordre)
        question = QuestionChoix.find_by(id_question: id_question, etat: false);

        if question != nil && question.update_attributes(:intitule => intitule,
                                                        :estObligatoire => estObligatoire,
                                                        :estUnique => estUnique,
                                                        :ordre => ordre)
            modifier = question
        else
            modifier = nil
        end

    end

    # Supprimer une QuestionChoix par ID
    def supprimerQuestion(id_question)
        question = QuestionChoix.find_by(id_question: id_question, etat: false)
        supprimer = (question != nil && question.update_attributes(:etat => true))

        # supprimer tout les choix de cette question
        if supprimer
            choix = Choix.where(question_id: id_question)
            choix.update(etat: true)
        end

    end


    # Récupérer le nombre de participations sur chaque choix effectué pour toutes les question à choix unique d'un sondage
    def questionsChoixUnique(id_sondage)

        arry = Array.new

        # récupérer la liste des QuestionChoix de type choix unique par sondage
        # et traiter chaque QuestionChoix
        QuestionChoix.where(sondage_id: id_sondage, estUnique: true, etat: false).find_each do |question|
            participations = ParticiperService.instance.ParticipationsParQuestionChoixUniqueEtParSondage(question.id_question, id_sondage)
            arry << participations 
        end

        resultatChoix = arry

    end

    # Récupérer le nombre de participations sur chaque choix effectué pour toutes les question à choix multiple d'un sondage
    def questionsChoixMultiple(id_sondage)
        
        arry = Array.new

        # récupérer la liste des QuestionChoix de type choix multiple par sondage
        # et traiter chaque QuestionChoix
        QuestionChoix.where(sondage_id: id_sondage, estUnique: false, etat: false).find_each do |question|
            participations=ParticiperService.instance.ParticipationsParQuestionChoixMultipleEtParSondage(question.id_question, id_sondage)
            arry << participations 
        end

        resultatChoix = arry

    end


end