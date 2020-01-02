################################
#   Sondage Controller
# #############################
#
# Expose des services REST :
#   - Afficher la liste des sondages 
#   - Afficher un sondage par ID
#   - Creer une nouveau sondage
#   - Modifier un sondage 
#   - Supprimer un sondage par ID

class SondagesController < ApplicationController
  
  # Afficher la liste des sondages 
  def index
    sondages = Sondage.where(etat: false).order('created_at ASC');
    render json: sondages, status: :ok
  end

  # Afficher un Sondage par ID
  def show

    sondages = Sondage.find_by(id_sondage: params[:id], etat: false);

    if sondages != nil
      render json: sondages, status: :ok
    else
      render json: {status: 'ERROR', message: 'Sondage not found'}, status: :not_found
    end

end

#afficher les questions d'un sondage
def questionsBySondage
end

# Creer un nouveau Sondage
def create
  
  sondages = Sondage.new(sondage_params)

  if sondages.save
    render json: sondages, status: :ok
  else
    render json: {status: 'ERROR', message: 'Sondage not saved'}, status: :unprocessable_entity
  end

end

# Modifier un Sondage
def update
      
  sondages = Sondage.find_by(id_sondage: params[:id], etat: false);

  if sondages != nil && sondages.update_attributes(sondage_params)
    render json: sondages, status: :ok
  else
    render json: {status: 'ERROR', message: 'Sondage not updated'}, status: :not_found
  end


end

# Supprimer un Sondage par ID
def delete

  sondages = Sondage.find_by(id_sondage: params[:id], etat: false);

  if sondages != nil && sondages.update_attributes(sondage_param_delete)
    render json: sondages, status: :ok
  else
    render json: {status: 'ERROR', message: 'Sondage not Deleted'}, status: :not_found
  end

end

  # Liste des parametres à fournir

  private
  
  # parametres d'ajout
  def sondage_params
      params.permit(:intituleSondage, :descriptionSondage, :administrateur_id)
  end

  # parametres de suppression
  def sondage_param_delete
    params.permit(:etat)
  end



  
end
