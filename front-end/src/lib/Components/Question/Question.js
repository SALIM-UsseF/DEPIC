import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import {
  Divider,
  Form, 
  Segment,
  Button,
  Confirm
} from 'semantic-ui-react'

import ListTypeQuestion from './ListTypeQuestion'
import QuestionOuverte from './QuestionOuverte'
import QuestionChoixUnique from './QuestionChoixUnique'
import QuestionChoixMultiple from './QuestionChoixMultiple'
import QuestionPoints from './QuestionPoints'

const options = [
  { key: 'Facultative', text: 'Facultative', value: 'Facultative' },
  { key: 'Obligatoire', text: 'Obligatoire', value: 'Obligatoire' }
]

/**
 * Saisie de la question
 */

export default class Question extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    idQuestion: PropTypes.number,
    numberOfQuestion: PropTypes.number,
    onChangeQuestion: PropTypes.func,
    onChangeTypeQuestion: PropTypes.func,
    onChangeCharacter: PropTypes.func,
    color: PropTypes.string,
    isModifying: PropTypes.bool,
    typeQuestion: PropTypes.string,
    intituleQuestion: PropTypes.string,
    isOptional: PropTypes.bool,
    ordre: PropTypes.number,
    nbChoix: PropTypes.number,
    isUnique: PropTypes.bool,
    nbCharactere: PropTypes.number,
    minPoints: PropTypes.number,
    maxPoints: PropTypes.number,
    removeQuestion: PropTypes.func
  }

  state = {
    typeQuestion: '',
    isOptional: this.props.isOptional?'Obligatoire':'Facultative',
    choix: [],
    remove: false,
    save: true,
    intitule: this.props.isModifying?this.props.intituleQuestion:'',
    nbCharactere: this.props.isModifying?this.props.nbCharactere:0,
    maxPoints: this.props.isModifying?this.props.maxPoints:0
  }

  componentDidMount() {
    this.props.client.Choix.readByQuestion(
      this.props.idQuestion,
      result => {
        this.setState({
          choix: result.data
        })
      },
      error => {
        console.log(error);
      }
    )
  }

  onChangeTypeQuestion = value => {
    this.setState({ typeQuestion: value });
    
    if (this.props.onChangeTypeQuestion) {
      this.props.onChangeTypeQuestion(value);
    }
  }

  onChangeNbCharactere = value => {
    this.setState({
      nbCharactere: (value==='')?0:parseInt(value)
    });
  }

  onChangeMaxPoints = value => {
    this.setState({
      maxPoints: (value==='')?0:parseInt(value)
    })
  }

  update = () => {
    this.props.client.Choix.readByQuestion(
      this.props.idQuestion,
      result => {
        this.setState({
          choix: result.data
        })
      },
      error => {
        console.log(error);
      }
    )
  }

  render() {
    let choixunique = (
      <React.Fragment>
        {_.map(this.state.choix, uniqueChoix => (
          <QuestionChoixUnique
            client={this.props.client}
            disabled={this.state.save}
            key={uniqueChoix.id_choix}
            isModifying={this.props.isModifying}
            choix={uniqueChoix.intituleChoix}
            id_choix={uniqueChoix.id_choix}
            update={this.update} />
        ))}
        <Form.Button
          primary 
          disabled={this.state.save}
          circular 
          icon='plus'
          onClick={() => {
            let params = {
              intituleChoix: 'Nouveau choix',
              question_id: this.props.idQuestion
            };

            this.props.client.Choix.create(
              params,
              result => {
                this.update();
              },
              error => {
                console.log(error);
              }
            )
          }}
        />
      </React.Fragment>
    );
    let choixMultiple = (
      <React.Fragment>
        {_.map(this.state.choix, uniqueChoix => (
          <QuestionChoixMultiple
            client={this.props.client}
            idQuestion={this.props.idQuestion}
            disabled={this.state.save}
            key={uniqueChoix.id_choix}
            isModifying={this.props.isModifying}
            choix={uniqueChoix.intituleChoix}
            id_choix={uniqueChoix.id_choix}
            update={this.update} />
        ))}
        <Form.Button
          primary 
          disabled={this.state.save}
          circular 
          icon='plus'
          onClick={() => {
            let params = {
              intituleChoix: 'Nouveau choix',
              question_id: this.props.idQuestion
            };

            this.props.client.Choix.create(
              params,
              result => {
                this.update();
              },
              error => {
                console.log(error);
              }
            )
          }}
        />
      </React.Fragment>
    );
    return (
      <React.Fragment>
        <Segment 
          secondary={!this.state.save}
          color={this.props.color}
          clearing
        >
          <Form>
            <p
              style={{ 
                fontFamily: 'serif',
                fontWeight: 'bold'
              }}
            >Question n°{this.props.numberOfQuestion}</p>
            <Form.Group>
              <Form.Input
                disabled={this.state.save}
                value={this.state.intitule}
                placeholder='Saisissez votre question'
                onChange={(e, data) => {
                  this.setState({
                    intitule: data.value
                  })
                  if (this.props.onChangeQuestion) {
                    this.props.onChangeQuestion(data.value);
                  }
                }}
                width={13}
              />
              {!this.props.isModifying?
                <ListTypeQuestion
                  disabled={this.state.save}
                  client={this.props.client}
                  fluid={false}
                  onChangeTypeQuestion={this.onChangeTypeQuestion}
                  isModifying={this.props.isModifying}
                  typeOfQuestion={this.props.typeQuestion}
                  isUnique={this.props.isUnique}
                />
                :''
              }
              <Form.Select
                disabled={this.state.save}
                options={options}
                value={this.props.isModifying?this.state.isOptional:''}
                placeholder='Facultative'
                onChange={(e, data) => {
                  this.setState({
                    isOptional: data.value
                  });

                  if (this.props.onChangeCharacter) {
                    this.props.onChangeCharacter(data.value);
                  }
                }}
                width={2}
              />
            </Form.Group>
            <Divider />
            {this.props.isModifying?
              (this.props.typeQuestion === 'QuestionChoix')?
                (this.props.isUnique)?
                  choixunique
                :choixMultiple
              :(this.props.typeQuestion === 'QuestionOuverte')?
                <QuestionOuverte
                  disabled={this.state.save}
                  nbCharactere={this.state.nbCharactere}
                  isModifying={this.props.isModifying}
                  onChangeNbCharactere={this.onChangeNbCharactere} />
              :(this.props.typeQuestion === 'QuestionPoint')?
                <QuestionPoints
                  disabled={this.state.save}
                  maxPoints={this.state.maxPoints}
                  isModifying={this.props.isModifying}
                  onChangeMaxPoints={this.onChangeMaxPoints} />
              :(this.props.typeQuestion === 'GroupeQuestion')?
                ''
              :''
            :''
            }
            {/* {(this.state.typeQuestion === 'Question ouverte')?
                (
                  <QuestionOuverte />
                ):
              (this.state.typeQuestion === 'Choix unique')?
                (
                  <React.Fragment>
                    <QuestionChoixUnique />
                    <QuestionChoixUnique />
                    <QuestionChoixUnique />
                  </React.Fragment>
                ):
              (this.state.typeQuestion === 'Choix multiple')?
                (
                  <React.Fragment>
                    <QuestionChoixMultiple />
                    <QuestionChoixMultiple />
                    <QuestionChoixMultiple />
                  </React.Fragment>
                ):
              (this.state.typeQuestion === 'Évaluation par points')?
                (
                  <QuestionPoints />
                ):''
            } */}
          </Form>
          {this.props.isModifying?
            (
              <React.Fragment>
                {this.state.save?
                  <Button
                    size='huge'
                    circular
                    icon='edit'
                    floated='right'
                    primary
                    onClick={() => {
                      this.setState({
                        save: false
                      });
                    }}
                  />:
                  (
                    <React.Fragment>
                      <Button
                        size='huge'
                        circular
                        icon='save'
                        floated='right'
                        positive
                        onClick={() => {
                          if (this.props.typeQuestion === 'QuestionChoix') {
                            const params = {
                              intitule: this.state.intitule,
                              estObligatoire: (this.state.isOptional==='Obligatoire')?true:false,
                              nombreChoix: 5,
                              estUnique: this.props.isUnique,
                              ordre: this.props.ordre,
                              type: "QuestionChoix"
                            }
  
                            this.props.client.QuestionChoix.update(
                              this.props.idQuestion,
                              params,
                              result => {
                                this.setState({
                                  save: true
                                });
                              },
                              error => {
                                console.log(error);
                              }
                            )
                          } else if (this.props.typeQuestion === 'QuestionOuverte') {
                            const params = {
                              intitule: this.state.intitule,
                              estObligatoire: (this.state.isOptional==='Obligatoire')?true:false,
                              nombreDeCaractere: this.state.nbCharactere,
                              ordre: this.props.ordre,
                              type: "QuestionOuverte"
                            }

                            this.props.client.QuestionOuverte.update(
                              this.props.idQuestion,
                              params,
                              result => {
                                this.setState({
                                  save: true
                                });
                              },
                              error => {
                                console.log(error);
                              }
                            )
                          } else if (this.props.typeQuestion === 'QuestionPoint') {
                            const params = {
                              intitule: this.state.intitule,
                              estObligatoire: (this.state.isOptional==='Obligatoire')?true:false,
                              minPoints: this.props.minPoints,
                              maxPoints: this.state.maxPoints,
                              ordre: this.props.ordre,
                              type: "QuestionPoint"
                            }

                            this.props.client.QuestionPoints.update(
                              this.props.idQuestion,
                              params,
                              result => {
                                this.setState({
                                  save: true
                                });
                              },
                              error => {
                                console.log(error);
                              }
                            )
                          } else if (this.props.typeQuestion === 'GroupeQuestion') {
                            const params = {
                              intitule: this.state.intitule,
                              estObligatoire: (this.state.isOptional==='Obligatoire')?true:false,
                              numerosDeQuestionsGroupe:'',
                              ordre: this.props.ordre,
                              type: "GroupeQuestion"
                            }

                            this.props.client.GroupQuestions.update(
                              this.props.idQuestion,
                              params,
                              result => {
                                this.setState({
                                  save: true
                                });
                              },
                              error => {
                                console.log(error);
                              }
                            )
                          }
                        }}
                      />
                      <Button
                        size='huge'
                        circular
                        icon='trash'
                        floated='right'
                        negative
                        onClick={() => {
                          this.setState({
                            remove: true
                          });
                        }}
                      />
                    </React.Fragment>
                  )
                }
              </React.Fragment>
            ):
            (
              <React.Fragment>
                <Button
                  content='Enregistrer'
                  floated='right'
                  positive
                />
                <Button
                  content='Annuler'
                  floated='right'
                  basic
                  color='black'
                />
              </React.Fragment>
            )
          }
        </Segment>
        <Confirm
          header="Suppression définitive de la question"
          content="Etes-vous sûr de vouloir supprmier votre question ?"
          open={this.state.remove}
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
              remove: false
            })
          }}
          onConfirm={() => {
            if (this.props.typeQuestion === 'QuestionChoix') {
              this.props.client.QuestionChoix.delete(
                this.props.idQuestion,
                result => {
                  this.props.client.Choix.readByQuestion(
                    this.props.idQuestion,
                    result => {
                      if (this.props.removeQuestion) {
                        this.props.removeQuestion()
                      }

                      this.setState({
                        remove: false
                      });

                      _.map(result.data, choix => {
                        this.props.client.Choix.delete(
                          choix.id_choix,
                          result => {
                            this.update();
                          },
                          error => {
                            console.log(error);
                          }
                        );
                      })
                    },
                    error => {
                      console.log(error);
                    }
                  )
                },
                error => {
                  console.log(error)
                }
              )
            } else if (this.props.typeQuestion === 'QuestionOuverte') {
              this.props.client.QuestionOuverte.delete(
                this.props.idQuestion,
                result => {
                  if (this.props.removeQuestion) {
                    this.props.removeQuestion()
                  }
                  this.setState({
                    remove: false
                  })
                },
                error => {
                  console.log(error)
                }
              )
            } else if (this.props.typeQuestion === 'QuestionPoint') {
              this.props.client.QuestionPoints.delete(
                this.props.idQuestion,
                result => {
                  if (this.props.removeQuestion) {
                    this.props.removeQuestion()
                  }
                  this.setState({
                    remove: false
                  })
                },
                error => {
                  console.log(error)
                }
              )
            } else if (this.props.typeQuestion === 'GroupeQuestion') {
              this.props.client.GroupQuestions.delete(
                this.props.idQuestion,
                result => {
                  if (this.props.removeQuestion) {
                    this.props.removeQuestion()
                  }
                  this.setState({
                    remove: false
                  })
                },
                error => {
                  console.log(error)
                }
              )
            }
          }}
        />
      </React.Fragment>
    );
  }
}