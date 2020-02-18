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
rvm install ruby-2.6.3

installer rails :
gem install rails --version 5.2.4.1

```

Configurer PostgreSQL

```bash
Essayez de créer un utilisateur avec mot de passe dans psql:

CREATE USER Myname WITH PASSWORD 'your_password';

Editer le fichier pg_hba.conf:

/etc/postgresql/votreVersion/main/pg_hba.conf

Changer la ligne:
local   all             postgres                                peer

par:

local   all             postgres                                md5

Redémarrer postgresql:
sudo service postgresql restart

```

Configurer Ruby on Rails

```bash
cd /DEPIC/back-end

modifier le fichier database.yml :

username: Myname
password: your_password

```

## Usage

```python
cd DEPIC/back-end

gem install bundler:2.1.0

sudo apt-get install libpq-dev

sudo apt-get update

bundle install

En cas d'erreur lancer:
gem uninstall -i /home/utilisateur/.rvm/rubies/ruby-2.6.3/lib/ruby/gems/2.6.0 rubygems-bundler

puis relancer:
bundle install

rake db:create db:migrate db:seed

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

Une fois cette commande est lancée, un token a été généré sous format :

{"auth_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE0NjA2NTgxODZ9.xsSwcPC22IR71OBv6bU_OGCSyfE89DvEzWfDU0iybAZ"}

Utilser ce token pour obtenir par exemple une liste des sondages:

curl -H "Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE0NjA2NTgxODZ9.xsSwcPC22IR71OBv6bU_OGCSyfE89DvEzWfDU0iybAZ" http://localhost:3100/sondages

 
```
# Installation FRONT-END

Application web permettant la gestion de sondages par les administrateurs. De plus, cette application permet aussi la publication des sondages.

## Technologies

Ce projet est développé principalement autour des librairies :
- [Semantic UI React](https://react.semantic-ui.com/) : librairie de composants prédéfinis pour faciliter le développement en React.
- [Axios](https://github.com/axios/axios) : client HTTP assurant la communication client-serveur.

## Installation

Avant d'exécuter l'application, il faut s'assurer que `Node JS` est installé sur votre poste.
Si ce n'est pas le cas, référez vous à la [documentation officielle](https://nodejs.org/fr/) de Node JS pour procéder à son installation.

Pour la gestion de module de Node, il est nécessaire d'installer la version la plus de récente `npm`. Exécuter la commande suivante :
```bash
npm install --global npm
```

Le plus simple est d'installer ces deux technologies à l'aide des commandes suivantes :
```bash
# Installation de la version 12.x (LTS support jusqu'à Novembre 2021)
wget -qO- https://deb.nodesource.com/setup_12.x | sudo -E bash -
sudo apt-get install -y nodejs
```

## Lancement en mode développement

```bash
git clone https://github.com/SALIM-UsseF/DEPIC.git
cd front-end
npm install
npm start
```

## Partie Android

Pour pouvoir utiliser l'application

```bash
Créez un projet android sur AndroidStudio à partir du dossier ApplicationMobileDEPIC

Accédez au fichier ServiceGenerator : 
ApplicationMobileDEPIC/app/src/main/java/com/android/application/applicationmobiledepic/BaseDeDonneesInterne/Retrofit

Changez la variable baseURL pour obtenir http://Adresse_Serveur:Port_A_Utiliser/ 
à partir de l'adresse du serveur et du port attribué

Créez l'apk et l'utiliser pour installer l'application ou installez directement à partir d'AndroidStudio

```
