import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import {
  Button,
  Form, 
  Header,
  Message,
  Modal,
  Segment
} from 'semantic-ui-react'

export default class Register extends React.Component {
  static propTypes = {
    onSuccess: PropTypes.func,
    onCancel: PropTypes.func
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
    openMessage: false,
    modalType: null // success en cas de succès et error dans le cas contraire
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
        errorMessage: 'Certains champs ne sont pas valides',
        openMessage: true
      });

      return;
    }

    /**
     * Lorsque l'utilisateur renseigne ses coordonnées, on envoyera au serveur pour les enregistrer
     * Si les coordonnées sont enregistrés dans la BDD, alors on revient à la page de connexion pour se connecter
     * Sinon on affiche un message d'erreur
     */

    // Je fais un test si l'identifiant n'existe pas déjà
    if (this.state.login === 'user') {
      this.setState({
        modalType: 2
      })
    } else {
      this.setState({
        modalType: 1
      })
    }
  }

  onCancel = () => {
    if (this.props.onCancel) {
      this.props.onCancel();
    }
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
              error={!this.emailValidity()}
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
              error={
                !_.isEmpty(this.state.confirmPassword) &&
                this.state.confirmPassword !== this.state.password
              }
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
              size='large'
              onClick={this.onCancel} />
          </Form>
        </Segment>

        {/* Message d'erreur */}
        {this.state.openMessage?
          (
            <Message
              error={this.state.error}
              content={this.state.errorMessage}
            />
          ):''
        }

        {/* Modal, retour inscription (success ou error) */}
        <Modal 
          size='small' 
          open={!_.isNull(this.state.modalType)}
          dimmer='blurring'
          closeOnDimmerClick={false}
        >
          <Modal.Header
            content='Inscription'
            style={{ 
              color:this.state.modalType === 1?'teal':'red'
            }}
          />
          <Modal.Content>
            {this.state.modalType === 1?
              <p>Bienvenue. La création de votre compte a été effectuée avec succès et vous pouvez y accéder en utilisant l'dentifiant et le mot de passe créés.</p>
              : this.state.modalType === 2?
                <p>Erreur lors de la création de votre compte. L'identifiant a déjà été utilisé par un autre utilisateur.</p>
                : null
            }
          </Modal.Content>
          <Modal.Actions>
            <Button
              positive={this.state.modalType === 1?true:false}
              negative={this.state.modalType === 2?true:false}
              content='OK'
              onClick={() => {
                if (this.state.modalType === 1) {
                  if (this.props.onSuccess) {
                    this.props.onSuccess();
                  }
                } else {
                  this.setState({
                    modalType: null
                  });
                }
              }}
            />
          </Modal.Actions>
        </Modal>
      </React.Fragment>
    );
  }
}