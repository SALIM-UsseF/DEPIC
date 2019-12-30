
         ################################
        #   Groupequestions Controller
        # #############################
        #
        # Expose des service REST :
        #   - Afficher la liste des groupes de questions
        #   - Afficher un groupe de question par ID
        #   - Creer un nouveau groupe de question
        #   - Modifier un groupe de question
        #   - Supprimer un groupe de question par ID

        class GroupequestionsController < ApplicationController
          
        # Afficher la liste des groupes de questions
        def index
          questions = GroupeQuestion.order('created_at ASC');
          render json: {status: 'SUCCESS', message:'Loaded questions', data:questions},status: :ok
        end


        # Afficher un groupe de questions par ID
        def show
          begin
            
          
            
          
            question=GroupeQuestion.find(params[:id])
            render json: {status: 'SUCCESS', message:'Loaded question', data:question},status: :ok
            rescue ActiveRecord::RecordNotFound => e
            render json: {
              status: 'ERROR',
              error: e.to_s
            }, status: :not_found  

          end
        end

        # Creer un nouveau groupe de questions
        def create
            question = GroupeQuestion.new(question_params)
    
            if question.save
            render json: {status: 'SUCCESS', message:'Saved question', data:question},status: :ok
            else
            render json: {status: 'ERROR', message:'Question not saved', data:GroupeQuestion.errors},status: :unprocessable_entity
            end
        end 

        # Supprimer un groupe de question par ID
        def delete

          begin
            
          
            question = GroupeQuestion.find(params[:id])
            if question.update_attributes(question_param_delete)
              render json: {status: 'SUCCESS', message: 'Deleted Question', data:question}, status: :ok
            else
              render json: {status: 'ERROR', message: 'Question not Deleted', data:GroupeQuestion.errors}, status: :unprocessable_entity
            end
            rescue ActiveRecord::RecordNotFound => e
            render json: {
              status: 'ERROR',
              error: e.to_s
            }, status: :not_found

          end
        end

        # Modifier un groupe de questions
        def update

          begin
            
            question = GroupeQuestion.find(params[:id])
            if question.update_attributes(question_params)
              render json: {status: 'SUCCESS', message:'Updated question', data:question},status: :ok
            else
              render json: {status: 'ERROR', message:'question not updated', data:GroupeQuestion.errors},status: :unprocessable_entity
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

            params.permit(:id_question, :intitule, :estObligatoire, :ordre, :etat, :id_sondage, :numerosDeQuestions)
        end

        # parametres de suppression
        def question_param_delete
          params.permit(:etat)
        end
      


        
      end
