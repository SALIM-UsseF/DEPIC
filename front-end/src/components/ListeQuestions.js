import React from 'react'
import _ from 'lodash'

import { 
  List 
} from 'semantic-ui-react';

import Question from './Question'

export default class ListeQuestions extends React.Component {
  state = {
    question: '',
    typeQuestion: '',
    character: ''
  }

  onChangeQuestion = question => {
    this.setState({ question: question });
  }

  onChangeTypeQuestion = type => {
    this.setState({ typeQuestion: type });
  }

  onChangeCharacter = character => {
    this.setState({ character: character });
  }

  render() {
    return (
      <React.Fragment>
        <List
          divided 
          verticalAlign='middle'
        >
          {_.map(this.props.listQuestions, numberOfQuestion => (
            <List.Item key={numberOfQuestion}>
              <Question
                numberOfQuestion={numberOfQuestion}
                onChangeQuestion={this.onChangeQuestion}
                onChangeTypeQuestion={this.onChangeTypeQuestion}
                onChangeCharacter={this.onChangeCharacter}
              />
            </List.Item>
          ))}
        </List>
      </React.Fragment>
    );
  }
}