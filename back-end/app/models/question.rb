class Question < ApplicationRecord
    validates :intitule, presence: true
    validates :sondage_id, presence: true
end
