import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import {
  Divider,
  Form, 
  Segment,
  Button,
  Confirm,
  Modal,
  Message
} from 'semantic-ui-react'

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
    question: PropTypes.object,
    numberOfQuestion: PropTypes.number,
    isModifying: PropTypes.bool,
    color: PropTypes.string,
    onChangeQuestion: PropTypes.func,
    onChangeTypeQuestion: PropTypes.func,
    onChangeCharacter: PropTypes.func,
    removeQuestion: PropTypes.func
  }

  state = {
    typeQuestion: '',
    choix: [],
    remove: false,
    save: true,
    isOptional: this.props.question.estObligatoire?'Obligatoire':'Facultative',
    intitule: this.props.question.intitule,
    nbCharactere: this.props.question.nombreDeCaractere,
    maxPoints: this.props.question.maxPoints,
    errorEmptyStringQuestion: false,
    enregistrementQuestion: false
  }

  componentDidMount() {
    this.props.client.Choix.readByQuestion(
      this.props.question.id_question,
      result => {
        this.setState({
          choix: result.data
        });
      },
      error => {
        console.log(error);
      }
    )
  }

  componentDidUpdate(prevProps) {
    if (prevProps.question !== this.props.question) {
      this.props.client.Choix.readByQuestion(
        this.props.question.id_question,
        result => {
          this.setState({
            choix: result.data,
            isOptional: this.props.question.estObligatoire?'Obligatoire':'Facultative',
            intitule: this.props.question.intitule,
            nbCharactere: this.props.question.nombreDeCaractere,
            maxPoints: this.props.question.maxPoints,
            typeQuestion: '',
            remove: false,
            save: true
          })
        },
        error => {
          console.log(error);
        }
      )
    }
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
      this.props.question.id_question,
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
            disabled={this.props.isModifying?this.state.save:!this.state.save}
            key={uniqueChoix.id_choix}
            isModifying={this.props.isModifying}
            choix={uniqueChoix.intituleChoix}
            id_choix={uniqueChoix.id_choix}
            update={this.update} />
        ))}
        <Form.Button
          primary 
          disabled={this.props.isModifying?this.state.save:!this.state.save}
          circular 
          icon='plus'
          onClick={() => {
            let params = {
              intituleChoix: 'Nouveau choix',
              question_id: this.props.question.id_question
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
            idQuestion={this.props.question.id_question}
            disabled={this.props.isModifying?this.state.save:!this.state.save}
            key={uniqueChoix.id_choix}
            isModifying={this.props.isModifying}
            choix={uniqueChoix.intituleChoix}
            id_choix={uniqueChoix.id_choix}
            update={this.update} />
        ))}
        <Form.Button
          primary 
          disabled={this.props.isModifying?this.state.save:!this.state.save}
          circular 
          icon='plus'
          onClick={() => {
            let params = {
              intituleChoix: 'Nouveau choix',
              question_id: this.props.question.id_question
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
                error={this.state.errorEmptyStringQuestion}
                disabled={this.props.isModifying?this.state.save:!this.state.save}
                value={this.state.intitule}
                placeholder='Saisissez votre question'
                onChange={(e, data) => {
                  this.setState({
                    intitule: data.value,
                    errorEmptyStringQuestion: false
                  });

                  if (data.value === '') {
                    return;
                  }
                  
                  if (this.props.onChangeQuestion) {
                    this.props.onChangeQuestion(data.value);
                  }
                }}
                width={13}
              />
              <Form.Select
                disabled={this.props.isModifying?this.state.save:!this.state.save}
                options={options}
                value={this.state.isOptional}
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
            {true?
              (this.props.question.type === 'QuestionChoix')?
                (this.props.question.estUnique)?
                  choixunique
                :choixMultiple
              :(this.props.question.type === 'QuestionOuverte')?
                <QuestionOuverte
                  disabled={this.props.isModifying?this.state.save:!this.state.save}
                  nbCharactere={this.state.nbCharactere}
                  isModifying={this.props.isModifying}
                  onChangeNbCharactere={this.onChangeNbCharactere} />
              :(this.props.question.type === 'QuestionPoint')?
                <QuestionPoints
                  disabled={this.props.isModifying?this.state.save:!this.state.save}
                  maxPoints={this.state.maxPoints}
                  isModifying={this.props.isModifying}
                  onChangeMaxPoints={this.onChangeMaxPoints} />
              :(this.props.question.type === 'GroupeQuestion')?
                ''
              :''
            :''
          }
          </Form>
          <React.Fragment>
            {this.state.save && this.props.isModifying?
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
                      if (this.props.question.type === 'QuestionChoix') {
                        const params = {
                          intitule: this.state.intitule,
                          estObligatoire: (this.state.isOptional==='Obligatoire')?true:false,
                          nombreChoix: 5,
                          estUnique: this.props.question.estUnique,
                          ordre: this.props.question.ordre,
                          type: "QuestionChoix"
                        }

                        this.props.client.QuestionChoix.update(
                          this.props.question.id_question,
                          params,
                          result => {
                            this.setState({
                              save: true,
                              enregistrementQuestion: this.props.isModifying?false:true
                            });
                          },
                          error => {
                            this.setState({
                              errorEmptyStringQuestion: true
                            });
                          }
                        )
                      } else if (this.props.question.type === 'QuestionOuverte') {
                        const params = {
                          intitule: this.state.intitule,
                          estObligatoire: (this.state.isOptional==='Obligatoire')?true:false,
                          nombreDeCaractere: this.state.nbCharactere,
                          ordre: this.props.question.ordre,
                          type: "QuestionOuverte"
                        }

                        this.props.client.QuestionOuverte.update(
                          this.props.question.id_question,
                          params,
                          result => {
                            this.setState({
                              save: true,
                              enregistrementQuestion: this.props.isModifying?false:true
                            });
                          },
                          error => {
                            this.setState({
                              errorEmptyStringQuestion: true
                            });
                          }
                        )
                      } else if (this.props.question.type === 'QuestionPoint') {
                        const params = {
                          intitule: this.state.intitule,
                          estObligatoire: (this.state.isOptional==='Obligatoire')?true:false,
                          minPoints: this.props.question.minPoints,
                          maxPoints: this.state.maxPoints,
                          ordre: this.props.question.ordre,
                          type: "QuestionPoint"
                        }

                        this.props.client.QuestionPoints.update(
                          this.props.question.id_question,
                          params,
                          result => {
                            this.setState({
                              save: true,
                              enregistrementQuestion: this.props.isModifying?false:true
                            });
                          },
                          error => {
                            this.setState({
                              errorEmptyStringQuestion: true
                            });
                          }
                        )
                      } else if (this.props.question.type === 'GroupeQuestion') {
                        const params = {
                          intitule: this.state.intitule,
                          estObligatoire: (this.state.isOptional==='Obligatoire')?true:false,
                          numerosDeQuestionsGroupe:'',
                          ordre: this.props.question.ordre,
                          type: "GroupeQuestion"
                        }

                        this.props.client.GroupQuestions.update(
                          this.props.question.id_question,
                          params,
                          result => {
                            this.setState({
                              save: true,
                              enregistrementQuestion: this.props.isModifying?false:true
                            });
                          },
                          error => {
                            this.setState({
                              errorEmptyStringQuestion: true
                            });
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
            if (this.props.question.type === 'QuestionChoix') {
              this.props.client.QuestionChoix.delete(
                this.props.question.id_question,
                result => {
                  this.props.client.Choix.readByQuestion(
                    this.props.question.id_question,
                    result => {
                      if (this.props.removeQuestion) {
                        this.props.removeQuestion();
                      }

                      this.setState({
                        remove: false
                      });

                      _.map(result.data, choix => {
                        this.props.client.Choix.delete(
                          choix.id_choix,
                          result => {},
                          error => {}
                        );
                      });
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
            } else if (this.props.question.type === 'QuestionOuverte') {
              this.props.client.QuestionOuverte.delete(
                this.props.question.id_question,
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
            } else if (this.props.question.type === 'QuestionPoint') {
              this.props.client.QuestionPoints.delete(
                this.props.question.id_question,
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
            } else if (this.props.question.type === 'GroupeQuestion') {
              this.props.client.GroupQuestions.delete(
                this.props.question.id_question,
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
        <Modal open={this.state.enregistrementQuestion}>
          <Modal.Content>
            <Message positive>
              <p>Votre question vient d'être enregistrée</p>
            </Message>
          </Modal.Content>
          <Modal.Actions>
            <Button
              positive
              icon='checkmark'
              content='OK'
              onClick={() => {
                this.setState({
                  enregistrementQuestion: false
                })
              }}
            />
          </Modal.Actions>
        </Modal>
      </React.Fragment>
    );
  }
}