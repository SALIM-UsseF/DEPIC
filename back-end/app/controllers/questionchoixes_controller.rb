################################
#   question choix Controller
# #############################
#
# Expose des service REST :
#   - Afficher la liste des questions choix 
#   - Afficher une question choix par ID
#   - Creer une nouvelle question choix 
#   - Modifier une question choix
#   - Supprimer une question choix par ID

class QuestionchoixesController < ApplicationController
  
  # Afficher la liste des questions choix
  def index
    questions = QuestionChoix.where(etat: false).order('created_at ASC');
    render json: {status: 'SUCCESS', message:'Loaded questions', data:questions},status: :ok
  end


  # Afficher un QuestionChoix par ID
  def show

    questions = QuestionChoix.find_by(id_question: params[:id], etat: false);

    if questions != nil
      render json: {status: 'SUCCESS', message: 'Loaded QuestionChoix', data:questions}, status: :ok
    else
      render json: {status: 'ERROR', message: 'QuestionChoix not found'}, status: :not_found
    end

end

# Creer un nouveau QuestionChoix
def create
  
  questions = QuestionChoix.new(question_params)

  if questions.save
    render json: {status: 'SUCCESS', message: 'Saved QuestionChoix', data:questions}, status: :ok
  else
    render json: {status: 'ERROR', message: 'QuestionChoix not saved'}, status: :unprocessable_entity
  end

end

# Modifier un QuestionChoix
def update
      
  questions = QuestionChoix.find_by(id_question: params[:id], etat: false);

  if questions != nil && questions.update_attributes(question_params)
    render json: {status: 'SUCCESS', message: 'Updated QuestionChoix', data:questions}, status: :ok
  else
    render json: {status: 'ERROR', message: 'QuestionChoix not updated'}, status: :not_found
  end


end

# Supprimer un QuestionChoix par ID
def delete

  questions = QuestionChoix.find_by(id_question: params[:id], etat: false);

  if questions != nil && questions.update_attributes(question_param_delete)
    render json: {status: 'SUCCESS', message: 'Deleted QuestionChoix', data:questions}, status: :ok
  else
    render json: {status: 'ERROR', message: 'QuestionChoix not Deleted'}, status: :not_found
  end

end

  # Liste des parametres à fournir

  private
  
  # parametres d'ajout
  def question_params

      params.permit(:id_question, :estUnique, :lesChoix, :intitule, :estObligatoire, :ordre, :etat, :id_sondage)
  end

  # parametres de suppression
  def question_param_delete
    params.permit(:etat)
  end



  
end
