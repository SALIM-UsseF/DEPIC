################################
#   Question ouverte Controller
# #############################
#
# Expose des services REST sous format Json:
#   - Afficher la liste des questions ouvertes
#   - Afficher une question ouverte par ID
#   - Creer une nouvelle question ouverte 
#   - Modifier une question ouverte
#   - Supprimer une question ouverte par ID

require 'QuestionOuverteService'

class QuestionouvertesController < ApplicationController
  
  # Afficher la liste des QuestionOuverte
  def index
    questions = QuestionOuverteService.instance.listeDesQuestions
    render json: questions, status: :ok
  end


  # Afficher une QuestionOuverte par ID
  def show
    question = QuestionOuverteService.instance.afficherQuestionParId(params[:id])
    (question != nil) ? (render json: question, status: :ok) : (render json: nil, status: :not_found)
  end

  # Creer une nouvelle QuestionOuverte
  def create
    params.permit(:intitule, :estObligatoire, :nombreDeCaractere, :ordre, :sondage_id)
    ajout = QuestionOuverteService.instance.creerQuestionOuverte(params[:intitule], params[:estObligatoire], params[:nombreDeCaractere], params[:ordre], params[:sondage_id])
    (ajout != nil) ? (render json: ajout, status: :ok) : (render json: nil, status: :not_found)
  end

  # Modifier une QuestionOuverte
  def update
    modifier = QuestionOuverteService.instance.modifierQuestion(params[:id], params[:intitule], params[:estObligatoire], params[:nombreDeCaractere], params[:ordre])
    (modifier != nil) ? (render json: modifier, status: :ok) : (render json: nil, status: :not_found) 
  end

  # Supprimer une QuestionOuverte par ID
  def delete
    supprimer = QuestionOuverteService.instance.supprimerQuestion(params[:id])
    (supprimer) ? (render json: true, status: :ok) : (render json: false, status: :not_found)
  end

  # Liste des parametres à fournir
  private

  # parametres d'ajout
  def question_params
      params.permit(:intitule, :estObligatoire, :nombreDeCaractere, :ordre, :sondage_id)
  end
  
end