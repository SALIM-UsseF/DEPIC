class Utilisateur < ApplicationRecord

    validates :email, presence: true
    validates :adresseIp, presence: true

    validates :etat, presence: true

end
