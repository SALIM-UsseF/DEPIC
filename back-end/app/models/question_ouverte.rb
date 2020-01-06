################################
#   QuestionOuverte Model
# #############################
#   integer - nombreDeCaractere

class QuestionOuverte < Question
    validates :nombreDeCaractere, numericality: { greater_than_or_equal_to: 0}
end
