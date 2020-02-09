################################
#   Question points Controller
# #############################
#
# Expose des services REST sous format Json:
#   - Afficher la liste des questions points
#   - Afficher une question points par ID
#   - Creer une nouvelle question points 
#   - Modifier une question points
#   - Supprimer une question points par ID

require 'QuestionPointService'

class QuestionpointsController < ApplicationController

    # Afficher la liste des QuestionPoint
    def index
      questions = QuestionPointService.instance.listeDesQuestions
      render json: questions, status: :ok
    end
  
  
    # Afficher une QuestionPoint par ID
    def show
      question = QuestionPointService.instance.afficherQuestionParId(params[:id])
      (question != nil) ? (render json: question, status: :ok) : (render json: nil, status: :not_found)
    end
  
    # Creer une nouvelle QuestionPoint
    def create
      params.permit(:intitule, :estObligatoire, :minPoints, :maxPoints, :ordre, :sondage_id)
      ajout = QuestionPointService.instance.creerQuestionPoint(params[:intitule], params[:estObligatoire], params[:minPoints], params[:maxPoints], params[:ordre], params[:sondage_id])
      (ajout != nil) ? (render json: ajout, status: :ok) : (render json: nil, status: :not_found)
    end
  
    # Modifier une QuestionPoint
    def update
      modifier = QuestionPointService.instance.modifierQuestion(params[:id], params[:intitule], params[:estObligatoire], params[:minPoints], params[:maxPoints], params[:ordre])
      (modifier != nil) ? (render json: modifier, status: :ok) : (render json: nil, status: :not_found) 
    end
  
    # Supprimer une QuestionPoint par ID
    def delete
      supprimer = QuestionPointService.instance.supprimerQuestion(params[:id])
      (supprimer) ? (render json: true, status: :ok) : (render json: false, status: :not_found)
    end

  # Liste des parametres à fournir
  private
  
  # parametres d'ajout
  def question_params
      params.permit(:intitule, :estObligatoire, :minPoints, :maxPoints, :ordre, :sondage_id)
  end
  
end