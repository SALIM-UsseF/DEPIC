class QuestionOuverteSerializer < ActiveModel::Serializer
  attributes :id_question, :intitule, :estObligatoire, :nombreDeCaractere, :ordre, :type
end
