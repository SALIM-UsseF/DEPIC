require 'singleton'



# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class ResultatService

    include Singleton


    
    def Resultats(id_sondage)

        ############# récupérer le nombre de questions

        questions=QuestionService.instance.nombreQuestionParSondage(id_sondage)


        ############ récupérer le nombre de participations dans un sondage

        utilisateurs= participerService.instance.nombreUtilisateurParSondage(id_sondage)

        ###########         






    end

end