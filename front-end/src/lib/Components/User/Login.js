import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'
import md5 from 'js-md5'

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
import { emailValidityForm } from '../../Helpers/Helpers'

export default class Login extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    onSuccess: PropTypes.func,
    createAccount: PropTypes.func
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    email: '',
    password: '',
    error: false,
    errorMessage: '',
    openMessage: false
  }

  handleChangeInput = (e, name) => {
    let state = {
      email: this.state.email,
      password: this.state.password,
      error: false,
      errorMessage: '',
      openMessage: false
    };

    _.set(state, name, e.target.value);
    this.setState({...state});
  }

  emailValidity = () => {
    return (_.isEmpty(this.state.email) || emailValidityForm(this.state.email));
  }

  valideForm = () => {
    return (this.emailValidity() && !_.isEmpty(this.state.password));
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

    this.props.client.Admin.login(
      this.state.email,
      md5(this.state.password),
      result => {
        if (this.props.onSuccess) {
          let admin = {
            idAdmin: result.data.id_administrateur,
            pseudoAdmin: result.data.pseudo_administrateur,
            emailAdmin: result.data.email_administrateur,
            supAdmin: result.data.supAdmin
          }

          this.props.onSuccess(admin);
        }
      },
      error => {
        let lang = _.toUpper(this.props.lang);
        let errorMessageLogin2 = _.get(dictionnary, lang + '.errorMessageLogin2');

        this.setState({
          error: true,
          errorMessage: _.upperFirst(errorMessageLogin2),
          openMessage: true
        });
      }
    );
  }

  register = () => {
    if (this.props.createAccount) {
      this.props.createAccount();
    }
  }

  render() {
    let lang = _.toUpper(this.props.lang);
    let email = _.get(dictionnary, lang + '.email');
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
                  error={!this.emailValidity()}
                  focus
                  icon='user'
                  iconPosition='left'
                  placeholder={_.upperFirst(email)}
                  onChange={e => this.handleChangeInput(e, 'email')} />

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