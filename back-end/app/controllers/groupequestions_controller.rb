
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
    
  # Afficher la liste des GroupeQuestion
  def index
    questions = GroupeQuestion.where(etat: false).order('created_at ASC');
    render json: {status: 'SUCCESS', message:'Loaded questions', data:questions},status: :ok
  end


  # Afficher un GroupeQuestion par ID
  def show

    questions = GroupeQuestion.find_by(id_question: params[:id], etat: false);

    if questions != nil
      render json: {status: 'SUCCESS', message: 'Loaded GroupeQuestion', data:questions}, status: :ok
    else
      render json: {status: 'ERROR', message: 'GroupeQuestion not found'}, status: :not_found
    end

end

# Creer un nouveau GroupeQuestion
def create
  
  questions = GroupeQuestion.new(question_params)

  if questions.save
    render json: {status: 'SUCCESS', message: 'Saved GroupeQuestion', data:questions}, status: :ok
  else
    render json: {status: 'ERROR', message: 'GroupeQuestion not saved'}, status: :unprocessable_entity
  end

end

# Modifier un GroupeQuestion
def update
      
  questions = GroupeQuestion.find_by(id_question: params[:id], etat: false);

  if questions != nil && questions.update_attributes(question_params)
    render json: {status: 'SUCCESS', message: 'Updated GroupeQuestion', data:questions}, status: :ok
  else
    render json: {status: 'ERROR', message: 'GroupeQuestion not updated'}, status: :not_found
  end


end

# Supprimer un GroupeQuestion par ID
def delete

  questions = GroupeQuestion.find_by(id_question: params[:id], etat: false);

  if questions != nil && questions.update_attributes(question_param_delete)
    render json: {status: 'SUCCESS', message: 'Deleted GroupeQuestion', data:questions}, status: :ok
  else
    render json: {status: 'ERROR', message: 'GroupeQuestion not Deleted'}, status: :not_found
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
