require 'singleton'
require 'QuestionService'
require 'QuestionPointService'
require 'ParticiperService'
require 'QuestionChoixService'


class ResultatService

    include Singleton

    # renvoie le nombre de questions d'un sondage
    def nombreQuestions(id_sondage)
        nombreQuestions = QuestionService.instance.nombreQuestionParSondage(id_sondage)
    end

    # renvoie le nombre des utilisateurs d'un sondage
    def nombreUtilisateurs(id_sondage)
        nombreUtilisateurs= ParticiperService.instance.nombreUtilisateurParSondage(id_sondage)
    end

    # renvoie les resultats d'un sondage
    def resultats(id_sondage)

        # récupérer le nombre de questions pour un sondage
        nbrQuestions = nombreQuestions(id_sondage)

        # récupérer le nombre de participations dans un sondage
        nbrUtilisateur = nombreUtilisateurs(id_sondage) 
        
        # Moyennes des questions à point
        arrayMoy = QuestionPointService.instance.questionsPointsMoyennes(id_sondage)

        # Nombre de réponse pour chaque choix d'une question à choix unique
        questionsChoixUnique = QuestionChoixService.instance.questionsChoixUnique(id_sondage) # en vérifiant l'atribut estUnique= true

        # Nombre de réponse pour chaque choix d'une question à choix multiple
        questionsChoixMultiple = QuestionChoixService.instance.questionsChoixMultiple(id_sondage) 

        hash = []

        res = {
                id_sondage: id_sondage.to_i,
                nombre_de_questions: nbrQuestions,
                nombre_de_participations: nbrUtilisateur,
                moyennes_generales_des_questions_a_points:  arrayMoy,
                nombre_de_participations_sur_chaque_choix_des_questions_a_choix_unique: questionsChoixUnique,
                nombre_de_participations_sur_chaque_choix_des_questions_a_choix_multiple: questionsChoixMultiple
        }

        hash << res
        
        resultatHash = hash

    end

    # renvoie les resultats d'un sondage publié
    def resultatsPublie(id_sondage)
        if SondageService.instance.checkResultats(id_sondage)
            resultats = resultats(id_sondage)
        end
    end

end