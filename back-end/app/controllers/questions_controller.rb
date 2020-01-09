################################
#   Questions Controller
# #############################
#
# Expose des services REST sous format Json:
#   - Afficher la liste des questions
#   - Afficher une question par ID
#   - Afficher les questions d'un sondage
#   - Supprimer une question par ID
#   - Actions pour la partie Mobile :
#     - Afficher les questions d'un sondage publié
#     - Afficher une question d'un sondage publié

require 'QuestionService'

class QuestionsController < ApplicationController
  
  # Afficher la liste des questions
  def index
    questions = QuestionService.instance.listeDesQuestions
    render json: questions, status: :ok
  end

  # Afficher une question par id
  def show
    question = QuestionService.instance.afficherQuestionParId(params[:id])
    (question != nil) ? (render json: question, status: :ok) : (render json: nil, status: :not_found)
  end


  # Afficher les questions d'un sondage
  def showBySondage
    questions = QuestionService.instance.afficherQuestionsParSondage(params[:id])
    (!questions.empty?) ? (render json: questions, status: :ok) : (render json: nil, status: :not_found)
  end


  ########################################### "Actions pour la partie Mobile" ###########################
  # Afficher les questions d'un sondage publié
  def questionsDuSondage
    questions = QuestionService.instance.afficherQuestionsParSondagePublie(params[:idSondage])
    (questions != nil) ? (render json: questions, status: :ok) : (render json: nil, status: :not_found)
  end

  # Afficher une question d'un sondage publié
  def questionDuSondage
    question = QuestionService.instance.questionDuSondagePublie(params[:idSondage], params[:idQuestion])
    (question != nil) ? (render json: question, status: :ok) : (render json: nil, status: :not_found)
  end
  #######################################################################################################

  
end
