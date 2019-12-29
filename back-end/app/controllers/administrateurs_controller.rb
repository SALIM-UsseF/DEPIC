################################
#   Administrateur Controller
# #############################
#
# Expose des service REST :
#   - Afficher la liste des admins
#   - Afficher un admin par ID
#   - Creer un nouveau admin
#   - Modifier un admin
#   - Supprimer un admin par ID

class AdministrateursController < ApplicationController

    # Afficher la liste des admins
    def index
      administrateurs = Administrateur.order('created_at ASC');
      render json: {status: 'SUCCESS', message: 'Loaded Administrateurs', data:administrateurs}, status: :ok
    end
  
    # Afficher un admin par ID
    def show
  
      begin
  
        administrateur = Administrateur.find(params[:id]);
        render json: {status: 'SUCCESS', message: 'Loaded Administrateur', data:administrateur}, status: :ok
  
      rescue ActiveRecord::RecordNotFound => e
        render json: {
          status: 'ERROR',
          error: e.to_s
        }, status: :not_found    
  
      end
  
    end
  
    # Creer un nouveau admin
    def create
      
      administrateur = Administrateur.new(administrateur_params)
  
      if administrateur.save
        render json: {status: 'SUCCESS', message: 'Saved Administrateur', data:administrateur}, status: :ok
      else
        render json: {status: 'ERROR', message: 'Administrateur not saved', data:nil}, status: :unprocessable_entity
      end
  
    end
  
    # Modifier un admin
    def update
        
      begin 
  
      administrateur = Administrateur.find(params[:id])
  
      if administrateur.update_attributes(administrateur_params)
        render json: {status: 'SUCCESS', message: 'Updated Administrateur', data:administrateur}, status: :ok
      else
        render json: {status: 'ERROR', message: 'Administrateur not updated', data:nil}, status: :unprocessable_entity
      end
  
      rescue ActiveRecord::RecordNotFound => e
        render json: {
          status: 'ERROR',
          error: e.to_s
        }, status: :not_found    
      end
  
    end
  
    # Supprimer un admin par ID
    def delete
  
      begin
  
      administrateur = Administrateur.find(params[:id])
  
      if administrateur.update_attributes(administrateur_param_delete)
        render json: {status: 'SUCCESS', message: 'Deleted Administrateur', data:administrateur}, status: :ok
      else
        render json: {status: 'ERROR', message: 'Administrateur not Deleted', data:Administrateur.errors}, status: :unprocessable_entity
      end
  
      rescue ActiveRecord::RecordNotFound => e
        render json: {
          status: 'ERROR',
          error: e.to_s
        }, status: :not_found
  
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
  