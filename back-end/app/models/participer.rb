################################
#   Participer Model
# #############################
#   integer - id_utilisateur
#   integer - id_sondage
#   integer - id_question
#   text - reponse
#   boolean - etat
#   datetime - created_at
#   datetime - updated_at

class Participer < ApplicationRecord

    self.primary_key = "id_utilisateur"
    belongs_to :utilisateur, foreign_key: "id_utilisateur"

    self.primary_key = "id_sondage"
    belongs_to :sondage, foreign_key: "id_sondage"

    self.primary_key = "id_question"
    belongs_to :question, foreign_key: "id_question"

    validates :id_utilisateur, presence: true
    validates :id_sondage, presence: true
    validates :id_question, presence: true

end
