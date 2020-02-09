class SondageSerializer < ActiveModel::Serializer
  attributes :id_sondage, :intituleSondage, :descriptionSondage, :categorie_id
end
