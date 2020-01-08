require 'singleton'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class QuestionService

    include Singleton

    # selectionner que les Questions non supprimés (etat=false)
    def listeDesQuestions
        questions = Question.where(etat: false).order('sondage_id ASC, ordre ASC');
    end

    # Afficher une Question par ID
    def afficherQuestionParId(id_question)
        questions = Question.find_by(id_question: id_question, etat: false);
    end

    # Afficher les questions d'un sondage
    def afficherQuestionsParSondage(id_sondage)
        questions = Question.where(sondage_id: id_sondage, etat: false);
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

end