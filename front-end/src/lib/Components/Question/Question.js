import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import {
  Divider,
  Form, 
  Segment,
  Button
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
    nbChoix: PropTypes.number,
    isUnique: PropTypes.bool,
    nbCharactere: PropTypes.number,
    maxPoints: PropTypes.number
  }

  state = {
    typeQuestion: '',
    isOptional: this.props.isOptional?'Obligatoire':'Facultative',
    choix: []
  }

  componentDidMount() {
    this.props.client.Choix.read(
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

  render() {
    let choixunique = (
      <React.Fragment>
        {_.map(this.state.choix, uniqueChoix => (
          <QuestionChoixUnique
            key={uniqueChoix.id_choix}
            isModifying={this.props.isModifying}
            choix={uniqueChoix.intituleChoix} />
        ))}
      </React.Fragment>
    );
    let choixMultiple = (
      <React.Fragment>
        {_.map(this.state.choix, uniqueChoix => (
          <QuestionChoixMultiple
            key={uniqueChoix.id_choix}
            isModifying={this.props.isModifying}
            choix={uniqueChoix.intituleChoix} />
        ))}
      </React.Fragment>
    );
    return (
      <React.Fragment>
        <Segment 
          secondary 
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
            <Form.Group widths='equal'>
              <Form.Input
                value={this.props.isModifying?this.props.intituleQuestion:''}
                placeholder='Saisissez votre question'
                onChange={(e, data) => {
                  if (this.props.onChangeQuestion) {
                    this.props.onChangeQuestion(data.value);
                  }
                }}
              />
              <ListTypeQuestion
                client={this.props.client}
                fluid={false}
                onChangeTypeQuestion={this.onChangeTypeQuestion}
                isModifying={this.props.isModifying}
                typeOfQuestion={this.props.typeQuestion}
                isUnique={this.props.isUnique}
              />
              <Form.Select
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
                  nbCharactere={this.props.nbCharactere}
                  isModifying={this.props.isModifying} />
              :(this.props.typeQuestion === 'QuestionPoint')?
                <QuestionPoints
                  maxPoints={this.props.maxPoints}
                  isModifying={this.props.isModifying} />
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
                <Button
                  content='Enregistrer'
                  floated='right'
                  positive
                />
                <Button
                  content='Supprimer'
                  floated='right'
                  negative
                />
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
      </React.Fragment>
    );
  }
}