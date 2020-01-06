################################
#   question choix Controller
# #############################
#
# Expose des services REST :
#   - Afficher la liste des questions choix 
#   - Afficher une question choix par ID
#   - Creer une nouvelle question choix 
#   - Modifier une question choix
#   - Supprimer une question choix par ID

class QuestionchoixesController < ApplicationController

  
  # Afficher la liste des questions choix
  def index
    questions = QuestionChoix.where(etat: false).order('sondage_id ASC, ordre ASC');
    render json: questions, status: :ok
  end


  # Afficher un QuestionChoix par ID
  def show

    questions = QuestionChoix.find_by(id_question: params[:id], etat: false);

    if questions != nil
      render json: questions, status: :ok
    else
      render json: nil, status: :not_found
    end

end

# Creer un nouveau QuestionChoix
def create
  
  questions = QuestionChoix.new(question_params)

  if questions.save
    render json: questions, status: :ok
  else
    render json: nil, status: :unprocessable_entity
  end

end

# Modifier un QuestionChoix
def update
      
  questions = QuestionChoix.find_by(id_question: params[:id], etat: false);

  if questions != nil && questions.update_attributes(question_params)
    render json: questions, status: :ok
  else
    render json: nil, status: :not_found
  end


end

# Supprimer un QuestionChoix par ID
def delete

  questions = QuestionChoix.find_by(id_question: params[:id], etat: false);

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
      params.permit(:intitule, :estObligatoire, :estUnique, :lesChoix, :ordre, :sondage_id)
  end

  # parametres de suppression
  def question_param_delete
    params.permit(:etat)
  end

end