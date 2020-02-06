import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import Title from '../Title'
import Dashboard from './Dashboard'
import CreateSurvey from './CreateSurvey'
import Settings from './Settings'

import { dictionnary } from '../../Langs/langs'
import { Form, Modal, Button } from 'semantic-ui-react'

export default class Frame extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    title: PropTypes.string,
    idAdmin: PropTypes.number,
    onCreateSurvey: PropTypes.func,
    openModalSondage: PropTypes.bool,
    openModalSondageFunc: PropTypes.func,
    closeModalSondageFunc: PropTypes.func,
    onSuccess: PropTypes.func
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    title: this.props.title,
    idSondage: 0,
    nomSondage: '',
    descriptionSondage: '',
    errorNomSondage: false,
    errorDescriptionSondage: false,
    modifying: false
  }

  static getDerivedStateFromProps(props, state) {
    return {title: props.title};
  }

  onCreateSurvey = title => {
    if (this.props.onCreateSurvey) {
      this.props.onCreateSurvey(title);
      this.props.openModalSondageFunc();
    }
  }

  onModify = idSondage => {
    if (this.props.onCreateSurvey) {
      this.props.onCreateSurvey('createSurvey');
    }

    this.setState({
      idSondage: idSondage,
      modifying: true
    });
  }

  onUpdate = () => {
    this.props.closeModalSondageFunc();
  }

  render() {
    let lang = _.toUpper(this.props.lang);
    let title = _.get(dictionnary, lang + '.' + this.state.title);

    let dashboard = (
      <React.Fragment>
        <Title
          as='h1'
          content={_.upperFirst(title)}
          color='teal' />

        <Dashboard
          client={this.props.client}
          lang={this.props.lang}
          onCreateSurvey={this.onCreateSurvey}
          onModify={this.onModify} />
      </React.Fragment>
    );
    let createSurvey = (
      <React.Fragment>
        <Modal open={this.props.openModalSondage}>
          <Modal.Header>Nommez votre sondage</Modal.Header>
          <Modal.Content>
            <Form>
              <Form.Input 
                fluid 
                placeholder='Nom du sondage'
                error={this.state.errorNomSondage}
                onChange={(e, value) => {
                  this.setState({
                    nomSondage: value.value,
                    errorNomSondage: false
                  })
                }} />
              <Form.TextArea 
                placeholder='Description du sondage'
                error={this.state.errorDescriptionSondage}
                onChange={(e, value) => {
                  this.setState({
                    descriptionSondage: value.value,
                    errorDescriptionSondage: false
                  })
                }} />
            </Form>
          </Modal.Content>
          <Modal.Actions>
            <Button 
              onClick={() => {
                if (this.props.closeModalSondageFunc) {
                  this.props.closeModalSondageFunc();
                }
              }}
            >
              Annuler
            </Button>
            <Button 
              color='teal'
              onClick={() => {
                if (this.state.nomSondage === '') {
                  this.setState({
                    errorNomSondage: true
                  });
                  return;
                }

                if (this.state.descriptionSondage === '') {
                  this.setState({
                    errorDescriptionSondage: true
                  });
                  return;
                }

                this.props.client.Sondage.newSondage(
                  this.state.nomSondage,
                  this.state.descriptionSondage,
                  this.props.idAdmin,
                  result => {
                    if (this.props.onSuccess) {
                      this.props.onSuccess();
                    }

                    this.setState({
                      idSondage: result.data.id_sondage,
                      modifying: false
                    });
                  },
                  error => {
                    // console.log(error);
                    // Le sondage n'a pas pu se créer
                  }
                );
              }}
            >
              Créer un sondage
            </Button>
          </Modal.Actions>
        </Modal>

        {/* Création ou modification d'un sondage */}
        <CreateSurvey
          client={this.props.client}
          lang={this.props.lang}
          idSondage={this.state.idSondage}
          modifying={this.state.modifying}
          onUpdate={this.onUpdate} />
      </React.Fragment>
    );
    let settings = (
      <React.Fragment>
        <Title
          as='h1'
          content={_.upperFirst(title)}
          color='teal' />

        <Settings
          client={this.props.client}
          lang={this.props.lang} />
      </React.Fragment>
    );
    return (
      <React.Fragment>        
        {(this.state.title === 'dashboard')?
          dashboard
          :(this.state.title === 'createSurvey')?
            createSurvey
            :(this.state.title === 'settings')?
              settings
              :''
        }
      </React.Fragment>
    );
  }
}