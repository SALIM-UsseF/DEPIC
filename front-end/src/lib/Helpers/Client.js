import axios from "axios";

class Client {
  constructor(url, token) {
    this.url = url;

    this.header = {
      headers:{
        'Authorization' : token,
        'Content-Type': 'application/json'
      }
    }
  };

  // create : {pseudo_administrateur, email_administrateur, motDePasse_administrateur}
  // update : {id_administrateur, pseudo_administrateur, email_administrateur, supAdmin}
  Admin = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'admins', this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'admin/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    login: (email, password, success, errors) => {
      axios
        .post(this.url + 'login/admin', { 
            email_administrateur: email, 
            motDePasse_administrateur: password 
          },
          this.header
        )
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    checkEmail: (email, success, errors) => {
      axios
        .post(this.url + 'checkEmail/admin', { 
            email_administrateur: email 
          },
          this.header
        )
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    checkPassword: (id, password, success, errors) => {
      axios
        .post(this.url + 'checkPassword/admin/' + id, { 
            motDePasse_administrateur: password 
          },
          this.header
        )
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'new/admin', params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'update/admin/' + id, params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'delete/admin/' + id, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  // create : {intitule}
  // update : {intitule}
  Categorie = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'categories', this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'categorie/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'new/categorie', params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'update/categorie/' + id, params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'delete/categorie/' + id, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  // create : {intituleSondage, descriptionSondage, administrateur_id, categorie_id}
  // update : {intituleSondage, descriptionSondage, categorie_id}
  Sondage = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'sondages', this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'sondage/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    readAllByAdmin: (idAdmin, success, errors) => {
      axios
        .get(this.url + 'sondages/admin/' + idAdmin, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    readByCategorie: (id, success, errors) => {
      axios
        .get(this.url + 'sondages/categorie/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    readQuestionBySondage: (id, success, errors) => {
      axios
        .get(this.url + 'questions/sondage/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'new/sondage', params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'update/sondage/' + id, params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'delete/sondage/' + id, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    publier: (id, success, errors) => {
      axios
        .put(this.url + 'publier/sondage/' + id, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    activerResultats: (id, success, errors) => {
      axios
        .put(this.url + 'activer/resultats/' + id, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  // create + update: {email, adresseIp}
  User = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'utilisateurs', this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'utilisateur/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'new/utilisateur', params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'update/utilisateur/' + id, params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'delete/utilisateur/' + id, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  Participation = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'participations', this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    readBySondage: (id, success, errors) => {
      axios
        .get(this.url + 'participations/Sondage/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    readByUserAndSondage: (idUser, idSondage, success, errors) => {
      axios
        .get(this.url + 'participations/user/sondage/' + idUser + '/' + idSondage, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    readByQuestionAndSondage: (idQuestion, idSondage, success, errors) => {
      axios
        .get(this.url + 'participations/question/sondage/' + idQuestion + '/' + idSondage, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    participationsCSV: (idSondage, success, errors) => {
      axios
        .get(this.url + 'participationsCSV/' + idSondage, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    delete: (idUser, idSondage, idQuestion, success, errors) => {
      axios
        .put(this.url + 'delete/participation/' + idUser + '/' + idSondage + '/' + idQuestion, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  Question = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'questions', this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'question/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    readBySondage: (id, success, errors) => {
      axios
        .get(this.url + 'questions/sondage/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  // create : {intitule, estObligatoire, nombreDeCaractere, ordre, sondage_id}
  // update : {intitule, estObligatoire, nombreDeCaractere, ordre, type}
  QuestionOuverte = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'questionsOuvertes', this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'questionOuverte/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'new/questionOuverte', params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'update/questionOuverte/' + id, params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'delete/questionOuverte/' + id, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };
  
  // create : {intitule, estObligatoire, estUnique, nombreChoix, ordre, sondage_id}
  // update : {intitule, estObligatoire, nombreChoix, estUnique, ordre, type}
  QuestionChoix = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'questionsChoix', this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'questionChoix/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'new/questionChoix', params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'update/questionChoix/' + id, params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'delete/questionChoix/' + id, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  // create : {intitule, estObligatoire, minPoints, maxPoints, ordre, sondage_id}
  // update : {intitule, estObligatoire, minPoints, maxPoints, ordre, type}
  QuestionPoints = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'questionsPoints', this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'questionPoints/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'new/questionPoints', params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'update/questionPoints/' + id, params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'delete/questionPoints/' + id, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  // create : {intitule, estObligatoire, numerosDeQuestionsGroupe, ordre, sondage_id}
  // update : {intitule, estObligatoire, numerosDeQuestionsGroupe, ordre, type}
  GroupQuestions = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'groupesQuestions', this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'groupeQuestions/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'new/groupeQuestions', params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'update/groupeQuestions/' + id, params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'delete/groupeQuestions/' + id, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  // create : {id_groupe, id_question}
  Groupe = {
    read: (idQuestion, success, errors) => {
      axios
        .get(this.url + 'questions/groupe/' + idQuestion, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'addQuestion/groupe', params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'delete/groupe/' + id, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  }

  Resultat = {
    read: (idSondage, success, errors) => {
      axios
        .get(this.url + 'resultats/' + idSondage, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  // create : {intituleChoix, question_id}
  // update : {id_choix, intituleChoix, question_id}
  Choix = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'choix', this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'choix/' + id, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    readByQuestion: (idQuestion, success, errors) => {
      axios
        .get(this.url + 'choix/question/' + idQuestion, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'new/choix', params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'update/choix/' + id, params, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'delete/choix/' + id, {}, this.header)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  // élément à fournir : email et password
  AuthApi = {
    authenticate: (params, success, errors) => {
      axios
        .post(this.url + 'authenticate/', params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };
}

export default Client;
