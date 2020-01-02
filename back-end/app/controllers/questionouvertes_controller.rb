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
    questions = QuestionOuverte.where(etat: false).order('created_at ASC');
    render json: {status: 'SUCCESS', message:'Loaded questions', data:questions},status: :ok
  end


  # Afficher un QuestionOuverte par ID
  def show

    questions = QuestionOuverte.find_by(id_question: params[:id], etat: false);

    if questions != nil
      render json: {status: 'SUCCESS', message: 'Loaded QuestionOuverte', data:questions}, status: :ok
    else
      render json: {status: 'ERROR', message: 'QuestionOuverte not found'}, status: :not_found
    end

end

# Creer un nouveau QuestionOuverte
def create
  
  questions = QuestionOuverte.new(question_params)

  if questions.save
    render json: {status: 'SUCCESS', message: 'Saved QuestionOuverte', data:questions}, status: :ok
  else
    render json: {status: 'ERROR', message: 'QuestionOuverte not saved'}, status: :unprocessable_entity
  end

end

# Modifier un QuestionOuverte
def update
      
  questions = QuestionOuverte.find_by(id_question: params[:id], etat: false);

  if questions != nil && questions.update_attributes(question_params)
    render json: {status: 'SUCCESS', message: 'Updated QuestionOuverte', data:questions}, status: :ok
  else
    render json: {status: 'ERROR', message: 'QuestionOuverte not updated'}, status: :not_found
  end


end

# Supprimer un QuestionOuverte par ID
def delete

  questions = QuestionOuverte.find_by(id_question: params[:id], etat: false);

  if questions != nil && questions.update_attributes(question_param_delete)
    render json: {status: 'SUCCESS', message: 'Deleted QuestionOuverte', data:questions}, status: :ok
  else
    render json: {status: 'ERROR', message: 'QuestionOuverte not Deleted'}, status: :not_found
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
