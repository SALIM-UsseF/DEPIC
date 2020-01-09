# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: 'Star Wars' }, { name: 'Lord of the Rings' }])
#   Character.create(name: 'Luke', movie: movies.first)

# Insertion dans la Table Administrateur
# Ajouter le super administrateur
Administrateur.create({
    pseudo_administrateur: 'admin',
    email_administrateur: 'admin@test.com',
    motDePasse_administrateur: '21232f297a57a5a743894a0e4a801fc3',
    supAdmin: true, # true = admin principale
    etat: false # true = admin est supprimé 
})

# Insertion dans la Table utilisateur
Utilisateur.create({
	email: "user1@user.com",
    adresseIp: "30.171.172.52"
})

# Insertion dans la Table utilisateur => user sans email et adresseIP
user = Utilisateur.new();
user.save

####################################################         "SIMULATION D'UN SONDAGE"        ####################################################
# Insertion dans la Table Sondage
Sondage.create({
	intituleSondage: "Bracelet connecté",
    descriptionSondage: "Il s’agit de la création d’un bracelet connecté faisant office de télécommande universelle basé sur un nouveau système de commande à distance et de saisie de texte.
    Ce bracelet sera composé d’un écran à affichage permanent, lisible en pleine lumière sans rétro-éclairage et d’une zone tactile de contrôle de 25mm de diamètre.",
	administrateur_id: 1
})

#Les questions du sondage N°1
#Question choix multiple
QuestionChoix.create({
	intitule: "Parmi les équipements technologiques suivants, lesquels possédez-vous ?",
    estObligatoire: true,
    nombreChoix: 5,
    estUnique: false,
	ordre: 1,
	sondage_id: 1
})

Choix.create({
	intituleChoix: "Ordinateur fixe",
	question_id: 1
})

Choix.create({
	intituleChoix: "Ordinateur portable",
	question_id: 1
})

Choix.create({
	intituleChoix: "Tablette",
	question_id: 1
})

Choix.create({
	intituleChoix: "Smartphone",
	question_id: 1
})

Choix.create({
	intituleChoix: "Aucun de cette liste",
	question_id: 1
})

#Question choix multiple
QuestionChoix.create({
	intitule: "Quels usages faites-vous de votre smartphone ?",
    estObligatoire: true,
    nombreChoix: 5,
    estUnique: false,
	ordre: 2,
	sondage_id: 1
})

Choix.create({
	intituleChoix: "Surfer sur internet",
	question_id: 2
})

Choix.create({
	intituleChoix: "Regarder des vidéos",
	question_id: 2
})

Choix.create({
	intituleChoix: "Aller sur les réseaux sociaux",
	question_id: 2
})

Choix.create({
	intituleChoix: "Jouer à des jeux mobiles",
	question_id: 2
})

Choix.create({
	intituleChoix: "Effectuer des réservations ou des achats en ligne",
	question_id: 2
})

#Question choix unique
QuestionChoix.create({
	intitule: "A quelle fréquence utilisez-vous votre smartphone pour surfer sur internet ou consulter vos mails ou vos messages ?",
    estObligatoire: true,
    nombreChoix: 5,
    estUnique: true,
	ordre: 3,
	sondage_id: 1
})

Choix.create({
	intituleChoix: "Plusieurs fois par jour",
	question_id: 3
})

Choix.create({
	intituleChoix: "Au moins une fois par jour",
	question_id: 3
})

Choix.create({
	intituleChoix: "Plusieurs fois par semaine",
	question_id: 3
})

Choix.create({
	intituleChoix: "1 fois par semaine",
	question_id: 3
})

Choix.create({
	intituleChoix: "Plus occasionnellement",
	question_id: 3
})

#Question choix unique
QuestionChoix.create({
	intitule: "Avez-vous l’habitude de porter des ornements au niveau des poignets (tels que des bracelets, montres, bijoux,…) ?",
    estObligatoire: true,
    nombreChoix: 3,
    estUnique: true,
	ordre: 4,
	sondage_id: 1
})

Choix.create({
	intituleChoix: "Oui, tous les jours",
	question_id: 4
})

Choix.create({
	intituleChoix: "Oui, pour des occasions particulières",
	question_id: 4
})

Choix.create({
	intituleChoix: "Non, je n’en porte pas",
	question_id: 4
})

#Question choix unique
QuestionChoix.create({
	intitule: "Êtes-vous intéressé(e) par ce nouveau concept de bracelet connecté/télécommande universelle ?",
    estObligatoire: true,
    nombreChoix: 4,
    estUnique: true,
	ordre: 5,
	sondage_id: 1
})

Choix.create({
	intituleChoix: "Très intéressé(e)",
	question_id: 5
})

Choix.create({
	intituleChoix: "Plutôt intéressé(e)",
	question_id: 5
})

Choix.create({
	intituleChoix: "Plutôt pas intéressé(e)",
	question_id: 5
})

Choix.create({
	intituleChoix: "Pas du tout intéressé(e)",
	question_id: 5
})

#Question choix multiple
QuestionChoix.create({
	intitule: "Quelles sont les fonctionnalités qui vous plaisent le plus ?",
    estObligatoire: true,
    nombreChoix: 4,
    estUnique: false,
	ordre: 6,
	sondage_id: 1
})

Choix.create({
	intituleChoix: "L’écran à affichage permanent",
	question_id: 6
})

Choix.create({
	intituleChoix: "Le système de saisie vocale",
	question_id: 6
})

Choix.create({
	intituleChoix: "La réception des notifications (mails, messages,…)",
	question_id: 6
})

Choix.create({
	intituleChoix: "L’accessibilité de l’interface pour tous, notamment pour les personnes malvoyantes",
	question_id: 6
})


#Groupe de questions :
#Question ouverte
QuestionOuverte.create({
	intitule: "Matière ?",
	estObligatoire: false,
	nombreDeCaractere: 100,
	ordre: 1,
	sondage_id: 1
})

#Question ouverte
QuestionOuverte.create({
	intitule: "Coloris possibles ?",
	estObligatoire: false,
	nombreDeCaractere: 100,
	ordre: 2,
	sondage_id: 1
})

#Question ouverte
QuestionOuverte.create({
	intitule: "Largeur du bracelet ?",
	estObligatoire: false,
	nombreDeCaractere: 100,
	ordre: 3,
	sondage_id: 1
})

#Question Groupe
GroupeQuestion.create({
	intitule: "Quelles seraient vos attentes concernant le design de ce bracelet connecté ?",
	estObligatoire: false,
	numerosDeQuestionsGroupe: "7;8;9",
	ordre: 7,
	sondage_id: 1
})

#Question choix unique
QuestionChoix.create({
	intitule: "Quel serait le prix maximum que vous seriez prêt à payer pour acheter ce bracelet connecté de type télécommande universelle ?",
    estObligatoire: true,
    nombreChoix: 6,
    estUnique: true,
	ordre: 8,
	sondage_id: 1
})

Choix.create({
	intituleChoix: "Moins de 50€",
	question_id: 11
})

Choix.create({
	intituleChoix: "Entre 50 et 74€",
	question_id: 11
})

Choix.create({
	intituleChoix: "Entre 75 et 99€",
	question_id: 11
})

Choix.create({
	intituleChoix: "Entre 100 et 124€",
	question_id: 11
})

Choix.create({
	intituleChoix: "Entre 175 et 199€",
	question_id: 11
})

Choix.create({
	intituleChoix: "200€ et plus",
	question_id: 11
})

#Question choix unique
QuestionChoix.create({
	intitule: "Si elle était vendue à un prix qui vous convenait… Seriez-vous susceptible d’acheter ce bracelet connecté?",
    estObligatoire: true,
    nombreChoix: 4,
    estUnique: true,
	ordre: 9,
	sondage_id: 1
})

Choix.create({
	intituleChoix: "Oui, certainement",
	question_id: 12
})

Choix.create({
	intituleChoix: "Oui, probablement",
	question_id: 12
})

Choix.create({
	intituleChoix: "Non, probablement pas",
	question_id: 12
})

Choix.create({
	intituleChoix: "Non, certainement pas",
	question_id: 12
})

#Question ouverte
QuestionPoint.create({
	intitule: "Donner une note pour le bracelet connecté iB20:",
	estObligatoire: false,
	minPoints: 0,
	maxPoints: 10,
	ordre: 10,
	sondage_id: 1
})

#Question ouverte
QuestionOuverte.create({
	intitule: "Si vous avez des remarques, suggestions ou critiques à soumettre au créateur de ce nouveau bracelet connecté, veuillez les indiquer ci-dessous svp :",
	estObligatoire: false,
	nombreDeCaractere: 100,
	ordre: 11,
	sondage_id: 1
})
##############################                  "END SIMULATION SONDAGE"                           ###########################

Sondage.create({
	intituleSondage: "Sondage Test 2",
    descriptionSondage: "Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression.
     Le Lorem Ipsum est le faux texte standard de l'imprimerie depuis les années 1500.",
	administrateur_id: 1
})