class QuestionOuverteSerializer < ActiveModel::Serializer
  attributes :id_question, :sondage_id, :intitule, :nombreDeCaractere, :estObligatoire, :ordre
end
