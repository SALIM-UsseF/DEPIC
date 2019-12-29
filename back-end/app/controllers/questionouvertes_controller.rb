
         ################################
        #   question ouverte Controller
        # #############################
        #
        # Expose des service REST :
        #   - Afficher la liste des questions ouvertes
        #   - Afficher une question ouverte par ID
        #   - Creer une nouvelle question ouverte 
        #   - Modifier une question ouverte
        #   - Supprimer une question ouverte par ID

        class QuestionouvertesController < ApplicationController
          
          # Afficher la liste des questions ouvertes
          def index
            questions = QuestionOuverte.order('created_at ASC');
            render json: {status: 'SUCCESS', message:'Loaded questions', data:questions},status: :ok
          end
  
  
          # Afficher une question ouverte par ID
          def show
            begin
              
        
              question=QuestionOuverte.find(params[:id])
              render json: {status: 'SUCCESS', message:'Loaded question', data:question},status: :ok
              rescue ActiveRecord::RecordNotFound => e
              render json: {
                status: 'ERROR',
                error: e.to_s
              }, status: :not_found  
  
            end
          end
  
          # Creer une nouvelle question ouverte
          def create
              question = QuestionOuverte.new(question_params)
      
              if question.save
              render json: {status: 'SUCCESS', message:'Saved question', data:question},status: :ok
              else
              render json: {status: 'ERROR', message:'Question not saved', data:QuestionOuverte.errors},status: :unprocessable_entity
              end
          end 
  
          # Supprimer une question ouverte par ID
          def delete
  
            begin
              
            
              question = QuestionOuverte.find(params[:id])
              if question.update_attributes(question_param_delete)
                render json: {status: 'SUCCESS', message: 'Deleted Question', data:question}, status: :ok
              else
                render json: {status: 'ERROR', message: 'Question not Deleted', data:QuestionOuverte.errors}, status: :unprocessable_entity
              end
              rescue ActiveRecord::RecordNotFound => e
              render json: {
                status: 'ERROR',
                error: e.to_s
              }, status: :not_found
  
            end
          end
  
          # Modifier une question ouverte
          def update
  
            begin
              
              question = QuestionOuverte.find(params[:id])
              if question.update_attributes(question_params)
                render json: {status: 'SUCCESS', message:'Updated question', data:question},status: :ok
              else
                render json: {status: 'ERROR', message:'question not updated', data:QuestionOuverte.errors},status: :unprocessable_entity
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
          def question_params
  
              params.permit(:id_question, :nombreDeCaractere, :intitule, :estObligatoire, :ordre, :etat, :id_sondage)
          end
  
          # parametres de suppression
          def question_param_delete
            params.permit(:etat)
          end
        
  
  
          
        end
  