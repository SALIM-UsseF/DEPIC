# DPIC

Démocratie participative et d’inclusion citoyenne

Il s’agit de développer une solution logiciel qui implémentera les fonctionnalités de base d’une démocratie participative et d’inclusion citoyenne. L’objectif principal est de permettre de sonder les citoyens sur des sujets divers et variés.

## Installation BACK-END RUBY ON RAILS

Installer RVM

```bash
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