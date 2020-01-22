import axios from "axios";

class Client {
  constructor(url) {
    this.url = url;
  };

  Administrateur = {

  };

  Sondage = {
    readAll: (params, success, errors) => {
      axios
        .get(this.url + 'sondages', params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'sondage/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  Utilisateur = {

  };

  Participer = {

  };

  Question = {
    read: (id, success, errors) => {
      axios
        .get(this.url + 'question/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    readAll: (params, success, errors) => {
      axios
        .get(this.url + 'questions', params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    readBySondage: (id, success, errors) => {
      axios
        .get(this.url + 'questionsBySondage/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  QuestionOuverte = {

  };

  QuestionChoix = {

  };

  QuestionPoints = {

  };

  GroupQuestions = {

  };

  Resultat = {

  };

  Choix = {
    read: (id, success, errors) => {
      axios
        .get(this.url + 'choixByQuestion/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
  };
}

export default Client;
