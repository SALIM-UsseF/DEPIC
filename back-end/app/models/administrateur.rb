class Administrateur < ApplicationRecord
    validates :pseudo_administrateur, presence: true
    validates :email_administrateur, presence: true
    validates :motDePasse_administrateur, presence: true
    validates :motDePasse_administrateur, presence: true
end
