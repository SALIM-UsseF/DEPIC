class QuestionSerializer < ActiveModel::Serializer
  attributes :id_question, :sondage_id, :intitule, :ordre, :type
end
