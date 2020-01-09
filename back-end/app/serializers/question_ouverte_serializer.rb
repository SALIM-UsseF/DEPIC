class QuestionOuverteSerializer < ActiveModel::Serializer
  attributes :id_question, :sondage_id, :intitule, :estObligatoire, :nombreDeCaractere, :ordre, :type
end