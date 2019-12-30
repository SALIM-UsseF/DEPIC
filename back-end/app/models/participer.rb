class Participer < ApplicationRecord

    self.primary_key = "id_utilisateur"
    belongs_to :utilisateur, foreign_key: "id_utilisateur"

    self.primary_key = "id_sondage"
    belongs_to :sondage, foreign_key: "id_sondage"

    self.primary_key = "id_question"
    belongs_to :sondage, foreign_key: "id_question"

    
    validates :reponse, presence: true

    validates :etat, presence: true
end
