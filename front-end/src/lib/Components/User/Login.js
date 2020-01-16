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

import { dictionnary } from '../../Langs/langs'

export default class Login extends React.Component {
  static propTypes = {
    lang: PropTypes.string,
    onSuccess: PropTypes.func,
    createAccount: PropTypes.func
  }

  static defaultProps = {
    lang: 'fr'
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
    this.setState({...state});
  }

  valideForm = () => {
    return (!_.isEmpty(this.state.login) && !_.isEmpty(this.state.password));
  }

  connexion = () => {
    if (!this.valideForm()) {
      let lang = _.toUpper(this.props.lang);
      let invalidForm = _.get(dictionnary, lang + '.invalidForm');
      this.setState({
        error: true,
        errorMessage: _.upperFirst(invalidForm),
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
      if (this.props.onSuccess) {
        this.props.onSuccess();
      }
    } else {
      let lang = _.toUpper(this.props.lang);
      let errorMessageLogin2 = _.get(dictionnary, lang + '.errorMessageLogin2');

      this.setState({
        error: true,
        errorMessage: _.upperFirst(errorMessageLogin2),
        openMessage: true
      });
    }
  }

  register = () => {
    if (this.props.createAccount) {
      this.props.createAccount();
    }
  }

  render() {
    let lang = _.toUpper(this.props.lang);
    let login = _.get(dictionnary, lang + '.login');
    let password = _.get(dictionnary, lang + '.password');
    let createAccount = _.get(dictionnary, lang + '.createAccount');
    let signIn1 = _.get(dictionnary, lang + '.signIn1');
    let signIn2 = _.get(dictionnary, lang + '.signIn2');
    let dividerOr = _.get(dictionnary, lang + '.dividerOr');

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
                {_.upperFirst(signIn2)}
              </Header>

              <Form>
                <Form.Input
                  error={this.state.error}
                  focus
                  icon='user'
                  iconPosition='left'
                  placeholder={_.upperFirst(login)}
                  onChange={e => this.handleChangeInput(e, 'login')} />

                <Form.Input
                  error={this.state.error}
                  focus
                  icon='lock'
                  iconPosition='left'
                  placeholder={_.upperFirst(password)}
                  type='password'
                  onChange={e => this.handleChangeInput(e, 'password')} />

                <Form.Button 
                  fluid
                  content={_.upperFirst(signIn1)}
                  color='teal'
                  size='large'
                  onClick={this.connexion} />
              </Form>
            </Grid.Column>

            <Grid.Column verticalAlign='middle'>
              <Button
                content={_.upperFirst(createAccount)} 
                icon='signup' 
                size='large'
                onClick={this.register}
              />
            </Grid.Column>
          </Grid>

          <Divider vertical>{_.upperFirst(dividerOr)}</Divider>
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
      </React.Fragment>
    );
  }
}