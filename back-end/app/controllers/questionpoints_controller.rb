################################
#   question points Controller
# #############################
#
# Expose des services REST :
#   - Afficher la liste des questions points
#   - Afficher une question points par ID
#   - Creer une nouvelle question points 
#   - Modifier une question points
#   - Supprimer une question points par ID

class QuestionpointsController < ApplicationController
  
  # Afficher la liste des questions points
  def index
    questions = QuestionPoint.where(etat: false).order('sondage_id ASC, ordre ASC');
    render json: questions, status: :ok
  end


 # Afficher un QuestionPoint par ID
 def show

  questions = QuestionPoint.find_by(id_question: params[:id], etat: false);

  if questions != nil
    render json: questions, status: :ok
  else
    render json: nil, status: :not_found
  end

end

# Creer un nouveau QuestionPoint
def create

questions = QuestionPoint.new(question_params)

if questions.save
  render json: questions, status: :ok
else
  render json: nil, status: :unprocessable_entity
end

end

# Modifier un QuestionPoint
def update
    
questions = QuestionPoint.find_by(id_question: params[:id], etat: false);

if questions != nil && questions.update_attributes(question_params)
  render json: questions, status: :ok
else
  render json: nil, status: :not_found
end


end

# Supprimer un QuestionPoint par ID
def delete

questions = QuestionPoint.find_by(id_question: params[:id], etat: false);

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
      params.permit(:intitule, :estObligatoire, :minPoints, :maxPoints, :ordre, :sondage_id)
  end

  # parametres de suppression
  def question_param_delete
    params.permit(:etat)
  end
  
end