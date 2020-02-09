################################
#   Categorie Model
# #############################
#   integer - id_categorie
#   text - intitule
#   boolean - etat
#   datetime - created_at
#   datetime - updated_at

class Categorie < ApplicationRecord
    validates :intitule, presence: true
end
