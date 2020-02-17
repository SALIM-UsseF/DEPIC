# DPIC

Démocratie participative et d’inclusion citoyenne

Une solution logiciel qui implémentera les fonctionnalités de base d’une démocratie participative et d’inclusion citoyenne. L’objectif principal est de permettre de sonder les citoyens sur des sujets divers et variés.

## Installation BACK-END RUBY ON RAILS

Installer RVM

```bash
installer rvm:

https://rvm.io/rvm/install

sudo apt-get update

Déconnectez-vous et connectez-vous à votre système
Ouvrez un nouveau terminal et exécutez:

source $HOME/.rvm/scripts/rvm

sudo apt-get update

```

Installer RUBY ON RAILS

```bash
Installer Ruby:
rvm install 2.6.5

installer rails :
gem install rails --version 5.2.4.1

```

Configurer PostgreSQL

```bash
Essayez de créer un utilisateur avec mot de passe dans psql:

CREATE USER Myname WITH PASSWORD 'your_password';

```

Configurer Ruby on Rails

```bash
cd /DEPIC/back-end

modifier le fichier database.yml :

username: Myname
password: your_password

Dans le terminal lancer:
rake db:drop db:create db:migrate db:seed

```

## Usage

```python
cd DEPIC/back-end

bundle install

lancer le serveur:
rails s

ou

lancer le serveur avec des paramètres personnaliser:
rails server --binding 192.168.43.24 --port 3100
 
```

```python
Exemple d'utilisation de l'authentification JWT en ligne de commande

Lancer:

curl -H "Content-Type: application/json" -X POST -d '{"email":"reactapp@gmail.com","password":"reactapptest"}' http://localhost:3100/authenticate

Une fois cette commade est lancée, un token a été généré sous format :

{"auth_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE0NjA2NTgxODZ9.xsSwcPC22IR71OBv6bU_OGCSyfE89DvEzWfDU0iybAZ"}

Utilser ce token pour obtenir par exemple une liste des sondages:

curl -H "Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE0NjA2NTgxODZ9.xsSwcPC22IR71OBv6bU_OGCSyfE89DvEzWfDU0iybAZ" http://localhost:3100/sondages

 
```