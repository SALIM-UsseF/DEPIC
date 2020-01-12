require 'singleton'
require 'ChoixService'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class ParticiperService

    include Singleton

    # Afficher la liste des participations
    def listeDesParticipations
        participations = Participer.where(etat: false).order('created_at ASC, id_sondage DESC')

        if participations.empty?
            liste = nil
        else
            liste = participations
        end

    end

    # Afficher les participations d'un sondage
    def afficherParticipationsParSondage(id_sondage)
        participations = Participer.where(id_sondage: id_sondage, etat: false)

        if participations.empty?
            liste = nil
        else
            liste = participations
        end

    end

    # Afficher les participations d'un utilisateur pour un sondage
    def afficherParticipationsParUtilisateurEtParSondage(id_utilisateur, id_sondage)
        participations = Participer.where(id_utilisateur: id_utilisateur, id_sondage: id_sondage, etat: false)

        if participations.empty?
            liste = nil
        else
            liste = participations
        end

    end

    # Afficher les participations d'une question pour un sondage
    def afficherParticipationsParQuestionEtParSondage(id_question, id_sondage)
        participations = Participer.where(id_question: id_question, id_sondage: id_sondage, etat: false)

        if participations.empty?
            liste = nil
        else
            liste = participations
        end

    end

    # Creer une Participation
    def creerParticipation(id_utilisateur, id_sondage, id_question, reponse)

        participer = Participer.new(:id_utilisateur => id_utilisateur,
                                        :id_sondage => id_sondage,
                                        :id_question => id_question,
                                        :reponse => reponse)

        if participer.save
            newParticipation = participer
        else
            newParticipation = nil
        end

    end


    # Creer une participation pour un sondage publié
    def reponseUtilisateurPourSondagePublie(id_utilisateur, id_sondage, id_question, reponse)

      #verifier si le sondage est encore en publication
      sondage = Sondage.find_by(id_sondage: id_sondage, etat: false, publier: true)
      
      if sondage != nil
        creerParticipation(id_utilisateur, id_sondage, id_question, reponse)
      end

    end

    # Supprimer une Participation
    def supprimerParticipation(id_utilisateur, id_sondage, id_question, etat)
        participation = Participer.find_by(id_utilisateur: id_utilisateur, id_sondage: id_sondage, id_question: id_question, etat: false)
        supprimer = (participation != nil && participation.update_attributes(:etat => etat))
    end

    # nombre de participations sur un sondage
    def nombreUtilisateurParSondage(id_sondage)
        nombreUtilisateurs = Participer.where(id_sondage: id_sondage, etat: false).count
    end

    # Calculer la moyenne des participations pour une question 
    def moyenneParticipationsParQuestionAPointsEtParSondage(id_question, id_sondage)
        
        compteur = 0 # pour faire la somme des réponses
        Participer.where(id_question: id_question, id_sondage: id_sondage, etat: false).find_each do |participation|

            # convertir la reponse de type 'string' vers type 'float'
            reponseString = participation.reponse
            reponseFloat = reponseString.to_f

            compteur = compteur + reponseFloat
            
        end

        nbrParticipations = countParticipationsParQuestionAPointsEtParSondage(id_question,id_sondage)

        if nbrParticipations != 0
            moyenne = compteur / nbrParticipations
        else
            moyenne = nil
        end
        
    end

    # Afficher le nombre des participations pour une question à point
    def countParticipationsParQuestionAPointsEtParSondage(id_question,id_sondage)
        nombreParticipations = Participer.where(id_question: id_question, id_sondage: id_sondage, etat: false).count
    end

    # Afficher pour chaque question à choix unique le nombre de participations pour chaque choix
    def ParticipationsParQuestionChoixUniqueEtParSondage(id_question, id_sondage, nombre_choix)
        
        # récupérer les choix de la question passée en paramétre
        choixQuestion = ChoixService.instance.afficherLesChoixParQuestion(id_question)

        # map pour associer chaque choix le nombre de participation 
        hash = Hash.new
        hash ["id_question"] = id_question

        choixQuestion.find_each do |choix|
            choixString = choix.id_choix.to_s
            hash[choixString] = 0

        end

        # récupérer la liste des participations par sondage et par question
        # et traiter chaque participation
        Participer.where(id_question: id_question, id_sondage: id_sondage, etat: false).find_each do |participation|

            reponseString = participation.reponse
            hash[reponseString] = hash[reponseString]+1

        end

        resultat = hash

    end

    # Afficher pour chaque question à choix multiple le nombre de participations pour chaque choix
    def ParticipationsParQuestionChoixMultipleEtParSondage(id_question, id_sondage, nombre_choix)
        
        # récupérer les choix de la question passée en paramétre
        choixQuestion = ChoixService.instance.afficherLesChoixParQuestion(id_question)

        # map pour associer chaque choix le nombre de participation 
        hash = Hash.new
        hash ["id_question"] = id_question

        choixQuestion.find_each do |choix|
            choixString = choix.id_choix.to_s
            hash[choixString] = 0
        end

        # récupérer la liste des participations par sondage et par question
        # et traiter chaque participation
        Participer.where(id_question: id_question, id_sondage: id_sondage, etat: false).find_each do |participation|

            reponseString = participation.reponse

            # dans le cas des choix multiple la reponse est sous format : idChoix;idChoix;...
            reponseString.split(';').each do |id|
                hash[id] = hash[id]+1    
            end
        
        end

        resultat = hash

    end

end