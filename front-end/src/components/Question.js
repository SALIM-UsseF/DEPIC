import React from 'react'

import { 
  Form 
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
  state = {
    typeQuestion: ""
  }

  onChangeTypeQuestion = value => {
    this.setState({
      typeQuestion: value
    });
  }

  render() {
    console.log(this.state.typeQuestion);

    return (
      <React.Fragment>
        <Form>
          <p>Q1</p>
          <Form.Group widths='equal'>
            <Form.Input
              placeholder='Saisissez votre question'
            />
            <ListTypeQuestion
              fluid={false}
              onChangeTypeQuestion={this.onChangeTypeQuestion}
            />
            <Form.Select
              options={options}
              placeholder='Facultative'
            />
          </Form.Group>
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
      </React.Fragment>
    );
  }
}