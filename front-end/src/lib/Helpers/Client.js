import axios from "axios";

class Client {
  constructor(url) {
    this.url = url;
  };

  Administrateur = {
    loginAdmin: (email, password, success, errors) => {
      axios
        .post(this.url + 'loginAdmin', { email: email, password: password })
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    checkAdminEmail: (email, success, errors) => {
      axios
        .post(this.url + 'checkAdminEmail', { email: email })
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    checkAdminPassword: (id, password, success, errors) => {
      axios
        .post(this.url + 'checkAdminPassword/' + id, { password: password })
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    newAdmin: (pseudo, email, password, success, errors) => {
      axios
        .post(this.url + 'newAdmin', {
          pseudo_administrateur: pseudo,
          email_administrateur: email,
          motDePasse_administrateur: password
        })
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    readAll: (success, errors) => {
      axios
        .get(this.url + 'admins')
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'admin/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  Sondage = {
    newSondage: (intituleSondage, descriptionSondage, administrateur_id, success, errors) => {
      axios
        .post(this.url + 'newSondage', {
          intituleSondage: intituleSondage,
          descriptionSondage: descriptionSondage,
          administrateur_id: administrateur_id
        })
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    publierSondage: (id, success, errors) => {
      axios
        .put(this.url + 'publierSondage/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    deleteSondage: (id, success, errors) => {
      axios
        .put(this.url + 'deleteSondage/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    sondages: (params, success, errors) => {
      axios
        .get(this.url + 'sondages', params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    sondage: (id, success, errors) => {
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

  // update : {:intitule, :estObligatoire, :nombreDeCaractere, :ordre, :type}
  QuestionOuverte = {
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteQuestionOuverte/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateQuestionOuverte/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };
  
  // update : {:intitule, :estObligatoire, :nombreChoix, :estUnique, :ordre, :type}
  QuestionChoix = {
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteQuestionChoix/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateQuestionChoix/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  // update : {:intitule, :estObligatoire, :minPoints, :maxPoints, :ordre, :type}
  QuestionPoints = {
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteQuestionPoints/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateQuestionPoints/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  // update : {:intitule, :estObligatoire, :numerosDeQuestionsGroupe, :ordre, :type}
  GroupQuestions = {
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteGroupeQuestions/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateGroupeQuestions/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  Resultat = {

  };

  // update : {:id_choix, :intituleChoix, :question_id}
  Choix = {
    read: (id, success, errors) => {
      axios
        .get(this.url + 'choixByQuestion/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteChoix/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateChoix/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    newChoix: (intituleChoix, question_id, success, errors) => {
      axios
        .post(this.url + 'newChoix', {
          intituleChoix: intituleChoix,
          question_id: question_id
        })
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };
}

export default Client;
