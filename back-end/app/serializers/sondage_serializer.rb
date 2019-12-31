class SondageSerializer < ActiveModel::Serializer
  attributes :id_sondage, :intituleSondage, :descriptionSondage, :administrateur_id
end
