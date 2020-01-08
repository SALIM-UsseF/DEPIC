################################
#   Sondage Controller
# #############################
#
# Expose des services REST sous format Json:
#   - Afficher la liste des sondages 
#   - Afficher un sondage par ID
#   - Creer une nouveau sondage
#   - Modifier un sondage 
#   - Supprimer un sondage par ID
#   - Activer les resultats d'un Sondage
#   - Publier un Sondage
#   - Actions pour la partie Mobile :
#     - Afficher les Sondages publiés
#     - Afficher un Sondage publié

require 'SondageService'

class SondagesController < ApplicationController
  
  # Afficher la liste des sondages 
  def index
    sondages = SondageService.instance.listeDesSondages
    render json: sondages, status: :ok
  end

  # Afficher un Sondage par ID
  def show
    sondage = SondageService.instance.afficherSondageParId(params[:id])
    (sondage != nil) ? (render json: sondage, status: :ok) : (render json: nil, status: :not_found)
  end

# Creer un nouveau Sondage
def create
  params.permit(:intituleSondage, :descriptionSondage, :administrateur_id)
  ajout = SondageService.instance.creerNouveauSondage(params[:intituleSondage], params[:descriptionSondage], params[:administrateur_id])
  (ajout != nil) ? (render json: ajout, status: :ok) : (render json: nil, status: :not_found)
end

# Modifier un Sondage
def update
  modifier = SondageService.instance.modifierSondage(params[:id], params[:intituleSondage], params[:descriptionSondage], params[:publier], params[:resultats])
  (modifier != nil) ? (render json: modifier, status: :ok) : (render json: nil, status: :not_found)      
end


# Publier un Sondage
def publierSondage
  modifier = SondageService.instance.publierSondage(params[:id], params[:publier])
  (modifier != nil) ? (render json: modifier, status: :ok) : (render json: nil, status: :not_found)
end

# Activer les resultats d'un Sondage
def activerResultats
  modifier = SondageService.instance.activerResultats(params[:id], params[:resultats])
  (modifier != nil) ? (render json: modifier, status: :ok) : (render json: nil, status: :not_found)
end

# Supprimer un Sondage par ID
def delete
  supprimer = SondageService.instance.supprimerSondage(params[:id], params[:etat])
  (supprimer) ? (render json: true, status: :ok) : (render json: false, status: :not_found)
end


################################## "Actions pour la partie Mobile" ########################################
# Afficher les Sondages publiés
def showSondagesPublies
  sondages = SondageService.instance.listeDesSondagesPublies
  (!sondages.empty?) ? (render json: sondages, status: :ok) : (render json: nil, status: :not_found)
end

# Afficher un Sondage publié
def showSondagePublie
  sondage = SondageService.instance.afficherSondagePublie(params[:idSondage])
  (sondage != nil) ? (render json: sondage, status: :ok) : (render json: nil, status: :not_found)
end
###########################################################################################################


# Liste des parametres à fournir
private

# parametres d'ajout
def sondage_params
    params.permit(:intituleSondage, :descriptionSondage, :administrateur_id)
end

# parametres de modification
def up_sondage_params
  params.permit(:intituleSondage, :descriptionSondage, :publier, :resultats)
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