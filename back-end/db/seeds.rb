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
    motDePasse_administrateur: 'admin', # changer motDePasse : admin par => md5
    supAdmin: 1,
    etat: 0
})
