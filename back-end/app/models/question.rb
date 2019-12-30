class Question < ApplicationRecord

    self.abstract_class = true
    
    validates :intitule, presence: true
    validates :estObligatoire, presence: true
    validates :ordre, presence: true
    validates :etat, presence: true
    validates :id_sondage, presence: true
end
