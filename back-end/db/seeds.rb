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
    lesChoix: "Ordinateur fixe;Ordinateur portable;Tablette;Smartphone;Aucun de cette liste",
    estUnique: false,
	ordre: 1,
	sondage_id: 1
})

#Question choix multiple
QuestionChoix.create({
	intitule: "Quels usages faites-vous de votre smartphone ?",
    estObligatoire: true,
    lesChoix: "Surfer sur internet;Regarder des vidéos;Aller sur les réseaux sociaux;Jouer à des jeux mobiles;Effectuer des réservations ou des achats en ligne",
    estUnique: false,
	ordre: 2,
	sondage_id: 1
})

#Question choix unique
QuestionChoix.create({
	intitule: "A quelle fréquence utilisez-vous votre smartphone pour surfer sur internet ou consulter vos mails ou vos messages ?",
    estObligatoire: true,
    lesChoix: "Plusieurs fois par jour;Au moins une fois par jour;Plusieurs fois par semaine;1 fois par semaine;Plus occasionnellement",
    estUnique: true,
	ordre: 3,
	sondage_id: 1
})

#Question choix unique
QuestionChoix.create({
	intitule: "Avez-vous l’habitude de porter des ornements au niveau des poignets (tels que des bracelets, montres, bijoux,…) ?",
    estObligatoire: true,
    lesChoix: "Oui, tous les jours;Oui, pour des occasions particulières;Non, je n’en porte pas",
    estUnique: true,
	ordre: 4,
	sondage_id: 1
})

#Question choix unique
QuestionChoix.create({
	intitule: "Êtes-vous intéressé(e) par ce nouveau concept de bracelet connecté/télécommande universelle ?",
    estObligatoire: true,
    lesChoix: "Très intéressé(e);Plutôt intéressé(e);Plutôt pas intéressé(e);Pas du tout intéressé(e)",
    estUnique: true,
	ordre: 5,
	sondage_id: 1
})

#Question choix multiple
QuestionChoix.create({
	intitule: "Quelles sont les fonctionnalités qui vous plaisent le plus ?",
    estObligatoire: true,
    lesChoix: "L’écran à affichage permanent;Le système de saisie vocale;La réception des notifications (mails, messages,…);L’accessibilité de l’interface pour tous, notamment pour les personnes malvoyantes",
    estUnique: false,
	ordre: 6,
	sondage_id: 1
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
    lesChoix: "Moins de 50€;Entre 50 et 74€;Entre 75 et 99€;Entre 100 et 124€;Entre 175 et 199€;200€ et plus",
    estUnique: true,
	ordre: 8,
	sondage_id: 1
})

#Question choix unique
QuestionChoix.create({
	intitule: "Si elle était vendue à un prix qui vous convenait… Seriez-vous susceptible d’acheter ce bracelet connecté?",
    estObligatoire: true,
    lesChoix: "Oui, certainement;Oui, probablement;Non, probablement pas;Non, certainement pas",
    estUnique: true,
	ordre: 9,
	sondage_id: 1
})

#Question ouverte
QuestionOuverte.create({
	intitule: "Si vous avez des remarques, suggestions ou critiques à soumettre au créateur de ce nouveau bracelet connecté, veuillez les indiquer ci-dessous svp :",
	estObligatoire: false,
	nombreDeCaractere: 100,
	ordre: 10,
	sondage_id: 1
})
##############################                  "END SIMULATION SONDAGE"                           ###########################

Sondage.create({
	intituleSondage: "Sondage Test 2",
    descriptionSondage: "Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression.
     Le Lorem Ipsum est le faux texte standard de l'imprimerie depuis les années 1500.",
	administrateur_id: 1
})