class Administrateur < ApplicationRecord

    self.primary_key = :id_administrateur
    validates :pseudo_administrateur, presence: true
    validates :email_administrateur, presence: true
    validates :motDePasse_administrateur, presence: true
end
