import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import { MainQuestion } from './../Question'
import ListTypeQuestion from '../Question/ListTypeQuestion'

// import { dictionnary } from '../../Langs/langs'

import Title from '../Title'
import { TextArea, Form, Divider, Button, Confirm, Modal } from 'semantic-ui-react'

const options = [
  { key: 'Facultative', text: 'Facultative', value: 'Facultative' },
  { key: 'Obligatoire', text: 'Obligatoire', value: 'Obligatoire' }
]

export default class CreateSurvey extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    idSondage: PropTypes.number,
    modifying: PropTypes.bool,
    onUpdate: PropTypes.func
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    idSondage: 0,
    nomSondage: '',
    questions: [],
    descriptionSondage: '',
    confirmModification: false,
    ajoutNouvelleQuestion: false,
    intituleNouvelleQuestion: '',
    typeNouvelleQuestion: '',
    estObligatoireNouvelleQuestion: '',
    maxPointsNouvelleQuestion: 5,
    numerosDeQuestionsGroupeNouvelleQuestion: '0',
    nbCharactereNouvelleQuestion: 100,
    nombreChoixNouvelleQuestion: 5,
    errorIntituleNouvelleQuestion: false,
    errorTypeNouvelleQuestion: false,
    errorEstObligatoireNouvelleQuestion: false,
    categories: [],
    nomCategorie: '',
    categorie_id: '',
    errorCategorie: false,
    openModalCategorie: false,
    nouvelleCategorie: '',
    errorNouvelleCategorie: false
  }

  componentDidMount() {
    if (this.props.modifying) {
      this.props.client.Sondage.read(
        this.props.idSondage,
        result => {
          this.props.client.Question.readBySondage(
            result.data.id_sondage,
            success => {
              this.setState({
                idSondage: result.data.id_sondage,
                nomSondage: result.data.intituleSondage,
                descriptionSondage: result.data.descriptionSondage,
                categorie_id: result.data.categorie_id,
                questions: success.data
              });
            },
            error => {
              this.setState({
                idSondage: result.data.id_sondage,
                nomSondage: result.data.intituleSondage,
                descriptionSondage: result.data.descriptionSondage,
                categorie_id: result.data.categorie_id,
                questions: []
              });
            }
          )
        },
        error => {
          console.log(error)
        }
      )
    } else {
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
  }

  componentDidUpdate(prevProps) {
    if (this.props.idSondage !== prevProps.idSondage) {
      this.updateSondage();
    }
  }

  updateSondage = () => {
    this.props.client.Sondage.read(
      this.props.idSondage,
      result => {
        this.props.client.Question.readBySondage(
          result.data.id_sondage,
          success => {
            this.setState({
              idSondage: result.data.id_sondage,
              nomSondage: result.data.intituleSondage,
              descriptionSondage: result.data.descriptionSondage,
              categorie_id: result.data.categorie_id,
              questions: success.data
            })
          },
          error => {
            this.setState({
              idSondage: result.data.id_sondage,
              nomSondage: result.data.intituleSondage,
              descriptionSondage: result.data.descriptionSondage,
              categorie_id: result.data.categorie_id,
              questions: []
            });
          }
        )
      },
      error => {
        console.log(error)
      }
    )
  }

  removeQuestion = () => {
    this.props.client.Question.readBySondage(
      this.props.idSondage,
      success => {
        this.setState({
          questions: success.data
        });
      },
      error => {
        this.setState({
          questions: []
        });
      }
    )
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
    let key1 = 0;
    let key2 = 0;
    let creation = (
      <React.Fragment>
        <Title
          as='h1'
          content={_.upperFirst(this.state.nomSondage)}
          color='teal' />
      
        <Form>
          <TextArea
            value={this.state.descriptionSondage}
            onChange={(e, value) => {
              this.setState({
                descriptionSondage: value.value
              });
            }}
            style={{ minHeight: 100 }}
          />
        </Form>
        <Divider hidden />
        {_.map(this.state.questions, question => {
          key1++;

          return (               
            <React.Fragment key={key1}>
              <MainQuestion
                client={this.props.client}
                isModifying={this.props.modifying}
                question={question}
                numberOfQuestion={key1}
                removeQuestion={this.removeQuestion} />
            </React.Fragment>
          )
        })}
        <div style={{ marginBottom:"50px" }}>
          <Button
            icon='plus'
            content='Ajouter une question'
            floated='left'
            primary
            onClick={() => {
              this.setState({
                ajoutNouvelleQuestion: true,
                intituleNouvelleQuestion: '',
                typeNouvelleQuestion: '',
                estObligatoireNouvelleQuestion: '',
                maxPointsNouvelleQuestion: 5,
                numerosDeQuestionsGroupeNouvelleQuestion: '0',
                nbCharactereNouvelleQuestion: 100,
                nombreChoixNouvelleQuestion: 5
              });
            }}
          />
          <Button
            content='Terminer'
            floated='right'
            positive
            onClick={() => {
              this.props.client.Sondage.update(
                this.props.idSondage,
                {
                  intituleSondage: this.state.nomSondage,
                  descriptionSondage: this.state.descriptionSondage,
                  categorie_id: this.state.categorie_id
                },
                result => {
                  this.setState({
                    confirmModification: true
                  })
                },
                error => {
                  console.log(error);
                }
              )
            }}
          />
        </div>
      </React.Fragment>
    );
    let modification = (
      <React.Fragment>
        <Title
          as='h1'
          content={_.upperFirst(this.state.nomSondage)}
          color='teal' />
      
        <Form>
          <TextArea
            value={this.state.descriptionSondage}
            onChange={(e, value) => {
              this.setState({
                descriptionSondage: value.value
              })
            }}
            style={{ minHeight: 100 }}
          />
        </Form>
        <Divider hidden />
        {_.map(this.state.questions, question => {
          key2++;

          return (               
            <React.Fragment key={key2}>
              <MainQuestion
                client={this.props.client}
                isModifying={this.props.modifying}
                question={question}
                numberOfQuestion={key2}
                removeQuestion={this.removeQuestion} />
            </React.Fragment>
          )
        })}
        <div style={{ marginBottom:"50px" }}>
          <Button
            icon='plus'
            content='Ajouter une question'
            floated='left'
            primary
            onClick={() => {
              this.setState({
                ajoutNouvelleQuestion: true,
                intituleNouvelleQuestion: '',
                typeNouvelleQuestion: '',
                estObligatoireNouvelleQuestion: '',
                maxPointsNouvelleQuestion: 5,
                numerosDeQuestionsGroupeNouvelleQuestion: '0',
                nbCharactereNouvelleQuestion: 100,
                nombreChoixNouvelleQuestion: 5
              });
            }}
          />
          <Button
            content='Terminer'
            floated='right'
            positive
            onClick={() => {
              this.props.client.Sondage.update(
                this.props.idSondage,
                {
                  intituleSondage: this.state.nomSondage,
                  descriptionSondage: this.state.descriptionSondage,
                  categorie_id: this.state.categorie_id
                },
                result => {
                  this.setState({
                    confirmModification: true
                  })
                },
                error => {
                  console.log(error);
                }
              )
            }}
          />
        </div>
      </React.Fragment>
    );

    return (
      <React.Fragment>
        {this.props.modifying?
          modification
          :creation
        }
        <Confirm
          header="Votre sondage a bien été enregistré"
          content="Voulez-vous continuer vos modifications ?"
          open={this.state.confirmModification}
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
              content='Oui' />
          }
          onConfirm={() => {
            this.setState({
              confirmModification: false
            });
          }}
          onCancel={() => {
            this.setState({
              confirmModification: false
            });

            if (this.props.onUpdate) {
              this.props.onUpdate();
            }
          }}
        />
        <Modal
          open={this.state.ajoutNouvelleQuestion}
          size='large'
        >
          <Modal.Header>Ajout d'une nouvelle question</Modal.Header>
          <Modal.Content>
            <Form>
              <Form.Group>
                <Form.Input
                  value={this.state.intituleNouvelleQuestion}
                  placeholder='Saisissez votre question'
                  onChange={(e, data) => {
                    this.setState({
                      intituleNouvelleQuestion: data.value,
                      errorIntituleNouvelleQuestion: false
                    });
                  }}
                  width={10}
                  error={this.state.errorIntituleNouvelleQuestion} />
                <ListTypeQuestion
                  disabled={false}
                  client={this.props.client}
                  fluid={false}
                  onChangeTypeQuestion={value => {
                    this.setState({
                      typeNouvelleQuestion: value,
                      errorTypeNouvelleQuestion: false
                    });
                  }}
                  isModifying={false}
                  typeOfQuestion={this.state.typeNouvelleQuestion}
                  isUnique={(this.state.typeNouvelleQuestion==='Choix unique')?true:false}
                  width={3}
                  error={this.state.errorTypeNouvelleQuestion} />
                <Form.Select
                  disabled={false}
                  options={options}
                  value={this.state.estObligatoireNouvelleQuestion}
                  placeholder='Facultative'
                  onChange={(e, data) => {
                    this.setState({
                      estObligatoireNouvelleQuestion: data.value,
                      errorEstObligatoireNouvelleQuestion: false
                    });
                  }}
                  width={3}
                  error={this.state.errorEstObligatoireNouvelleQuestion}
                />
              </Form.Group>
            </Form>
            <Form>
              <Form.Group>
                {(this.state.typeNouvelleQuestion === 'Choix unique' || this.state.typeNouvelleQuestion === 'Choix multiple')?
                  <Form.Input
                    label='Nombre de choix'
                    placeholder='5'
                    value={this.state.nombreChoixNouvelleQuestion}
                    onChange={(e, data) => {
                      this.setState({
                        nombreChoixNouvelleQuestion: (data.value==='')?0:parseInt(data.value)
                      });
                    }}
                  />
                  :(this.state.typeNouvelleQuestion === 'Question ouverte')?
                    <Form.Input
                      label='Nombre de caractères autorisés'
                      placeholder='100'
                      value={this.state.nbCharactereNouvelleQuestion}
                      onChange={(e, data) => {
                        this.setState({
                          nbCharactereNouvelleQuestion: (data.value==='')?0:parseInt(data.value)
                        });
                      }}
                    />
                    :(this.state.typeNouvelleQuestion === 'Groupe de questions')?
                      <Form.Input
                        label='Numéro de questions'
                        placeholder='1,2,3'
                        value={this.state.numerosDeQuestionsGroupeNouvelleQuestion}
                        onChange={(e, data) => {
                          this.setState({
                            numerosDeQuestionsGroupe: data.value
                          });
                        }}
                      />
                      :(this.state.typeNouvelleQuestion === 'Évaluation par points')?
                        <Form.Input
                          label='Nombre de points maximal'
                          placeholder='5'
                          value={this.state.maxPointsNouvelleQuestion}
                          onChange={(e, data) => {
                            this.setState({
                              maxPointsNouvelleQuestion: (data.value==='')?0:parseInt(data.value)
                            });
                          }}
                        />
                        :''
                }
              </Form.Group>
            </Form>
          </Modal.Content>
          <Modal.Actions>
            <Button
              negative
              content='Annuler'
              onClick={() => {
                this.setState({
                  ajoutNouvelleQuestion: false
                });
              }}
            />
            <Button
              positive
              content='Ajouter'
              onClick={() => {
                if (this.state.intituleNouvelleQuestion === '') {
                  this.setState({
                    errorIntituleNouvelleQuestion: true
                  });
                  return;
                }

                if (this.state.typeNouvelleQuestion === '') {
                  this.setState({
                    errorTypeNouvelleQuestion: true
                  });
                  return;
                }

                if (this.state.estObligatoireNouvelleQuestion === '') {
                  this.setState({
                    errorEstObligatoireNouvelleQuestion: true
                  });
                  return;
                }

                this.setState({
                  ajoutNouvelleQuestion: false
                });

                if (this.state.typeNouvelleQuestion === 'Choix unique') {
                  this.props.client.QuestionChoix.create(
                    {
                      intitule: this.state.intituleNouvelleQuestion, 
                      estObligatoire: (this.state.estObligatoireNouvelleQuestion === 'Facultative')?false: true, 
                      estUnique: true, 
                      nombreChoix: this.state.nombreChoixNouvelleQuestion, 
                      ordre: 2, // Ordre par défaut
                      sondage_id: this.props.idSondage
                    },
                    result => {
                      this.updateSondage();
                    },
                    error => {
                      console.log(error);
                    }
                  )
                } else if (this.state.typeNouvelleQuestion === 'Choix multiple') {
                    this.props.client.QuestionChoix.create(
                      {
                        intitule: this.state.intituleNouvelleQuestion, 
                        estObligatoire: (this.state.estObligatoireNouvelleQuestion === 'Facultative')?false: true, 
                        estUnique: false,
                        nombreChoix: this.state.nombreChoixNouvelleQuestion,
                        ordre: 2, // Ordre par défaut
                        sondage_id: this.props.idSondage
                      },
                      result => {
                        this.updateSondage();
                      },
                      error => {
                        console.log(error);
                      }
                    )
                } else if (this.state.typeNouvelleQuestion === 'Question ouverte') {
                  this.props.client.QuestionOuverte.create(
                    {
                      intitule: this.state.intituleNouvelleQuestion, 
                      estObligatoire: (this.state.estObligatoireNouvelleQuestion === 'Facultative')?false: true, 
                      nombreDeCaractere: this.state.nbCharactereNouvelleQuestion,
                      ordre: 2, // Ordre par défaut
                      sondage_id: this.props.idSondage
                    },
                    result => {
                      this.updateSondage();
                    },
                    error => {
                      console.log(error);
                    }
                  )
                } else if (this.state.typeNouvelleQuestion === 'Évaluation par points') {
                  this.props.client.QuestionPoints.create(
                    {
                      intitule: this.state.intituleNouvelleQuestion, 
                      estObligatoire: (this.state.estObligatoireNouvelleQuestion === 'Facultative')?false: true, 
                      minPoints: 0, 
                      maxPoints: this.state.maxPointsNouvelleQuestion,
                      ordre: 2, // Ordre par défaut
                      sondage_id: this.props.idSondage
                    },
                    result => {
                      this.updateSondage();
                    },
                    error => {
                      console.log(error);
                    }
                  )
                } else if (this.state.typeNouvelleQuestion === 'Groupe de questions') {
                  this.props.client.GroupQuestions.create(
                    {
                      intitule: this.state.intituleNouvelleQuestion, 
                      estObligatoire: (this.state.estObligatoireNouvelleQuestion === 'Facultative')?false: true, 
                      numerosDeQuestionsGroupe: this.state.numerosDeQuestionsGroupeNouvelleQuestion,
                      ordre: 2, // Ordre par défaut
                      sondage_id: this.props.idSondage
                    },
                    result => {
                      this.updateSondage();
                    },
                    error => {
                      console.log(error);
                    }
                  )
                }
              }}
            />
          </Modal.Actions>
        </Modal>
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
  }
}