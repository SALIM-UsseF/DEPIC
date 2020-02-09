################################
#   Groupe Model
# #############################
#   integer - id_groupe
#   integer - id_question
#   boolean - etat
#   datetime - created_at
#   datetime - updated_at

class Groupe < ApplicationRecord
    self.primary_key = "id_groupe"
    belongs_to :question, foreign_key: "id_question"

    self.primary_key = "id_question"
    belongs_to :question, foreign_key: "id_question"

    validates :id_groupe, presence: true
    validates :id_question, presence: true
end
