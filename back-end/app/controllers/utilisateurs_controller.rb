################################
#   Utilisateur Controller
# #############################
#
# Expose des services REST :
#   - Afficher la liste des utilisateurs 
#   - Afficher un utilisateur par ID
#   - Creer une nouveau utilisateur
#   - Modifier un utilisateur
#   - Supprimer un utilisateur par ID

class UtilisateursController < ApplicationController
  
  # Afficher la liste des utilisateurs 
  def index
    utilisateurs = Utilisateur.where(etat: false).order('created_at ASC');
    render json: utilisateurs,status: :ok
  end

  # Afficher un Utilisateur par ID
  def show

    utilisateurs = Utilisateur.find_by(id_utilisateur: params[:id], etat: false);

    if utilisateurs != nil
      render json: utilisateurs, status: :ok
    else
      render json: nil, status: :not_found
    end

end

# Creer un nouveau Utilisateur
def create
  
  utilisateurs = Utilisateur.new(utilisateur_params)

  if utilisateurs.save
    render json: utilisateurs, status: :ok
  else
    render json: nil, status: :unprocessable_entity
  end

end

# Modifier un Utilisateur
def update
      
  utilisateurs = Utilisateur.find_by(id_utilisateur: params[:id], etat: false);

  if utilisateurs != nil && utilisateurs.update_attributes(utilisateur_params)
    render json: utilisateurs, status: :ok
  else
    render json: nil, status: :not_found
  end


end

# Supprimer un Utilisateur par ID
def delete

  utilisateurs = Utilisateur.find_by(id_utilisateur: params[:id], etat: false);

  if utilisateurs != nil && utilisateurs.update_attributes(utilisateur_param_delete)
    render json: utilisateurs, status: :ok
  else
    render json: nil, status: :not_found
  end

end

  # Liste des parametres à fournir

  private
  
  # parametres d'ajout
  def utilisateur_params
      params.permit(:email, :adresseIp)
  end

  # parametres de suppression
  def utilisateur_param_delete
    params.permit(:etat)
  end



  
end