################################
#   Utilisateur Controller
# #############################
#
# Expose des service REST :
#   - Afficher la liste des utilisateurs 
#   - Afficher un utilisateur par ID
#   - Creer une nouveau utilisateur
#   - Modifier un utilisateur
#   - Supprimer un utilisateur par ID

class UtilisateursController < ApplicationController
  
  # Afficher la liste des utilisateurs 
  def index
    utilisateurs = Utilisateur.where(etat: false).order('created_at ASC');
    render json: {status: 'SUCCESS', message:'Loaded utilisateurs', data:utilisateurs},status: :ok
  end

  # Afficher un Utilisateur par ID
  def show

    utilisateurs = Utilisateur.find_by(id_utilisateur: params[:id], etat: false);

    if utilisateurs != nil
      render json: {status: 'SUCCESS', message: 'Loaded Utilisateur', data:utilisateurs}, status: :ok
    else
      render json: {status: 'ERROR', message: 'Utilisateur not found'}, status: :not_found
    end

end

# Creer un nouveau Utilisateur
def create
  
  utilisateurs = Utilisateur.new(question_params)

  if utilisateurs.save
    render json: {status: 'SUCCESS', message: 'Saved Utilisateur', data:utilisateurs}, status: :ok
  else
    render json: {status: 'ERROR', message: 'Utilisateur not saved'}, status: :unprocessable_entity
  end

end

# Modifier un Utilisateur
def update
      
  utilisateurs = Utilisateur.find_by(id_utilisateur: params[:id], etat: false);

  if utilisateurs != nil && utilisateurs.update_attributes(utilisateur_params)
    render json: {status: 'SUCCESS', message: 'Updated Utilisateur', data:utilisateurs}, status: :ok
  else
    render json: {status: 'ERROR', message: 'Utilisateur not updated'}, status: :not_found
  end


end

# Supprimer un Utilisateur par ID
def delete

  utilisateurs = Utilisateur.find_by(id_utilisateur: params[:id], etat: false);

  if utilisateurs != nil && utilisateurs.update_attributes(utilisateur_param_delete)
    render json: {status: 'SUCCESS', message: 'Deleted Utilisateur', data:utilisateurs}, status: :ok
  else
    render json: {status: 'ERROR', message: 'Utilisateur not Deleted'}, status: :not_found
  end

end

  # Liste des parametres à fournir

  private
  
  # parametres d'ajout
  def utilisateur_params

      params.permit(:email, :adresseIp, :etat)
  end

  # parametres de suppression
  def utilisateur_param_delete
    params.permit(:etat)
  end



  
end
