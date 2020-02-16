################################
#   Administrateur Model
# #############################
#   string - pseudo_administrateur
#   string - email_administrateur
#   text - motDePasse_administrateur
#   boolean - supAdmin
#   boolean - etat
#   datetime - created_at
#   datetime - updated_at

class Administrateur < ApplicationRecord

    scope :active_admin, -> { where(etat: false)}
    scope :inactive_admin, -> { where(etat: true)}

    self.primary_key = :id_administrateur
    validates :pseudo_administrateur, presence: true
    validates :email_administrateur, presence: true
    validates :motDePasse_administrateur, presence: true
end
