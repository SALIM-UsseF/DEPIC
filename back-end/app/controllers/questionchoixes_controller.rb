################################
#   Question choix Controller
# #############################
#
# Expose des services REST sous format Json:
#   - Afficher la liste des questions choix 
#   - Afficher une question choix par ID
#   - Creer une nouvelle question choix 
#   - Modifier une question choix
#   - Supprimer une question choix par ID

require 'QuestionChoixService'

class QuestionchoixesController < ApplicationController

  
  # Afficher la liste des questions choix
  def index
    questions = QuestionChoixService.instance.listeDesQuestions
    render json: questions, status: :ok
  end


  # Afficher une QuestionChoix par ID
  def show
    question = QuestionChoixService.instance.afficherQuestionParId(params[:id])
    (question != nil) ? (render json: question, status: :ok) : (render json: nil, status: :not_found)
  end

  # Creer une nouvelle QuestionChoix
  def create
    params.permit(:intitule, :estObligatoire, :estUnique, :nombreChoix, :ordre, :sondage_id)
    ajout = QuestionChoixService.instance.creerQuestionChoix(params[:intitule], params[:estObligatoire], params[:estUnique], params[:nombreChoix], params[:ordre], params[:sondage_id])
    (ajout != nil) ? (render json: ajout, status: :ok) : (render json: nil, status: :not_found)
  end

  # Modifier une QuestionChoix
  def update
    modifier = QuestionChoixService.instance.modifierQuestion(params[:id], params[:intitule], params[:estObligatoire], params[:estUnique], params[:nombreChoix], params[:ordre])
    (modifier != nil) ? (render json: modifier, status: :ok) : (render json: nil, status: :not_found) 
  end

  # Supprimer une QuestionChoix par ID
  def delete
    supprimer = QuestionChoixService.instance.supprimerQuestion(params[:id])
    (supprimer) ? (render json: true, status: :ok) : (render json: false, status: :not_found)
  end

  # Liste des parametres à fournir
  private

  # parametres d'ajout
  def question_params
      params.permit(:intitule, :estObligatoire, :estUnique, :nombreChoix, :ordre, :sondage_id)
  end

end