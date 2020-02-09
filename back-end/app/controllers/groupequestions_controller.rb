  ################################
  #   Groupequestions Controller
  # #############################
  #
  # Expose des services REST sous format Json:
  #   - Afficher la liste des groupes de questions
  #   - Afficher un groupe de question par ID
  #   - Creer un nouveau groupe de question
  #   - Modifier un groupe de question
  #   - Supprimer un groupe de question par ID

  require 'GroupeQuestionService'

  class GroupequestionsController < ApplicationController
    
  # Afficher la liste des GroupeQuestion
  def index
    questions = GroupeQuestionService.instance.listeDesQuestions
    render json: questions, status: :ok
  end


  # Afficher un GroupeQuestion par ID
  def show
    question = GroupeQuestionService.instance.afficherQuestionParId(params[:id])
    (question != nil) ? (render json: question, status: :ok) : (render json: nil, status: :not_found)
  end
  
  # Creer un nouveau GroupeQuestion
  def create
    params.permit(:intitule, :estObligatoire, :ordre, :sondage_id)
    ajout = GroupeQuestionService.instance.creerGroupeQuestion(params[:intitule], params[:estObligatoire], params[:ordre], params[:sondage_id])
    (ajout != nil) ? (render json: ajout, status: :ok) : (render json: nil, status: :not_found)
  end
  
  # Modifier un GroupeQuestion
  def update
    modifier = GroupeQuestionService.instance.modifierQuestion(params[:id], params[:intitule], params[:estObligatoire], params[:ordre])
    (modifier != nil) ? (render json: modifier, status: :ok) : (render json: nil, status: :not_found)  
  end
  
  # Supprimer un GroupeQuestion par ID
  def delete
    supprimer = GroupeQuestionService.instance.supprimerQuestion(params[:id], params[:etat])
    (supprimer) ? (render json: true, status: :ok) : (render json: false, status: :not_found) 
  end
  
  # Liste des parametres à fournir
  private
  
  # parametres d'ajout
  def question_params
      params.permit(:intitule, :estObligatoire, :ordre, :sondage_id)
  end

  # parametres de suppression
  def question_param_delete
    params.permit(:etat)
  end
    
  end