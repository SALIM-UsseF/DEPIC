################################
#   Question ouverte Controller
# #############################
#
# Expose des services REST sous format Json:
#   - Afficher la liste des questions ouvertes
#   - Afficher une question ouverte par ID
#   - Creer une nouvelle question ouverte 
#   - Modifier une question ouverte
#   - Supprimer une question ouverte par ID

# Si l'attribut 'etat' a la valeur 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class QuestionouvertesController < ApplicationController
  
  # Afficher la liste des questions ouvertes
  def index
    questions = QuestionOuverte.where(etat: false).order('sondage_id ASC, ordre ASC');
    render json: questions, status: :ok
  end


  # Afficher une QuestionOuverte par ID
  def show

    questions = QuestionOuverte.find_by(id_question: params[:id], etat: false);

    if questions != nil
      render json: questions, status: :ok
    else
      render json: nil, status: :not_found
    end

end

# Creer une nouvelle QuestionOuverte
def create
  
  questions = QuestionOuverte.new(question_params)

  if questions.save
    render json: questions, status: :ok
  else
    render json: nil, status: :unprocessable_entity
  end

end

# Modifier une QuestionOuverte
def update
      
  questions = QuestionOuverte.find_by(id_question: params[:id], etat: false);

  if questions != nil && questions.update_attributes(question_params)
    render json: questions, status: :ok
  else
    render json: nil, status: :not_found
  end


end

# Supprimer une QuestionOuverte par ID
def delete

  questions = QuestionOuverte.find_by(id_question: params[:id], etat: false);

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
      params.permit(:intitule, :estObligatoire, :nombreDeCaractere, :ordre, :sondage_id)
  end

  # parametres de suppression
  def question_param_delete
    params.permit(:etat)
  end



  
end