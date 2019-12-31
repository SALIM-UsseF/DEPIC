class QuestionChoixSerializer < ActiveModel::Serializer
  attributes :id_question, :sondage_id, :intitule, :lesChoix, :estObligatoire, :estUnique, :ordre
end
