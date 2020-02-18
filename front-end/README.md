# Installation FRONT-END

Le but de ce projet est de créer une application web permettant la gestion de sondages par les administrateurs. De plus, cette application permet aussi la publication des sondages.

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