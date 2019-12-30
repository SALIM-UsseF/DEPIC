
         ################################
        #   question Controller
        # #############################
        #
        # Expose des service REST :
        #   - Afficher la liste des questions 
        #   - Afficher une question par ID
        #   - Creer une nouvelle question 
        #   - Modifier une question 
        #   - Supprimer une question par ID

        class QuestionController < ApplicationController
          
          # Afficher la liste des questions 
          def index
            questions = Question.order('created_at ASC');
            render json: {status: 'SUCCESS', message:'Loaded questions', data:questions},status: :ok
          end
  
  
          # Afficher une question par ID
          def show
            begin
              
        
              question=Question.find(params[:id])
              render json: {status: 'SUCCESS', message:'Loaded question', data:question},status: :ok
              rescue ActiveRecord::RecordNotFound => e
              render json: {
                status: 'ERROR',
                error: e.to_s
              }, status: :not_found  
  
            end
          end
  
          # Creer une nouvelle question 
          def create
              question = Question.new(question_params)
      
              if question.save
              render json: {status: 'SUCCESS', message:'Saved question', data:question},status: :ok
              else
              render json: {status: 'ERROR', message:'Question not saved', data:Question.errors},status: :unprocessable_entity
              end
          end 
  
          # Supprimer une question par ID
          def delete
  
            begin
              
            
              question = Question.find(params[:id])
              if question.update_attributes(question_param_delete)
                render json: {status: 'SUCCESS', message: 'Deleted Question', data:question}, status: :ok
              else
                render json: {status: 'ERROR', message: 'Question not Deleted', data:Question.errors}, status: :unprocessable_entity
              end
              rescue ActiveRecord::RecordNotFound => e
              render json: {
                status: 'ERROR',
                error: e.to_s
              }, status: :not_found
  
            end
          end
  
          # Modifier une question 
          def update
  
            begin
              
              question = Question.find(params[:id])
              if question.update_attributes(question_params)
                render json: {status: 'SUCCESS', message:'Updated question', data:question},status: :ok
              else
                render json: {status: 'ERROR', message:'question not updated', data:Question.errors},status: :unprocessable_entity
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
  
              params.permit(:intitule, :estObligatoire, :ordre, :etat, :id_sondage)
          end
  
          # parametres de suppression
          def question_param_delete
            params.permit(:etat)
          end
        
  
  
          
        end
  