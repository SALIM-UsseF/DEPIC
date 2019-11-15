import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import { 
  Button,
  Divider,
  Form, 
  Grid, 
  Header,
  Message,
  Segment 
} from 'semantic-ui-react'

export default class Login extends React.Component {
  static propTypes = {
    onSuccess: PropTypes.func
  }

  state = {
    login: '',
    password: '',
    error: false,
    errorMessage: '',
    openMessage: false
  }

  handleChangeInput = (e, name) => {
    let state = {
      login: this.state.login,
      password: this.state.password,
      error: false,
      errorMessage: '',
      openMessage: false
    };

    _.set(state, name, e.target.value);
    this.setState({...state})
  }

  valideForm = () => {
    return (!_.isEmpty(this.state.login) && !_.isEmpty(this.state.password));
  }

  connexion = () => {
    if (!this.valideForm()) {
      this.setState({
        error: true,
        errorMessage: 'Renseignez l\'identifiant et le mot de passe',
        openMessage: true
      });

      return;
    }

    /**
     * Lorsque l'utilisateur renseigne ses identifiants (non vides), on va envoyer le 'login' et le 'password' au serveur pour les vérifications
     * Si les identifiants sont enregistrés dans la BDD, alors on se connecte (affichage de la page d'accueil des questionnaires)
     * Sinon on affiche un message d'erreur
     * Exemple avec les 'login=user' et 'password=user'
     */

    if (this.state.login === 'user' && this.state.password === 'user') {
      let successMessage = 'CONNEXION REUSSIE\nIdentifiant : ' + this.state.login + '\nMot de passe : ' + this.state.password;

      if (this.props.onSuccess) {
        this.props.onSuccess(successMessage);
      }
    } else {
      this.setState({
        error: true,
        errorMessage: 'Identifiant ou mot de passe incorrect',
        openMessage: true
      });
    }
  }

  render() {
    return (
      <React.Fragment>
        <Segment placeholder>
          <Grid 
            columns={2} 
            relaxed='very' 
            stackable
          >
            <Grid.Column>
              <Header 
                as='h2' 
                color='teal' 
                textAlign='center'
              >
                Connectez-vous à votre compte
              </Header>

              <Form>
                <Form.Input
                  focus
                  icon='user'
                  iconPosition='left'
                  placeholder="Identifiant"
                  onChange={e => this.handleChangeInput(e, 'login')} />

                <Form.Input
                  focus
                  icon='lock'
                  iconPosition='left'
                  placeholder='Mot de passe'
                  type='password'
                  onChange={e => this.handleChangeInput(e, 'password')} />

                <Button 
                  fluid
                  content='Connexion'
                  color='teal'
                  size='large'
                  onClick={this.connexion} />
              </Form>
            </Grid.Column>

            <Grid.Column verticalAlign='middle'>
              <Button
                content='Créer un compte' 
                icon='signup' 
                size='large' />
            </Grid.Column>
          </Grid>

          <Divider vertical>Ou</Divider>
        </Segment>
        {this.state.openMessage?
          (
            <Message
              error={this.state.error}
              content={this.state.errorMessage}
            />
          ):''
        }
      </React.Fragment>
    );
  }
}