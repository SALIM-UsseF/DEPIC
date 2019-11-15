import React from 'react'
import PropTypes from 'prop-types'

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
    numberOfQuestion: PropTypes.number,
    onChangeQuestion: PropTypes.func,
    onChangeTypeQuestion: PropTypes.func,
    onChangeCharacter: PropTypes.func
  }

  state = {
    typeQuestion: ''
  }

  onChangeTypeQuestion = value => {
    this.setState({ typeQuestion: value });
    
    if (this.props.onChangeTypeQuestion) {
      this.props.onChangeTypeQuestion(value);
    }
  }

  render() {
    return (
      <React.Fragment>
        <Segment 
          secondary 
          color='black'
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
                placeholder='Saisissez votre question'
                onChange={(e, data) => {
                  if (this.props.onChangeQuestion) {
                    this.props.onChangeQuestion(data.value);
                  }
                }}
              />
              <ListTypeQuestion
                fluid={false}
                onChangeTypeQuestion={this.onChangeTypeQuestion}
              />
              <Form.Select
                options={options}
                placeholder='Facultative'
                onChange={(e, data) => {
                  if (this.props.onChangeCharacter) {
                    this.props.onChangeCharacter(data.value);
                  }
                }}
              />
            </Form.Group>
            <Divider />
            {(this.state.typeQuestion === 'Question ouverte')?
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
            }
          </Form>
          {(this.state.typeQuestion !== '')?
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
            ):''
          }
        </Segment>
      </React.Fragment>
    );
  }
}