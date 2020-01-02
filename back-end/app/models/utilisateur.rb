class Utilisateur < ApplicationRecord
    validates :email, presence: false
    validates :adresseIp, presence: false
end
