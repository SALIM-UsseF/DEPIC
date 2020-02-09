################################
#   Categorie Controller
# #############################
#
# Expose des services REST sous format Json:
#   - Afficher la liste des categories 
#   - Afficher une categorie par ID
#   - Creer une categorie
#   - Modifier une categorie
#   - Supprimer une categorie par ID


require 'CategorieService'

class CategoriesController < ApplicationController

  # Afficher la liste des categories 
  def index
    categories = CategorieService.instance.listeDescategories
    render json: categories, status: :ok
  end

  # Afficher une categorie par ID
  def show
    categorie = CategorieService.instance.afficherCategorieParId(params[:id])
    (categorie != nil) ? (render json: categorie, status: :ok) : (render json: nil, status: :not_found)
  end

  # Creer une categorie
  def create
    params.permit(:intitule)
    ajout = CategorieService.instance.creerCategorie(params[:intitule])
    (ajout != nil) ? (render json: ajout, status: :ok) : (render json: nil, status: :not_found)
  end

  # Modifier une categorie
  def update
    modifier = CategorieService.instance.modifierCategorie(params[:id], params[:intitule])
    (modifier != nil) ? (render json: modifier, status: :ok) : (render json: nil, status: :not_found)
  end

  # Supprimer une categorie par ID
  def delete
    supprimer = CategorieService.instance.supprimerCategorie(params[:id])
    (supprimer) ? (render json: true, status: :ok) : (render json: false, status: :not_found)
  end

  # Liste des parametres à fournir
  private
  
  # parametres d'ajout
  def categorie_params
      params.permit(:intitule)
  end

end
