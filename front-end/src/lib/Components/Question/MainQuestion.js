import React from 'react'
import PropTypes from 'prop-types'

import CreationQuestions from './CreationQuestions'

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
    idQuestion: PropTypes.number,
    numberOfQuestion: PropTypes.number,
    type: PropTypes.string,
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

  removeQuestion = () => {
    if (this.props.removeQuestion) {
      this.props.removeQuestion();
    }
  }
  
  render() {
    return (
      <React.Fragment>
        <div style={styles.frame}>
          {this.props.isModifying?
            (
              <React.Fragment>
                <Question
                  client={this.props.client}
                  idQuestion={this.props.idQuestion}
                  color='teal'
                  numberOfQuestion={this.props.numberOfQuestion}
                  onChangeQuestion={this.onChangeQuestion}
                  onChangeTypeQuestion={this.onChangeTypeQuestion}
                  onChangeCharacter={this.onChangeCharacter}
                  isModifying={this.props.isModifying}
                  typeQuestion={this.props.type}
                  intituleQuestion={this.props.intituleQuestion}
                  isOptional={this.props.isOptional}
                  ordre={this.props.ordre}
                  nbChoix={this.props.nbChoix}
                  isUnique={this.props.isUnique}
                  nbCharactere={this.props.nbCharactere}
                  minPoints={this.props.minPoints}
                  maxPoints={this.props.maxPoints}
                  removeQuestion={this.removeQuestion}
                />
              </React.Fragment>
            )
            :<CreationQuestions
              client={this.props.client} />
          }
        </div>
      </React.Fragment>
    );
  }
}