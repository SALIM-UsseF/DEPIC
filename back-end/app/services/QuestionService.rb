require 'singleton'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class QuestionService

    include Singleton

    # selectionner tout les Questions
    def listeDesQuestions
        questions = Question.where(etat: false).order('sondage_id ASC, ordre ASC')
    end

    # selectionner tout les questions des sondages publies (publier = true)
    def listeDesQuestionsPublies

        # array contient la liste des questions publies
        questionsPublies = Array.new

        # récupérer la liste des sondages publies
        # et traiter chaque sondage
        Sondage.where(publier: true, etat: false).order('id_sondage DESC').find_each do |sondage|

            # récupérer la liste des questions
            # et traiter chaque question
            Question.where(sondage_id: sondage.id_sondage, etat: false).order('sondage_id ASC, ordre ASC').find_each do |question|
                questionsPublies << question
            end

        end
        
        questions = questionsPublies

    end

    # Afficher une Question par ID
    def afficherQuestionParId(id_question)
        questions = Question.find_by(id_question: id_question, etat: false)
    end

    # Afficher les questions d'un sondage
    def afficherQuestionsParSondage(id_sondage)
        questions = Question.where(sondage_id: id_sondage, etat: false)
    end

    # Afficher les questions d'un sondage publié
    def afficherQuestionsParSondagePublie(id_sondage)

        sondage = Sondage.where(id_sondage: id_sondage, etat: false, publier: true)

        if sondage.empty?
            questions = nil
        else
            questions = Question.where(sondage_id: id_sondage, etat: false)
        end

    end

    # Afficher une question d'un sondage publié
    def questionDuSondagePublie(id_sondage, id_question)
        sondage = Sondage.where(id_sondage: id_sondage, etat: false, publier: true)

        if sondage.empty?
          question = nil
        else
          question = Question.find_by(id_question: id_question, etat: false)
        end

    end

    # Nombre de questions que contient un sondage
    def nombreQuestionParSondage(id_sondage)
        nombreQuestions= Question.where(sondage_id: id_sondage, etat: false).count
    end

end