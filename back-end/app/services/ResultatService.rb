require 'singleton'



# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class ResultatService

    include Singleton

    require 'QuestionService'
    

    def nombreQuestions(id_sondage)

        nombreQuestions=QuestionService.instance.nombreQuestionParSondage(id_sondage)

    end


    def nombreUtilisateurs(id_sondage)
    nombreUtilisateurs= ParticiperService.instance.nombreUtilisateurParSondage(id_sondage)


    end

    def Resultats(id_sondage)

        ############# récupérer le nombre de questions pour un sondage
        
        nbrQuestions=nombreQuestions(id_sondage)

        ############ récupérer le nombre de participations dans un sondage

        nbrUtilisateur=nombreUtilisateurs(id_sondage) 
        

        ########### Taux de réponse pour chaque choix d'une question à choix unique

        #questionsChoixUnique= QuestionChoix.instance.questionsChoixUnique(id_sondage) # en vérifiant l'atribut estUnique= true










    end

end