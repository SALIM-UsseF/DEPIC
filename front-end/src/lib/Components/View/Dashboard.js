import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

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

import { dictionnary } from '../../Langs/langs'

export default class Dashboard extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    onCreateSurvey: PropTypes.func,
    onModify: PropTypes.func
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
    id_sondage: 0
  }

  componentDidMount() {
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
      </React.Fragment>
    );
  }
}