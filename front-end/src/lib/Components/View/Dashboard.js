import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'
import { CSVLink } from 'react-csv';

import { 
  Button,
  List,
  Segment,
  Grid,
  Header,
  Dropdown,
  Confirm,
  Modal,
  Message
} from 'semantic-ui-react'

import Title from '../Title'
import { MainReponse } from '../Reponse'

import { dictionnary } from '../../Langs/langs'

const headers = [
  { label: "utilisateur", key: "utilisateur" },
  { label: "question", key: "question" },
  { label: "typeQuestion", key: "typeQuestion" },
  { label: "reponse", key: "reponse" },
  { label: "dateReponse", key: "dateReponse" }
];

export default class Dashboard extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    onCreateSurvey: PropTypes.func,
    onModify: PropTypes.func,
    onResultat: PropTypes.func,
    idAdmin: PropTypes.number,
    supAdmin: PropTypes.bool
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    listSondage: [],
    showOption: false,
    publier: false,
    supprimer: false,
    publierFinal: false,
    id_sondage: 0,
    apercu: false,
    dataCSV: [],
    csv: false,
    titreSondageCSV: ''
  }

  componentDidMount() {
    // l'admin possède tous les droits
    if (this.props.supAdmin) {
      this.props.client.Sondage.readAll(
        result => {
          this.setState({
            listSondage: result.data
          });
        },
        error => {
          this.setState({
            listSondage: []
          });
        }
      )
    } else {
      this.props.client.Sondage.readAllByAdmin(
        this.props.idAdmin,
        result => {
          this.setState({
            listSondage: result.data
          });
        },
        error => {
          this.setState({
            listSondage: []
          });
        }
      )
    }
  }

  render() {
    let lang = _.toUpper(this.props.lang);
    let titleSurveyStart = _.get(dictionnary, lang + '.titleSurveyStart');
    let createSurvey = _.get(dictionnary, lang + '.createSurvey');
    let emptySurvey = (
      <React.Fragment>
        <Segment placeholder>
          <Title
            as='h2'
            content={_.upperFirst(titleSurveyStart)}
            icon />
          <Button
            positive
            content={_.upperFirst(createSurvey)}
            onClick={() => {
              if (this.props.onCreateSurvey) {
                this.props.onCreateSurvey('createSurvey');
              }
            }} />
        </Segment>
      </React.Fragment>
    );
    let survey = (
      <React.Fragment>
        <List
          verticalAlign='middle'
          size='massive'
          relaxed
        >
          {_.map(this.state.listSondage, survey => (
            <React.Fragment key={survey.id_sondage}>
              <List.Item key={survey.id_sondage}>
                <Segment color='teal' size='massive'>
                  <Grid>
                    <Grid.Column width={15}>
                      <Header as='h3'>{_.upperFirst(survey.intituleSondage)}</Header>
                    </Grid.Column>
                    <Grid.Column width={1}>
                      <Dropdown>
                        <Dropdown.Menu>
                          <Dropdown.Item 
                            icon='edit' 
                            text='Modifier'
                            onClick={() => {
                              if (this.props.onModify) {
                                this.props.onModify(survey.id_sondage);
                              }
                            }}
                          />
                          <Dropdown.Item 
                            icon='send' 
                            text='Publier'
                            onClick={() => {
                              this.setState({
                                publier: true,
                                id_sondage: survey.id_sondage
                              });
                            }}
                          />
                          <Dropdown.Item 
                            icon='chart line' 
                            text='Résultat'
                            onClick={() => {
                              if (this.props.onResultat) {
                                this.props.onResultat(survey.id_sondage);
                              }
                            }}
                          />
                          <Dropdown.Item 
                            icon='eye' 
                            text='Aperçu'
                            onClick={() => {
                              this.setState({
                                apercu: true,
                                id_sondage: survey.id_sondage
                              })
                            }}
                          />
                          <Dropdown.Item 
                            icon='file excel outline'
                            text='Format CSV'
                            onClick={() => {
                              this.props.client.Participation.participationsCSV(
                                survey.id_sondage,
                                result => {
                                  this.props.client.Sondage.read(
                                    survey.id_sondage,
                                    success => {
                                      this.setState({
                                        titreSondageCSV: success.data.intituleSondage,
                                        dataCSV: result.data,
                                        csv: true
                                      });
                                    },
                                    error => {
                                      console.log(error);
                                    }
                                  );
                                },
                                error => {
                                  console.log(error);
                                }
                              );
                            }} />
                          <Dropdown.Item 
                            icon='trash alternate' 
                            text='Supprimer'
                            onClick={() => {
                              this.setState({
                                supprimer: true,
                                id_sondage: survey.id_sondage
                              });
                            }}
                          />
                        </Dropdown.Menu>
                      </Dropdown>
                    </Grid.Column>
                  </Grid>
                </Segment>
              </List.Item>
            </React.Fragment>
          ))}
        </List>
        <div>
          <Button
            positive
            icon='plus'
            content={_.upperFirst(createSurvey)}
            onClick={() => {
              if (this.props.onCreateSurvey) {
                this.props.onCreateSurvey('createSurvey');
              }
            }} />
        </div>
      </React.Fragment>
    );

    return (
      <React.Fragment>
        {(_.size(this.state.listSondage) === 0)?
          emptySurvey
          :survey
        }
        <Confirm
          header="Publication d'un sondage"
          content="Etes-vous sûr de vouloir publier votre sondage ?"
          open={this.state.publier}
          size='large'
          cancelButton={
            <Button
              negative
              icon='close'
              content='Non'
            />
          }
          confirmButton={
            <Button
              positive
              icon='check'
              content='Oui'
            />
          }
          onCancel={() => {
            this.setState({
              publier: false
            })
          }}
          onConfirm={() => {
            this.props.client.Sondage.publier(
              this.state.id_sondage,
              result => {
                this.setState({
                  publierFinal: true,
                  publier: false
                })
              },
              error => {
                console.log(error)
              }
            )
          }}
        />
        <Confirm
          header="Suppression d'un sondage"
          content="Etes-vous sûr de vouloir supprimer votre sondage ?"
          open={this.state.supprimer}
          size='large'
          cancelButton={
            <Button
              negative
              icon='close'
              content='Non'
            />
          }
          confirmButton={
            <Button
              positive
              icon='check'
              content='Oui'
            />
          }
          onCancel={() => {
            this.setState({
              supprimer: false
            })
          }}
          onConfirm={() => {
            this.props.client.Sondage.delete(
              this.state.id_sondage,
              result => {
                this.setState({
                  supprimer: false
                });

                this.props.client.Sondage.readAll(
                  result => {
                    this.setState({
                      listSondage: result.data
                    })
                  },
                  error => {
                    console.log(error)
                  }
                )
              },
              error => {
                console.log(error);
              }
            )
          }}
        />
        <Modal open={this.state.publierFinal}>
          <Modal.Content>
            <Message positive>
              <p>Votre sondage vient d'être publié</p>
            </Message>
          </Modal.Content>
          <Modal.Actions>
            <Button
              positive
              icon='checkmark'
              content='OK'
              onClick={() => {
                this.setState({
                  publierFinal: false
                })
              }}
            />
          </Modal.Actions>
        </Modal>
        <Modal open={this.state.apercu} size="large">
          <Modal.Header>Aperçu du sondage</Modal.Header>
          <Modal.Content>
            <MainReponse
              client={this.props.client}
              idSondage={this.state.id_sondage}
            />
          </Modal.Content>
          <Modal.Actions>
            <Button
              positive
              icon='checkmark'
              content='OK'
              onClick={() => {
                this.setState({
                  apercu: false
                })
              }}
            />
          </Modal.Actions>
        </Modal>
        <Modal open={this.state.csv}>
          <Modal.Header>Format CSV</Modal.Header>
          <Modal.Content>Voulez-vous télécharger les réponses du sondage au format CSV ?</Modal.Content>
          <Modal.Actions>
            <Button
              negative
              content='Non'
              icon='close'
              onClick={() => {
                this.setState({
                  dataCSV: [],
                  csv: false
                });
              }} />
            <CSVLink
              data={this.state.dataCSV} 
              headers={headers}
              onClick={() => {
                this.setState({
                  csv: false,
                  dataCSV: []
                });
              }}
              filename={_.toLower(_.replace(this.state.titreSondageCSV, ' ', '_') + '.csv')}
            >
              <Button
                positive
                content='Oui'
                icon='check'
              />
            </CSVLink>
          </Modal.Actions>
        </Modal>
      </React.Fragment>
    );
  }
}