class QuestionOuverte < ApplicationRecord

    self.primary_key = "id_question"
    belongs_to :question, foreign_key: "id_question"

    validates :intitule, presence: true
    validates :estObligatoire, presence: true
    validates :ordre, presence: true
    validates :etat, presence: true
    validates :nombreDeCaractere, presence: true
    validates :id_sondage, presence: true
end
