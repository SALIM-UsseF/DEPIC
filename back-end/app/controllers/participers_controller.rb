################################
#   Participer Controller
# #############################
#
# Expose des services REST sout format Json:
#   - Afficher la liste des participations par sondage
#   - Afficher les participations d'un sondage
#   - Afficher les participations d'un utilisateur pour un sondage
#   - Afficher les participations d'une question pour un sondage
#   - Creer une nouvelle participation
#   - Supprimer une participation par ID
#   - Actions pour la partie Mobile :
#     - Creer une participation pour un sondage publié

require 'ParticiperService'

class ParticipersController < ApplicationController
  
  # Afficher la liste des participations par sondage
  def index
    participations = ParticiperService.instance.listeDesParticipations
    render json: participations, status: :ok
  end

  # Afficher les participations d'un sondage
  def showParticipationsBySondage
    participations = ParticiperService.instance.afficherParticipationsParSondage(params[:id])
    (participations != nil) ? (render json: participations, status: :ok) : (render json: nil, status: :not_found)
  end

  # Afficher les participations d'un utilisateur pour un sondage
  def showParticipationsByUserAndSondage
    participations = ParticiperService.instance.afficherParticipationsParUtilisateurEtParSondage(params[:idUser], params[:idSondage])
    (participations != nil) ? (render json: participations, status: :ok) : (render json: nil, status: :not_found)
  end

  # Afficher les participations d'une question pour un sondage
  def showParticipationsByQuestionAndSondage
    participations = ParticiperService.instance.afficherParticipationsParQuestionEtParSondage(params[:idQuestion], params[:idSondage])
    (participations != nil) ? (render json: participations, status: :ok) : (render json: nil, status: :not_found)
  end

  # Creer une participation
  def create
    params.permit(:id_utilisateur, :id_sondage, :id_question, :reponse)
    ajout = ParticiperService.instance.creerParticipation(params[:id_utilisateur], params[:id_sondage], params[:id_question], params[:reponse])
    (ajout != nil) ? (render json: ajout, status: :ok) : (render json: nil, status: :not_found)
  end

  # Supprimer une participation
  def delete
    supprimer = ParticiperService.instance.supprimerParticipation(params[:idUser], params[:idSondage], params[:idQuestion], etat: false)
    (supprimer) ? (render json: true, status: :ok) : (render json: false, status: :not_found)
  end

  ########################################### "Actions pour la partie Mobile" ############################
  # Creer une participation pour un sondage publié
  def repondreSondagePublie
    params.permit(:id_utilisateur, :id_sondage, :id_question, :reponse)
    ajout = ParticiperService.instance.reponseUtilisateurPourSondagePublie(params[:id_utilisateur], params[:id_sondage], params[:id_question], params[:reponse])
    (ajout != nil) ? (render json: ajout, status: :ok) : (render json: nil, status: :not_found)
  end
  #######################################################################################################

  # Liste des parametres à fournir
  private
  
  # parametres d'ajout
  def participer_params
    params.permit(:id_utilisateur, :id_sondage, :id_question, :reponse)
  end

  # parametres de suppression
  def participer_param_delete
    params.permit(:etat)
  end



  
end