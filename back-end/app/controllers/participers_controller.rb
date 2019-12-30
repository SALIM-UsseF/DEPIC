################################
#   Participer Controller
# #############################
#
# Expose des service REST :
#   - Afficher la liste des participations
#   - Afficher une participation par ID
#   - Creer une nouvelle participation
#   - Modifier une participation
#   - Supprimer une participation par ID

class ParticipersController < ApplicationController
  
  # Afficher la liste des participations par sondage
  def index
    participations = Participer.where(etat: false).order('created_at ASC, id_sondage DESC');
    render json: {status: 'SUCCESS', message:'Loaded Participations', data:participations},status: :ok
  end

  # Afficher les participations d'un sondage
  def showParticipationBySondage

    participations = Participer.find_by(id_sondage: params[:id], etat: false);

    if participations != nil
      render json: {status: 'SUCCESS', message: 'Loaded Participation', data:participations}, status: :ok
    else
      render json: {status: 'ERROR', message: 'Participation not found'}, status: :not_found
    end

  end

  # Afficher les participations d'un utilisateur pour un sondage
  def showParticipationByUserAndSondage

    participations = Participer.find_by(id_utilisateur: params[:idUser], id_sondage: params[:idSondage], etat: false);

    if participations != nil
      render json: {status: 'SUCCESS', message: 'Loaded Participation', data:participations}, status: :ok
    else
      render json: {status: 'ERROR', message: 'Participation not found'}, status: :not_found
    end

  end

  # Afficher les participations d'une question pour un sondage
  def showParticipationByQuestionAndSondage

    participations = Participer.find_by(id_question: params[:idQuestion], id_sondage: params[:idSondage], etat: false);

    if participations != nil
      render json: {status: 'SUCCESS', message: 'Loaded Participation', data:participations}, status: :ok
    else
      render json: {status: 'ERROR', message: 'Participation not found'}, status: :not_found
    end

  end

  # Creer un nouveau Participer
  def create

  participations = Participer.new(participer_params)

  if participations.save
    render json: {status: 'SUCCESS', message: 'Saved Participation', data:participations}, status: :ok
  else
    render json: {status: 'ERROR', message: 'Participation not saved'}, status: :unprocessable_entity
  end

  end

  # Modifier une Participation
  def update
      
  participations = Participer.find_by(id_utilisateur: params[:idUser], id_sondage: params[:idSondage], id_question: params[:idQuestion], etat: false);

  if participations != nil && participations.update_attributes(participer_params)
    render json: {status: 'SUCCESS', message: 'Updated Participation', data:participations}, status: :ok
  else
    render json: {status: 'ERROR', message: 'Participation not updated'}, status: :not_found
  end


  end

  # Supprimer une participation
  def delete

  participations = Participer.find_by(id_utilisateur: params[:idUser], id_sondage: params[:idSondage], id_question: params[:idQuestion], etat: false);

  if participations != nil && participations.update_attributes(participer_param_delete)
    render json: {status: 'SUCCESS', message: 'Deleted Participation', data:participations}, status: :ok
  else
    render json: {status: 'ERROR', message: 'Participation not Deleted'}, status: :not_found
  end

  end

  # Liste des parametres à fournir

  private
  
  # parametres d'ajout
  def participer_params
      params.permit(:id_utilisateur, :id_sondage, :id_question, :reponse, :etat)
  end

  # parametres de suppression
  def participer_param_delete
    params.permit(:etat)
  end



  
end
