import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'
import md5 from 'js-md5'

import {
  Button,
  Form, 
  Header,
  Message,
  Modal,
  Segment
} from 'semantic-ui-react'

import { dictionnary } from '../../Langs/langs'
import { emailValidityForm } from '../../Helpers/Helpers'

export default class Register extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    onSuccess: PropTypes.func,
    onCancel: PropTypes.func
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    login: '',
    password: '',
    confirmPassword: '',
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
      email: this.state.email,
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

  checkValidity = () => {
    return (
      !_.isEmpty(this.state.login) &&
      !_.isEmpty(this.state.password) &&
      this.emailValidity() &&
      (this.state.password === this.state.confirmPassword)
    );
  }

  register = () => {
    if (!this.checkValidity()) {
      let lang = _.toUpper(this.props.lang);
      let invalidForm = _.get(dictionnary, lang + '.invalidForm');
      this.setState({
        error: true,
        errorMessage: _.upperFirst(invalidForm),
        openMessage: true
      });

      return;
    }

    this.props.client.Admin.create(
      this.state.login,
      this.state.email,
      md5(this.state.password),
      result => {
        this.setState({
          modalType: 1
        })
      },
      error => {
        this.setState({
          modalType: 2
        })
      }
    )
  }

  onCancel = () => {
    if (this.props.onCancel) {
      this.props.onCancel();
    }
  }

  render() {
    let lang = _.toUpper(this.props.lang);
    let email = _.get(dictionnary, lang + '.email');
    let login = _.get(dictionnary, lang + '.login');
    let password = _.get(dictionnary, lang + '.password');
    let confirmPassword = _.get(dictionnary, lang + '.confirmPassword');
    let createAccount = _.get(dictionnary, lang + '.createAccount');
    let validate = _.get(dictionnary, lang + '.validate');
    let cancel = _.get(dictionnary, lang + '.cancel');
    let successRegister = _.get(dictionnary, lang + '.successRegister');
    let errorRegister = _.get(dictionnary, lang + '.errorRegister');

    return (
      <React.Fragment>
        <Segment secondary>
          <Header
            as='h1'
            color='teal'
            textAlign='center'
          >
            {_.upperFirst(createAccount)}
          </Header>
          <Form size='big'>
            <Form.Input
              error={!this.emailValidity()}
              focus
              placeholder={_.upperFirst(email)}
              onChange={e => this.handleChangeInput(e, 'email')} />

            <Form.Input
              focus
              placeholder={_.upperFirst(login)}
              onChange={e => this.handleChangeInput(e, 'login')} />

            <Form.Input
              focus
              placeholder={_.upperFirst(password)}
              type='password'
              onChange={e => this.handleChangeInput(e, 'password')} />
          
            <Form.Input
              error={
                !_.isEmpty(this.state.confirmPassword) &&
                this.state.confirmPassword !== this.state.password
              }
              focus
              placeholder={_.upperFirst(confirmPassword)}
              type='password'
              onChange={e => this.handleChangeInput(e, 'confirmPassword')} />

            <Form.Button
              fluid
              content={_.upperFirst(validate)}
              color='teal'
              size='large'
              onClick={this.register} />

            <Form.Button
              fluid
              content={_.upperFirst(cancel)}
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
              <p>{_.upperFirst(successRegister)}</p>
              : this.state.modalType === 2?
                <p>{_.upperFirst(errorRegister)}</p>
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