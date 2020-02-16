################################
#   Categorie Model
# #############################
#   integer - id_categorie
#   text - intitule
#   boolean - etat
#   datetime - created_at
#   datetime - updated_at

class Categorie < ApplicationRecord
    scope :active_cat, -> { where(etat: false)}
    scope :inactive_cat, -> { where(etat: true)}

    validates :intitule, presence: true
end
