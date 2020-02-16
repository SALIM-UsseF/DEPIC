################################
#   Choix Model
# #############################
#   integer - id_choix
#   text - intituleChoix
#   boolean - etat
#   integer - question_id
#   datetime - created_at
#   datetime - updated_at

class Choix < ApplicationRecord
    scope :active_choix, -> { where(etat: false)}
    scope :inactive_choix, -> { where(etat: true)}

    validates :intituleChoix, presence: true
    validates :question_id, presence: true
end
