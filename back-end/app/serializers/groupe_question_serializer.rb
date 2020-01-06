class GroupeQuestionSerializer < ActiveModel::Serializer
  attributes :id_question, :sondage_id, :intitule, :estObligatoire, :numerosDeQuestionsGroupe, :ordre, :type
end
