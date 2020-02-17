import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import Title from '../Title'
import Dashboard from './Dashboard'
import CreateSurvey from './CreateSurvey'
import Resultats from './Resultats'

import { dictionnary } from '../../Langs/langs'
import { Form, Modal, Button } from 'semantic-ui-react'

export default class Frame extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    title: PropTypes.string,
    idAdmin: PropTypes.number,
    supAdmin: PropTypes.bool,
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
    categorie_id: 0,
    categories: [],
    nomCategorie: '',
    errorNomSondage: false,
    errorDescriptionSondage: false,
    errorCategorie: false,
    modifying: false,
    openModalCategorie: false,
    nouvelleCategorie: '',
    errorNouvelleCategorie: false
  }

  componentDidMount() {
    this.props.client.Categorie.readAll(
      result => {
        this.setState({
          categories: result.data
        });
      },
      error => {
        this.setState({
          categories: []
        });
      }
    );
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

  categorieOption = () => {
    let categorieOption = [];

    _.map(this.state.categories, categorie => {
      let option = {
        key: categorie.id_categorie,
        text: categorie.intitule,
        value: categorie.intitule,
        id: categorie.id_categorie,
        onClick: (e, result) => this.onClickCategorie(result)
      }

      categorieOption.push(option);
    });

    return categorieOption;
  }

  onClickCategorie = (result) => {
    this.setState({
      nomCategorie: result.value,
      categorie_id: result.id,
      errorCategorie: false
    });
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
          onModify={this.onModify}
          idAdmin={this.props.idAdmin}
          supAdmin={this.props.supAdmin} />
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
              <Form.Group widths='equal'>
                <Form.Dropdown
                  error={this.state.errorCategorie}
                  fluid
                  placeholder='Catégorie'
                  selection
                  options={this.categorieOption()}
                  value={this.state.nomCategorie}
                />
                <Form.Button
                  circular
                  icon='plus'
                  onClick={() => {
                    this.setState({
                      openModalCategorie: true
                    });
                  }} />
              </Form.Group>
            </Form>
          </Modal.Content>
          <Modal.Actions>
            <Button 
              onClick={() => {
                if (this.props.closeModalSondageFunc) {
                  this.setState({
                    nomSondage: '',
                    descriptionSondage: '',
                    categorie_id: 0,
                    nomCategorie: '',
                    errorNomSondage: false,
                    errorDescriptionSondage: false,
                    errorCategorie: false
                  });
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

                if (this.state.nomCategorie === '') {
                  this.setState({
                    errorCategorie: true
                  });
                  return;
                }

                let params = {
                  intituleSondage: this.state.nomSondage,
                  descriptionSondage: this.state.descriptionSondage,
                  administrateur_id: this.props.idAdmin,
                  categorie_id: this.state.categorie_id
                };

                this.props.client.Sondage.create(
                  params,
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

        <Modal open={this.state.openModalCategorie}>
          <Modal.Header>Ajouter une nouvelle categorie</Modal.Header>
          <Modal.Content>
            <Form>
              <Form.Input
                fluid 
                placeholder='Nom de la nouvelle catégorie'
                error={this.state.errorNouvelleCategorie}
                onChange={(e, value) => {
                  this.setState({
                    nouvelleCategorie: value.value,
                    errorNouvelleCategorie: false
                  })
                }} />
            </Form>
          </Modal.Content>
          <Modal.Actions>
            <Button
              negative
              onClick={() => {
                this.setState({
                  nouvelleCategorie: '',
                  openModalCategorie: false
                })
              }}
            >
              Annuler
            </Button>
            <Button
              positive
              onClick={() => {
                this.props.client.Categorie.create(
                  {
                    intitule: this.state.nouvelleCategorie
                  },
                  result => {
                    this.props.client.Categorie.readAll(
                      result => {
                        this.setState({
                          categories: result.data,
                          nouvelleCategorie: '',
                          openModalCategorie: false
                        });
                      },
                      error => {
                        this.setState({
                          categories: []
                        });
                      }
                    );
                  },
                  error => {
                    console.log(error);
                  }
                )
              }}
            >
              Ajouter
            </Button>
          </Modal.Actions>
        </Modal>
      </React.Fragment>
    );
    let resultats = (
      <React.Fragment>
        <Title
          as='h1'
          content={_.upperFirst(title)}
          color='teal' />

        <Resultats
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
            :(this.state.title === 'resultats')?
              resultats
              :''
        }
      </React.Fragment>
    );
  }
}