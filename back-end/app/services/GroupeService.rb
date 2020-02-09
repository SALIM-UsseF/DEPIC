require 'singleton'
require 'QuestionService'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class GroupeService

    include Singleton

    # Afficher les questions d'un groupe
    def questionsDuGroupe(id_groupe)

                # array contient la liste des questions
                questions = Array.new

                # récupérer la liste des id_question
                # et traiter chaque id_question
                Groupe.where(etat: false, id_groupe: id_groupe).order('id_question ASC').find_each do |id_question|
        
                    questions << QuestionService.instance.afficherQuestionParId(id_question.id_question)
        
                end
                
                questionsDuGroupe = questions


    end

    # Ajouter une question dans un groupe
    def ajouterQuestion(id_groupe, id_question)

        ajout = Groupe.new(:id_groupe => id_groupe, :id_question => id_question)

            if ajout.save
                newAjout = ajout
            else
                newAjout = nil
            end

    end

    # Supprimer un groupe par ID
    def supprimerGroupe(id_groupe, etat)
        Groupe.where(id_groupe: id_groupe).update_all(etat: etat)
    end

end