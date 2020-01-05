class QuestionChoixSerializer < ActiveModel::Serializer
  attributes :id_question, :sondage_id, :intitule, :estObligatoire, :lesChoix, :estUnique, :ordre, :type
end
