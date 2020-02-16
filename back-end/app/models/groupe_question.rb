################################
#   GroupeQuestion Model
# #############################

class GroupeQuestion < Question

    scope :active_question, -> { where(etat: false)}
    scope :inactive_question, -> { where(etat: true)}
    
end
