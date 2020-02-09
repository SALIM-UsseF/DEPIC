class GroupeQuestionSerializer < ActiveModel::Serializer
  attributes :id_question, :sondage_id, :intitule, :estObligatoire, :ordre, :type
end
