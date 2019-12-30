class Sondage < ApplicationRecord


    validates :intituleSondage, presence: true
    validates :descriptionSondage, presence: true
    validates :etat, presence: true
    validates :id_administrateur, presence: true
end
