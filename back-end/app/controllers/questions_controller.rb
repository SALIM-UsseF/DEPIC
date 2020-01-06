################################
#   Questions Controller
# #############################
#
# Expose des services REST :
#   - Afficher la liste des questions
#   - Afficher une question par ID
#   - Supprimer une question par ID
#   - Actions pour la partie Mobile

class QuestionsController < ApplicationController
  
  # Afficher la liste des questions
  def index
    questions = Question.where(etat: false).order('sondage_id ASC, ordre ASC');
    render json: questions, status: :ok
  end

    # Afficher une question par id
    def show

      questions = Question.find_by(id_question: params[:id], etat: false);
  
      if questions != nil
        render json: questions, status: :ok
      else
        render json: nil, status: :not_found
      end
  
    end


  # Afficher les questions d'un sondage
  def showBySondage

    questions = Question.where(sondage_id: params[:id], etat: false);

    if questions != nil
      render json: questions, status: :ok
    else
      render json: nil, status: :not_found
    end

  end


  ########################################### "Actions pour la partie Mobile" ############################################
  # Afficher les questions d'un sondage publié
  def questionsDuSondage

    sondage = Sondage.where(id_sondage: params[:idSondage], etat: false, publier: true)

    if sondage.empty?
      render json: nil, status: :not_found
    else
      questions = Question.where(sondage_id: params[:idSondage], etat: false);

      if !questions.empty?
        render json: questions, status: :ok
      else
        render json: nil, status: :not_found
      end

    end

  end

  # Afficher une question d'un sondage publié
  def questionDuSondage

    sondage = Sondage.where(id_sondage: params[:idSondage], etat: false, publier: true)

    if sondage.empty?
      render json: nil, status: :not_found
    else
      questions = Question.find_by(id_question: params[:idQuestion], etat: false);
  
      if questions != nil
        render json: questions, status: :ok
      else
        render json: nil, status: :not_found
      end
    end

  end
  #######################################################################################################

  
end
