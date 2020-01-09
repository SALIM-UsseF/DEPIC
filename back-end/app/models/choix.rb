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
    validates :intituleChoix, presence: true
    validates :question_id, presence: true
end
