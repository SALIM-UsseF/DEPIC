################################
#   QuestionPoint Model
# #############################
#   integer - minPoints
#   integer - maxPoints

class QuestionPoint < Question
    validates :minPoints, numericality: { greater_than_or_equal_to: 0}
    validates :maxPoints, numericality: { greater_than_or_equal_to: 0}
end
