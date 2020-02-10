import axios from "axios";

class Client {
  constructor(url) {
    this.url = url;
  };

  // create : {pseudo_administrateur, email_administrateur, motDePasse_administrateur}
  // update : {id_administrateur, pseudo_administrateur, email_administrateur, supAdmin}
  Admin = {
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
    },
    login: (email, password, success, errors) => {
      axios
        .post(this.url + 'loginAdmin', { 
          email_administrateur: email, 
          motDePasse_administrateur: password 
        })
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    checkEmail: (email, success, errors) => {
      axios
        .post(this.url + 'checkAdminEmail', { 
          email_administrateur: email 
        })
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    checkPassword: (id, password, success, errors) => {
      axios
        .post(this.url + 'checkAdminPassword/' + id, { 
          motDePasse_administrateur: password 
        })
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'newAdmin', params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateAdmin/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteAdmin/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  // create : {intitule}
  // update : {à voir}
  Categorie = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'categories')
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'categorie/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'newCategorie', params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateCategorie/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteCategorie/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  // create : {intituleSondage, descriptionSondage, administrateur_id}
  // update : {intituleSondage, descriptionSondage}
  Sondage = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'sondages')
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'sondage/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    readByCategorie: (id, success, errors) => {
      axios
        .get(this.url + 'sondages/categorie/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    readQuestionBySondage: (id, success, errors) => {
      axios
        .get(this.url + 'questions/Sondage/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'newSondage', params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateSondage/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteSondage/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    publier: (id, success, errors) => {
      axios
        .put(this.url + 'publierSondage/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    activerResultats: (id, success, errors) => {
      axios
        .put(this.url + 'activerResultats/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  // create + update: {email, adresseIp}
  User = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'utilisateurs')
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'utilisateur/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'newUtilisateur', params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateUtilisateur/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteUtilisateur/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  Participation = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'participations')
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    readBySondage: (id, success, errors) => {
      axios
        .get(this.url + 'participationsBySondage/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    readByUserAndSondage: (idUser, idSondage, success, errors) => {
      axios
        .get(this.url + 'participationsByUserAndSondage/' + idUser + '/' + idSondage)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    readByQuestionAndSondage: (idQuestion, idSondage, success, errors) => {
      axios
        .get(this.url + 'participationsByQuestionAndSondage/' + idQuestion + '/' + idSondage)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    delete: (idUser, idSondage, idQuestion, success, errors) => {
      axios
        .put(this.url + 'deleteParticipation/' + idUser + '/' + idSondage + '/' + idQuestion)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  Question = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'questions')
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'question/' + id)
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

  // create : {intitule, estObligatoire, nombreDeCaractere, ordre, sondage_id}
  // update : {intitule, estObligatoire, nombreDeCaractere, ordre, type}
  QuestionOuverte = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'questionsOuvertes')
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'questionOuverte/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'newQuestionOuverte', params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateQuestionOuverte/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteQuestionOuverte/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };
  
  // create : {intitule, estObligatoire, estUnique, nombreChoix, ordre, sondage_id}
  // update : {intitule, estObligatoire, nombreChoix, estUnique, ordre, type}
  QuestionChoix = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'questionsChoix')
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'questionChoix/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'newQuestionChoix', params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateQuestionChoix/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteQuestionChoix/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  // create : {intitule, estObligatoire, minPoints, maxPoints, ordre, sondage_id}
  // update : {intitule, estObligatoire, minPoints, maxPoints, ordre, type}
  QuestionPoints = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'questionsPoints')
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'questionPoints/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'newQuestionPoints', params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateQuestionPoints/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteQuestionPoints/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  // create : {intitule, estObligatoire, numerosDeQuestionsGroupe, ordre, sondage_id}
  // update : {intitule, estObligatoire, numerosDeQuestionsGroupe, ordre, type}
  GroupQuestions = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'groupesQuestions')
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'groupeQuestions/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'newGroupeQuestions', params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateGroupeQuestions/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteGroupeQuestions/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  };

  // create : {id_groupe, id_question}
  Groupe = {
    read: (idQuestion, success, errors) => {
      axios
        .get(this.url + 'questions/groupe/' + idQuestion)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'groupe/ajoutQuestion', params)
        .then(result => success(result))
        .catch(error => errors(error.response))
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteGroupe/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response))
    }
  }

  Resultat = {
    read: (idSondage, success, errors) => {
      axios
        .get(this.url + 'resultats/' + idSondage)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };

  // create : {intituleChoix, question_id}
  // update : {id_choix, intituleChoix, question_id}
  Choix = {
    readAll: (success, errors) => {
      axios
        .get(this.url + 'choix')
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    read: (id, success, errors) => {
      axios
        .get(this.url + 'choix/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    readByQuestion: (idQuestion, success, errors) => {
      axios
        .get(this.url + 'choixByQuestion/' + idQuestion)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    create: (params, success, errors) => {
      axios
        .post(this.url + 'newChoix', params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    update: (id, params, success, errors) => {
      axios
        .put(this.url + 'updateChoix/' + id, params)
        .then(result => success(result))
        .catch(error => errors(error.response));
    },
    delete: (id, success, errors) => {
      axios
        .put(this.url + 'deleteChoix/' + id)
        .then(result => success(result))
        .catch(error => errors(error.response));
    }
  };
}

export default Client;
