  ################################
  #   Groupequestions Controller
  # #############################
  #
  # Expose des services REST sous format Json:
  #   - Afficher la liste des groupes de questions
  #   - Afficher un groupe de question par ID
  #   - Creer un nouveau groupe de question
  #   - Modifier un groupe de question
  #   - Supprimer un groupe de question par ID

  # Si l'attribut 'etat' a la valeur 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

  class GroupequestionsController < ApplicationController
    
    # Afficher la liste des GroupeQuestion
    def index
      questions = GroupeQuestion.where(etat: false).order('sondage_id ASC, ordre ASC');
      render json: questions, status: :ok
    end
  
  
    # Afficher un GroupeQuestion par ID
    def show
  
      questions = GroupeQuestion.find_by(id_question: params[:id], etat: false);
  
      if questions != nil
        render json: questions, status: :ok
      else
        render json: nil, status: :not_found
      end
  
  end
  
  # Creer un nouveau GroupeQuestion
  def create
    
    questions = GroupeQuestion.new(question_params)
  
    if questions.save
      render json: questions, status: :ok
    else
      render json: nil, status: :unprocessable_entity
    end
  
  end
  
  # Modifier un GroupeQuestion
  def update
        
    questions = GroupeQuestion.find_by(id_question: params[:id], etat: false);
  
    if questions != nil && questions.update_attributes(question_params)
      render json: questions, status: :ok
    else
      render json: nil, status: :not_found
    end
  
  
  end
  
  # Supprimer un GroupeQuestion par ID
  def delete
  
    questions = GroupeQuestion.find_by(id_question: params[:id], etat: false);
  
    if questions != nil && questions.update_attributes(question_param_delete)
      render json: questions, status: :ok
    else
      render json: nil, status: :not_found
    end
  
  end
  
    # Liste des parametres à fournir
    private
    
    # parametres d'ajout
    def question_params
        params.permit(:intitule, :estObligatoire, :numerosDeQuestionsGroupe, :ordre, :sondage_id)
    end
  
    # parametres de suppression
    def question_param_delete
      params.permit(:etat)
    end
    
  end