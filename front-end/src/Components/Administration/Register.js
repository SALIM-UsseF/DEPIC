import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import {
  Form, 
  Header,
  Segment 
} from 'semantic-ui-react'

export default class Register extends React.Component {
  static propTypes = {
    onSuccess: PropTypes.func
  }

  state = {
    login: '',
    password: '',
    confirmPassword: '',
    firstname: '',
    lastname: '',
    email: '',
    error: false,
    errorMessage: '',
    openMessage: false
  }

  handleChangeInput = (e, name) => {
    let state = {
      login: this.state.login,
      password: this.state.password,
      confirmPassword: this.state.confirmPassword,
      firstname: this.state.firstname,
      lastname: this.state.lastname,
      email: this.state.email,
      error: false,
      errorMessage: '',
      openMessage: false
    };

    _.set(state, name, e.target.value);
    this.setState({...state});
  }

  emailValidity = () => {
    let regex = /\S+@\S+\.\S+/;
    return (_.isEmpty(this.state.email) || regex.test(this.state.email));
  }

  checkValidity = () => {
    return (
      !_.isEmpty(this.state.login) &&
      !_.isEmpty(this.state.password) &&
      !_.isEmpty(this.state.firstname) &&
      !_.isEmpty(this.state.lastname) &&
      this.emailValidity() &&
      (this.state.password === this.state.confirmPassword)
    );
  }

  register = () => {
    if (!this.checkValidity()) {
      this.setState({
        error: true,
        errorMessage: '',
        openMessage: true
      });

      return;
    }

    /**
     * Lorsque l'utilisateur renseigne ses coordonnées, on envoyera au serveur pour les enregistrer
     * Si les coordonnées sont enregistrés dans la BDD, alors on revient à la page de connexion pour se connecter
     * Sinon on affiche un message d'erreur
     */
  }

  render() {
    return (
      <React.Fragment>
        <Segment secondary>
          <Header
            as='h1'
            color='teal'
            textAlign='center'
          >
            Créer un compte
          </Header>
          <Form size='big'>
            <Form.Input
              focus
              placeholder='Nom'
              onChange={e => this.handleChangeInput(e, 'lastname')} />

            <Form.Input
              focus
              placeholder='Prénom'
              onChange={e => this.handleChangeInput(e, 'firstname')} />

            <Form.Input
              error={!this.emailValidity}
              focus
              placeholder='E-mail'
              onChange={e => this.handleChangeInput(e, 'email')} />

            <Form.Input
              focus
              placeholder='Identifiant'
              onChange={e => this.handleChangeInput(e, 'login')} />

            <Form.Input
              focus
              placeholder='Mot de passe'
              type='password'
              onChange={e => this.handleChangeInput(e, 'password')} />
          
            <Form.Input
              focus
              placeholder='Confirmation de mot de passe'
              type='password'
              onChange={e => this.handleChangeInput(e, 'confirmPassword')} />

            <Form.Button
              fluid
              content='Valider'
              color='teal'
              size='large'
              onClick={this.register} />

            <Form.Button
              fluid
              content='Annuler'
              size='large' />
          </Form>
        </Segment>
      </React.Fragment>
    );
  }
}