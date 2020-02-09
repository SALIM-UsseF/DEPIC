################################
#   Groupe Controller
# #############################
#
# Expose des services REST sous format Json:
#   - Afficher les questions d'un groupe (par id_groupe)
#   - Ajouter une question dans un groupe
#   - Supprimer un groupe par ID

require 'GroupeService'

class GroupesController < ApplicationController

  # Afficher les questions d'un groupe (par id_groupe)
  def show
    questions = GroupeService.instance.questionsDuGroupe(params[:id])
    (questions != nil) ? (render json: questions, status: :ok) : (render json: nil, status: :not_found)
  end

  # Ajouter une question dans un groupe
  def create
    params.permit(:id_groupe, :id_question)
    ajout = GroupeService.instance.ajouterQuestion(params[:id_groupe], params[:id_question])
    (ajout != nil) ? (render json: ajout, status: :ok) : (render json: nil, status: :not_found)
  end

  # Supprimer un groupe par ID
  def delete
    GroupeService.instance.supprimerGroupe(params[:id_groupe], params[:etat])
  end

  # Liste des parametres à fournir
  private
  
  # parametres d'ajout
  def groupe_params
      params.permit(:id_groupe, :id_question)
  end

  # parametres de suppression
  def groupe_param_delete
    params.permit(:etat)
  end

end
