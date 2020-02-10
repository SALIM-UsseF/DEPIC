import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import { 
  Button
} from 'semantic-ui-react'

import ListQuestions from './ListQuestions'

export default class CreationQuestions extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired
  }

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
        <ListQuestions
          client={this.props.client}
          listQuestions={this.state.listQuestions}
        />
        <Button
          icon='plus circle'
          content='Question suivante'
          positive
          onClick={this.onNextQuestion}
        />
      </React.Fragment>
    );
  }
}