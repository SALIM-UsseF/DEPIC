################################
#   Sondage Model
# #############################
#   text - intituleSondage
#   text - descriptionSondage
#   integer - administrateur_id
#   boolean - publier => cet attribut permet la diffusion du sondage si ça valeur égale true
#   boolean - resultats => cet attribut permet de visualiser les résultats du sondage si ça valeur égale true
#   boolean - etat
#   datetime - created_at
#   datetime - updated_at

class Sondage < ApplicationRecord

    scope :active_sondage, -> { where(etat: false)}
    scope :inactive_sondage, -> { where(etat: true)}

    validates :intituleSondage, presence: true
    validates :descriptionSondage, presence: true
    validates :administrateur_id, presence: true
end
