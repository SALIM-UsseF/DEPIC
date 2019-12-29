
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

        class UtilisateurController < ApplicationController
          
          # Afficher la liste des utilisateurs 
          def index
            utilisateurs = Utilisateur.order('created_at ASC');
            render json: {status: 'SUCCESS', message:'Loaded utilisateurs', data:utilisateurs},status: :ok
          end
  
  
          # Afficher un utilisateur par ID
          def show
            begin
              
        
              utilisateur=Utilisateur.find(params[:id])
              render json: {status: 'SUCCESS', message:'Loaded utilisateur', data:utilisateur},status: :ok
              rescue ActiveRecord::RecordNotFound => e
              render json: {
                status: 'ERROR',
                error: e.to_s
              }, status: :not_found  
  
            end
          end
  
          # Creer une nouveau utilisateur
          def create  
              utilisateur = Utilisateur.new(utilisateur_params)
      
              if utilisateur.save
              render json: {status: 'SUCCESS', message:'Saved utilisateur', data:utilisateur},status: :ok
              else
              render json: {status: 'ERROR', message:'Utilisateur not saved', data:Utilisateur.errors},status: :unprocessable_entity
              end
          end 
  
          # Supprimer un utilisateur par ID
          def delete
  
            begin
              
            
              utilisateur = Utilisateur.find(params[:id])
              if utilisateur.update_attributes(utilisateur_param_delete)
                render json: {status: 'SUCCESS', message: 'Deleted Utilisateur', data:utilisateur}, status: :ok
              else
                render json: {status: 'ERROR', message: 'Utilisateur not Deleted', data:Utilisateur.errors}, status: :unprocessable_entity
              end
              rescue ActiveRecord::RecordNotFound => e
              render json: {
                status: 'ERROR',
                error: e.to_s
              }, status: :not_found
  
            end
          end
  
          # Modifier un utilisateur 
          def update
  
            begin
              
              utilisateur = Utilisateur.find(params[:id])
              if utilisateur.update_attributes(utilisateur_params)
                render json: {status: 'SUCCESS', message:'Updated utilisateur', data:utilisateur},status: :ok
              else
                render json: {status: 'ERROR', message:'utilisateur not updated', data:Utilisateur.errors},status: :unprocessable_entity
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
          def utilisateur_params
  
              params.permit(:email, :adresseIp, :etat)
          end
  
          # parametres de suppression
          def utilisateur_param_delete
            params.permit(:etat)
          end
        
  
  
          
        end
  