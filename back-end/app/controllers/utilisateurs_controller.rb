################################
#   Utilisateur Controller
# #############################
#
# Expose des services REST sous format Json:
#   - Afficher la liste des utilisateurs 
#   - Afficher un utilisateur par ID
#   - Creer une nouveau utilisateur
#   - Modifier un utilisateur
#   - Supprimer un utilisateur par ID

require 'UtilisateurService'

class UtilisateursController < ApplicationController
  
  # Afficher la liste des utilisateurs 
  def index
    utilisateurs = UtilisateurService.instance.listeDesUtilisateurs
    render json: utilisateurs, status: :ok
  end

  # Afficher un Utilisateur par ID
  def show
    utilisateur = UtilisateurService.instance.afficherUtilisateurParId(params[:id])
    (utilisateur != nil) ? (render json: utilisateur, status: :ok) : (render json: nil, status: :not_found)
  end

  # Creer un nouveau Utilisateur
  def create
    params.permit(:email, :adresseIp)
    ajout = UtilisateurService.instance.creerNouveauUtilisateur(params[:email], params[:adresseIp])
    (ajout != nil) ? (render json: ajout, status: :ok) : (render json: nil, status: :not_found)
  end

  # Modifier un Utilisateur
  def update
    modifier = UtilisateurService.instance.modifierUtilisateur(params[:id], params[:email], params[:adresseIp])
    (modifier != nil) ? (render json: modifier, status: :ok) : (render json: nil, status: :not_found)
  end

  # Supprimer un Utilisateur par ID
  def delete
    supprimer = UtilisateurService.instance.supprimerUtilisateur(params[:id], params[:etat])
    (supprimer) ? (render json: true, status: :ok) : (render json: false, status: :not_found)
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