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

# Si l'attribut 'etat' a la valeur 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

#Pour le CRYPTAGE MD5
require 'digest'

class AdministrateursController < ApplicationController

  # selectionner que les admins non supprimés (etat=false)
  def index
    administrateurs = Administrateur.where(etat: false).order('created_at ASC');
    render json: administrateurs, status: :ok
  end

  # Afficher un admin par ID
  def show
  
      administrateur = Administrateur.find_by(id_administrateur: params[:id], etat: false);

      if administrateur != nil
        render json: administrateur, status: :ok
      else
        render json: nil, status: :not_found
      end

  end

  # Creer un nouveau admin
  def create

    psw = params[:motDePasse_administrateur]

    if psw != ''
      md5Psw = Digest::MD5.hexdigest(psw)
      params[:motDePasse_administrateur] = md5Psw

      administrateur = Administrateur.new(administrateur_params)

      if administrateur.save
        render json: administrateur, status: :ok
      else
        render json: nil, status: :unprocessable_entity
      end
    else
      render json: nil, status: :unprocessable_entity
    end 

  end

  # Verifier le login d'un admin
  def loginAdmin
    emailAdmin = params[:email_administrateur]
    psw = params[:motDePasse_administrateur]
    if psw != '' && emailAdmin != ''
      administrateur = Administrateur.find_by(email_administrateur: emailAdmin, motDePasse_administrateur: psw, etat: false);
      if administrateur != nil
        render json: administrateur, status: :ok
      else
        render json: nil, status: :unprocessable_entity
      end
    else
      render json: nil, status: :unprocessable_entity
    end 
  end

  # Verifier le mot de passe d'un admin
  def checkAdminPassword
    idAdmin = params[:id_administrateur]
    psw = params[:motDePasse_administrateur]
    if psw != '' && idAdmin != nil
      administrateur = Administrateur.find_by(id_administrateur: idAdmin, motDePasse_administrateur: psw, etat: false);
      render json: (administrateur != nil), status: :ok
    end
  end

  # Modifier un admin
  def update
        
    administrateur = Administrateur.find_by(id_administrateur: params[:id], etat: false);

    if administrateur != nil && administrateur.update_attributes(administrateur_params)
      render json: administrateur, status: :ok
    else
      render json: nil, status: :not_found
    end


  end

  # Supprimer un admin par ID
  def delete
  
    administrateur = Administrateur.find_by(id_administrateur: params[:id], etat: false);

    if administrateur != nil && administrateur.update_attributes(administrateur_param_delete)
      render json: administrateur, status: :ok
    else
      render json: nil, status: :not_found
    end

  end


  # Liste des parametres à fournir
  private

  # parametres d'ajout
  def administrateur_params
    params.permit(:pseudo_administrateur, :email_administrateur, :motDePasse_administrateur)
  end

  # parametres de suppression
  def administrateur_param_delete
    params.permit(:etat)
  end

end