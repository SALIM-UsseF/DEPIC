require 'singleton'
require 'QuestionService'
require 'QuestionPointService'
require 'ParticiperService'
require 'QuestionChoixService'


# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class ResultatService

    include Singleton

    
    

    def nombreQuestions(id_sondage)

        nombreQuestions=QuestionService.instance.nombreQuestionParSondage(id_sondage)

    end


    def nombreUtilisateurs(id_sondage)
    nombreUtilisateurs= ParticiperService.instance.nombreUtilisateurParSondage(id_sondage)


    end

    def resultats(id_sondage)

        ############# récupérer le nombre de questions pour un sondage
        
        nbrQuestions=nombreQuestions(id_sondage)

        ############ récupérer le nombre de participations dans un sondage

        nbrUtilisateur=nombreUtilisateurs(id_sondage) 
        

        ########### Moyennes des questions à point

        arrayMoy= QuestionPointService.instance.questionsPointsMoyennes(id_sondage)

        ########### Nombre de réponse pour chaque choix d'une question à choix unique

        questionsChoixUnique= QuestionChoixService.instance.questionsChoixUnique(id_sondage) # en vérifiant l'atribut estUnique= true

        ########### Nombre de réponse pour chaque choix d'une question à choix multiple

        questionsChoixMultiple= QuestionChoixService.instance.questionsChoixMultiple(id_sondage) 

        hash=Hash.new

        hash["id_sondage"]=id_sondage.to_i
        hash["nombre de questions"]=nbrQuestions
        hash["nombre de participations"]=nbrUtilisateur
        hash["Moyennes generales des questions a points"]=arrayMoy
        hash["nombre de participations sur chaque choix des questions a choix unique"]=questionsChoixUnique
        hash["nombre de participations sur chaque choix des questions a choix multiple"]=questionsChoixMultiple
        resultatHash=hash






    end

end