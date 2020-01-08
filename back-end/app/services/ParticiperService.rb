require 'singleton'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class ParticiperService

    include Singleton

    # Afficher la liste des participations
    def listeDesParticipations
        participations = Participer.where(etat: false).order('created_at ASC, id_sondage DESC');

        if participations.empty?
            liste = nil
        else
            liste = participations
        end

    end

    # Afficher les participations d'un sondage
    def afficherParticipationsParSondage(id_sondage)
        participations = Participer.where(id_sondage: id_sondage, etat: false);

        if participations.empty?
            liste = nil
        else
            liste = participations
        end

    end

    # Afficher les participations d'un utilisateur pour un sondage
    def afficherParticipationsParUtilisateurEtParSondage(id_utilisateur, id_sondage)
        participations = Participer.where(id_utilisateur: id_utilisateur, id_sondage: id_sondage, etat: false);

        if participations.empty?
            liste = nil
        else
            liste = participations
        end

    end

    # Afficher les participations d'une question pour un sondage
    def afficherParticipationsParQuestionEtParSondage(id_question, id_sondage)
        participations = Participer.where(id_question: id_question, id_sondage: id_sondage, etat: false);

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
      sondage = Sondage.find_by(id_sondage: id_sondage, etat: false, publier: true);
      
      if sondage != nil
        creerParticipation(id_utilisateur, id_sondage, id_question, reponse)
      end

    end

    # Supprimer une Participation
    def supprimerParticipation(id_utilisateur, id_sondage, id_question, etat)

        participation = Participer.find_by(id_utilisateur: id_utilisateur, id_sondage: id_sondage, id_question: id_question, etat: false);

        if participation != nil && Participer.update_attributes(:etat => etat)
            supprimer = true
        else
            supprimer = false
        end

    end

end