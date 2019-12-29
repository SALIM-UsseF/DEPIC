
         ################################
        #   Sondage Controller
        # #############################
        #
        # Expose des service REST :
        #   - Afficher la liste des sondages 
        #   - Afficher un sondage par ID
        #   - Creer une nouveau sondage
        #   - Modifier un sondage 
        #   - Supprimer un sondage par ID

        class SondageController < ApplicationController
          
          # Afficher la liste des sondages 
          def index
            sondages = Sondage.order('created_at ASC');
            render json: {status: 'SUCCESS', message:'Loaded sondages', data:sondages},status: :ok
          end
  
  
          # Afficher un sondage par ID
          def show
            begin
              
        
              sondage=Sondage.find(params[:id])
              render json: {status: 'SUCCESS', message:'Loaded sondage', data:sondage},status: :ok
              rescue ActiveRecord::RecordNotFound => e
              render json: {
                status: 'ERROR',
                error: e.to_s
              }, status: :not_found  
  
            end
          end
  
          # Creer une nouveau sondage
          def create  
              sondage = Sondage.new(sondage_params)
      
              if sondage.save
              render json: {status: 'SUCCESS', message:'Saved sondage', data:sondage},status: :ok
              else
              render json: {status: 'ERROR', message:'Sondage not saved', data:Sondage.errors},status: :unprocessable_entity
              end
          end 
  
          # Supprimer un sondage par ID
          def delete
  
            begin
              
            
              sondage = Sondage.find(params[:id])
              if sondage.update_attributes(sondage_param_delete)
                render json: {status: 'SUCCESS', message: 'Deleted Sondage', data:sondage}, status: :ok
              else
                render json: {status: 'ERROR', message: 'Sondage not Deleted', data:Sondage.errors}, status: :unprocessable_entity
              end
              rescue ActiveRecord::RecordNotFound => e
              render json: {
                status: 'ERROR',
                error: e.to_s
              }, status: :not_found
  
            end
          end
  
          # Modifier un sondage 
          def update
  
            begin
              
              sondage = Sondage.find(params[:id])
              if sondage.update_attributes(sondage_params)
                render json: {status: 'SUCCESS', message:'Updated sondage', data:sondage},status: :ok
              else
                render json: {status: 'ERROR', message:'sondage not updated', data:Sondage.errors},status: :unprocessable_entity
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
          def sondage_params
  
              params.permit(:intituleSondage, :descriptionSondage, :etat, :id_administrateur)
          end
  
          # parametres de suppression
          def sondage_param_delete
            params.permit(:etat)
          end
        
  
  
          
        end
  