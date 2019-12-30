
         ################################
        #   Participer Controller
        # #############################
        #
        # Expose des service REST :
        #   - Afficher la liste des participations
        #   - Afficher une participation par ID
        #   - Creer une nouvelle participation
        #   - Modifier une participation
        #   - Supprimer une participation par ID

        class ParticipersController < ApplicationController
          
          # Afficher la liste des participations 
          def index
            participations = Participer.order('created_at ASC');
            render json: {status: 'SUCCESS', message:'Loaded participations', data:participations},status: :ok
          end
  
  
          # Afficher une participation par ID
          def show
            begin
              
        
              participation=Participer.find(params[:id])
              render json: {status: 'SUCCESS', message:'Loaded participation', data:participation},status: :ok
              rescue ActiveRecord::RecordNotFound => e
              render json: {
                status: 'ERROR',
                error: e.to_s
              }, status: :not_found  
  
            end
          end
  
          # Creer une nouvelle participation
          def create  
            participation = Participer.new(participer_params)
      
              if participation.save
              render json: {status: 'SUCCESS', message:'Saved participation', data:participation},status: :ok
              else
              render json: {status: 'ERROR', message:'participation not saved', data:Participer.errors},status: :unprocessable_entity
              end
          end 
  
          # Supprimer un participation par ID
          def delete
  
            begin
              
            
              participation = Participer.find(params[:id])
              if participation.update_attributes(participer_param_delete)
                render json: {status: 'SUCCESS', message: 'Deleted participation', data:participation}, status: :ok
              else
                render json: {status: 'ERROR', message: 'participation not Deleted', data:Participer.errors}, status: :unprocessable_entity
              end
              rescue ActiveRecord::RecordNotFound => e
              render json: {
                status: 'ERROR',
                error: e.to_s
              }, status: :not_found
  
            end
          end
  
          # Modifier un participation 
          def update
  
            begin
              
              participation = Participer.find(params[:id])
              if participation.update_attributes(participer_params)
                render json: {status: 'SUCCESS', message:'Updated participation', data:participation},status: :ok
              else
                render json: {status: 'ERROR', message:'participation not updated', data:Participer.errors},status: :unprocessable_entity
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
          def participer_params
  
              params.permit(:id_utilisateur, :id_sondage, :id_question, :reponse, :etat)
          end
  
          # parametres de suppression
          def participer_param_delete
            params.permit(:etat)
          end
        
  
  
          
        end
  