import React from 'react'
import PropTypes from 'prop-types'

import Question from './Question'

const styles = {
  frame: {
    marginBottom: "30px"
  }
};

export default class MainQuestion extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    isModifying: PropTypes.bool,
    question: PropTypes.object,
    numberOfQuestion: PropTypes.number,
    removeQuestion: PropTypes.func,
  }

  removeQuestion = () => {
    if (this.props.removeQuestion) {
      this.props.removeQuestion();
    }
  }
  
  render() {
    return (
      <React.Fragment>
        <div style={styles.frame}>
          <React.Fragment>
            <Question
              client={this.props.client}
              question={this.props.question}
              numberOfQuestion={this.props.numberOfQuestion}
              isModifying={this.props.isModifying}
              color='teal'
              onChangeQuestion={this.onChangeQuestion}
              onChangeTypeQuestion={this.onChangeTypeQuestion}
              onChangeCharacter={this.onChangeCharacter}
              removeQuestion={this.removeQuestion}
            />
          </React.Fragment>
        </div>
      </React.Fragment>
    );
  }
}