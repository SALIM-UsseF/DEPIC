import React from 'react'
import _ from 'lodash'

import { 
  Button, 
  Segment
} from 'semantic-ui-react'

import ListeQuestions from './ListeQuestions'

export default class CreationQuestions extends React.Component {
  state = {
    listQuestions: [1]
  }

  onNextQuestion = () => {
    let list = this.state.listQuestions;
    let next = _.last(list) + 1;
    list.push(next);

    this.setState({
      listQuestions: list
    })
  }
  
  render() {
    return (
      <React.Fragment>
        <Segment>
          <ListeQuestions
            listQuestions={this.state.listQuestions}
          />
          <Button
            icon='plus circle'
            content='Question suivante'
            positive
            onClick={this.onNextQuestion}
          />
        </Segment>
      </React.Fragment>
    );
  }
}