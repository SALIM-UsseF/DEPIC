################################
#   Choix Controller
# #############################
#
# Expose des services REST sous format Json:
#   - Afficher la liste des choix 
#   - Afficher un choix par ID
#   - Afficher les Choix pour une Question donnée
#   - Creer un choix
#   - Modifier un choix
#   - Supprimer un choix par ID

require 'ChoixService'

class ChoixesController < ApplicationController
    # Afficher la liste des choix 
  def index
    choix = ChoixService.instance.listeDesChoix
    render json: choix, status: :ok
  end

  # Afficher un Choix par ID
  def show
    choix = ChoixService.instance.afficherChoixParId(params[:id])
    (choix != nil) ? (render json: choix, status: :ok) : (render json: nil, status: :not_found)
  end

  # Afficher les Choix pour une Question donnée
  def afficherLesChoixParQuestion
    choix = ChoixService.instance.afficherLesChoixParQuestion(params[:id])
    (choix != nil) ? (render json: choix, status: :ok) : (render json: nil, status: :not_found)
  end

  # Creer un nouveau Choix
  def create
    params.permit(:intituleChoix, :question_id)
    ajout = ChoixService.instance.creerNouveauChoix(params[:intituleChoix], params[:question_id])
    (ajout != nil) ? (render json: ajout, status: :ok) : (render json: nil, status: :not_found)
  end

  # Modifier un Choix
  def update
    modifier = ChoixService.instance.modifierChoix(params[:id], params[:intituleChoix])
    (modifier != nil) ? (render json: modifier, status: :ok) : (render json: nil, status: :not_found)
  end

  # Supprimer un Choix par ID
  def delete
    supprimer = ChoixService.instance.supprimerChoix(params[:id], params[:etat])
    (supprimer) ? (render json: true, status: :ok) : (render json: false, status: :not_found)
  end

  ########################################### "Actions pour la partie Mobile" ###########################
  # Afficher les Choix pour une Question donnée d'un sondage publié
  def afficherLesChoixParQuestionPublie
    choix = ChoixService.instance.afficherLesChoixParQuestionPublie(params[:idSondage], params[:idQuestion])
    (choix != nil) ? (render json: choix, status: :ok) : (render json: nil, status: :not_found)
  end
  #######################################################################################################

  # Liste des parametres à fournir

  private
  
  # parametres d'ajout
  def choix_params
      params.permit(:intituleChoix, :question_id)
  end

  # parametres de suppression
  def choix_param_delete
    params.permit(:etat)
  end

end
