class Sondage < ApplicationRecord
    validates :intituleSondage, presence: true
    validates :descriptionSondage, presence: true
    validates :administrateur_id, presence: true
end
