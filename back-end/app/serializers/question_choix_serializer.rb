class QuestionChoixSerializer < ActiveModel::Serializer
  attributes :id_question, :sondage_id, :intitule, :estObligatoire, :estUnique, :ordre, :type
end
