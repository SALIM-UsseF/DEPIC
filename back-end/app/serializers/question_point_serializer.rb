class QuestionPointSerializer < ActiveModel::Serializer
  attributes :id_question, :sondage_id, :intitule, :minPoints, :maxPoints, :estObligatoire, :ordre
end
