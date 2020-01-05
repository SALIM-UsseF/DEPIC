################################
#   Participer Controller
# #############################
#
# Expose des services REST :
#   - Afficher la liste des participations
#   - Afficher une participation par ID
#   - Creer une nouvelle participation
#   - Modifier une participation
#   - Supprimer une participation par ID

class ParticipersController < ApplicationController
  
  # Afficher la liste des participations par sondage
  def index
    participations = Participer.where(etat: false).order('created_at ASC, id_sondage DESC');
    render json: participations, status: :ok
  end

  # Afficher les participations d'un sondage
  def showParticipationBySondage

    participations = Participer.find_by(id_sondage: params[:id], etat: false);

    if participations != nil
      render json: participations, status: :ok
    else
      render json: nil, status: :not_found
    end

  end

  # Afficher les participations d'un utilisateur pour un sondage
  def showParticipationByUserAndSondage

    participations = Participer.find_by(id_utilisateur: params[:idUser], id_sondage: params[:idSondage], etat: false);

    if participations != nil
      render json: participations, status: :ok
    else
      render json: nil, status: :not_found
    end

  end

  # Afficher les participations d'une question pour un sondage
  def showParticipationByQuestionAndSondage

    participations = Participer.find_by(id_question: params[:idQuestion], id_sondage: params[:idSondage], etat: false);

    if participations != nil
      render json: participations, status: :ok
    else
      render json: nil, status: :not_found
    end

  end

  # Creer une participation
  def create

  participations = Participer.new(participer_params)

  if participations.save
    render json: participations, status: :ok
  else
    render json: nil, status: :unprocessable_entity
  end

  end

  # Modifier une Participation
  def update
      
  participations = Participer.find_by(id_utilisateur: params[:idUser], id_sondage: params[:idSondage], id_question: params[:idQuestion], etat: false);

  if participations != nil && participations.update_attributes(participer_params)
    render json: participations, status: :ok
  else
    render json: nil, status: :not_found
  end


  end

  # Supprimer une participation
  def delete

  participations = Participer.find_by(id_utilisateur: params[:idUser], id_sondage: params[:idSondage], id_question: params[:idQuestion], etat: false);

  if participations != nil && participations.update_attributes(participer_param_delete)
    render json: participations, status: :ok
  else
    render json: nil, status: :not_found
  end

  end

  ########################################### "Actoins pour la partie Mobile" ############################
    # Creer une participation pour un sondage publié
    def repondreSondagePublie

      #verifier si le sondage est encore en publication
      sondage = Sondage.find_by(id_sondage: params[:id_sondage], etat: false, publier: true);
      if sondage != nil

        participations = Participer.new(participer_params)
    
        if participations.save
          render json: participations, status: :ok
        else
          render json: nil, status: :unprocessable_entity
        end

      else
        render json: nil, status: :not_found
      end
    
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