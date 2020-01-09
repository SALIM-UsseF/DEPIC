class QuestionChoixSerializer < ActiveModel::Serializer
  attributes :id_question, :sondage_id, :intitule, :estObligatoire, :nombreChoix, :estUnique, :ordre, :type
end
