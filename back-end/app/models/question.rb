################################
#   Question Model
# #############################
#   text - intitule
#   boolean - estObligatoire => cet attribut permet de définir si la reponse au question est obligatoire
#   integer - ordre => cet attribut permet de définir la postition du question parmi les autres questions
#   boolean - etat
#   integer - sondage_id
#   datetime - created_at
#   datetime - updated_at

class Question < ApplicationRecord
end
