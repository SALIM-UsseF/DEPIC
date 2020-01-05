class QuestionPointSerializer < ActiveModel::Serializer
  attributes :id_question, :sondage_id, :intitule, :estObligatoire, :minPoints, :maxPoints, :ordre, :type
end
