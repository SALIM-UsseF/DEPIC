################################
#   Administrateur Controller
# #############################
#
# Expose des services REST sous format Json:
#   - Afficher la liste des admins
#   - Afficher un admin par ID
#   - Creer un nouveau admin
#   - Modifier un admin
#   - Verifier le login d'un admin
#   - Verifier le mot de passe d'un admin
#   - Supprimer un admin par ID

require 'AdministrateurService'

class AdministrateursController < ApplicationController

  # selectionner tout les admins
  def index
    administrateurs = AdministrateurService.instance.listeDesAdmins
    render json: administrateurs, status: :ok
  end

  # Afficher un admin par ID
  def show
    administrateur = AdministrateurService.instance.afficherAdminParId(params[:id])
    (administrateur != nil) ? (render json: administrateur, status: :ok) : (render json: nil, status: :not_found)
  end

  # Creer un nouveau admin
  def create
    params.permit(:pseudo_administrateur, :email_administrateur, :motDePasse_administrateur)
    ajout = AdministrateurService.instance.creerNouveauAdmin(params[:pseudo_administrateur], params[:email_administrateur], params[:motDePasse_administrateur])
    (ajout != nil) ? (render json: ajout, status: :ok) : (render json: nil, status: :not_found)
  end

  # Verifier le login d'un admin
  def loginAdmin
    params.permit(:email_administrateur, :motDePasse_administrateur)
    login = AdministrateurService.instance.loginAdmin(params[:email_administrateur], params[:motDePasse_administrateur])
    (login != nil) ? (render json: login, status: :ok) : (render json: nil, status: :not_found)
  end

  # Verifier le mot de passe d'un admin
  def checkAdminPassword
    params.permit(:id_administrateur, :motDePasse_administrateur)
    check = AdministrateurService.instance.verifierMotDePasseAdmin(params[:id], params[:motDePasse_administrateur])
    (check) ? (render json: true, status: :ok) : (render json: false, status: :not_found)
  end

  # Verifier l'email d'un admin
  def checkAdminEmail
    params.permit(:email_administrateur)
    check = AdministrateurService.instance.verifierEmailAdmin(params[:email_administrateur])
    (check) ? (render json: true, status: :ok) : (render json: false, status: :not_found)
  end

  # Modifier un admin par ID
  def update
    modifier = AdministrateurService.instance.modifierAdmin(params[:id], params[:pseudo_administrateur], params[:email_administrateur], params[:motDePasse_administrateur])
    (modifier != nil) ? (render json: modifier, status: :ok) : (render json: nil, status: :not_found)
  end

  # Supprimer un admin par ID
  def delete
    supprimer = AdministrateurService.instance.supprimerAdmin(params[:id])
    (supprimer) ? (render json: true, status: :ok) : (render json: false, status: :not_found)
  end


  # Liste des parametres à fournir
  private

  # parametres d'ajout
  def administrateur_params
    params.permit(:pseudo_administrateur, :email_administrateur, :motDePasse_administrateur)
  end

end