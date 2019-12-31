class ParticiperSerializer < ActiveModel::Serializer
  attributes :id_utilisateur, :id_sondage, :id_question, :reponse
end
