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
#   - Actions pour la partie Mobile

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
      render json: nil, status: :not_found
    end

  end

# Creer un nouveau Sondage
def create
  
  sondages = Sondage.new(sondage_params)

  if sondages.save
    render json: sondages, status: :ok
  else
    render json: nil, status: :unprocessable_entity
  end

end

# Modifier un Sondage
def update
      
  sondages = Sondage.find_by(id_sondage: params[:id], etat: false);

  if sondages != nil && sondages.update_attributes(sondage_params)
    render json: sondages, status: :ok
  else
    render json: nil, status: :not_found
  end


end


# Publier un Sondage
def publierSondage
      
  sondages = Sondage.find_by(id_sondage: params[:id], etat: false);

  if sondages != nil && sondages.update_attributes(publier_params)
    render json: sondages, status: :ok
  else
    render json: nil, status: :not_found
  end

end

# Activer les resultats d'un Sondage
def activerResultats
      
  sondages = Sondage.find_by(id_sondage: params[:id], etat: false, publier:true);

  if sondages != nil && sondages.update_attributes(resultats_params)
    render json: sondages, status: :ok
  else
    render json: nil, status: :not_found
  end

end

# Supprimer un Sondage par ID
def delete

  sondages = Sondage.find_by(id_sondage: params[:id], etat: false);

  if sondages != nil && sondages.update_attributes(sondage_param_delete)
    render json: sondages, status: :ok
  else
    render json: nil, status: :not_found
  end

end


################################## "Actions pour la partie Mobile" ########################################
# Afficher les Sondages publiés
def showSondagesPublies

  sondages = Sondage.where(etat: false, publier: true).order('created_at DESC');

  if !sondages.empty?
    render json: sondages, status: :ok
  else
    render json: nil, status: :not_found
  end

end

# Afficher un Sondage publié
def showSondagePublie

  sondages = Sondage.find_by(id_sondage: params[:idSondage], etat: false, publier: true);

  if sondages != nil
    render json: sondages, status: :ok
  else
    render json: nil, status: :not_found
  end

end
###########################################################################################################


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

# parametres de publication
def publier_params
  params.permit(:publier)
end

# parametres de resultats
def resultats_params
  params.permit(:resultats)
end

  
end