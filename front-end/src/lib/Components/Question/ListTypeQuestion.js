import React from 'react'
import PropTypes from 'prop-types'

import {
  Form
} from 'semantic-ui-react'

const typeOfQuestion = [
  {
    key: 'Question ouverte',
    text: 'Question ouverte',
    value: 'Question ouverte',
    icon: 'list alternate outline',
  },
  {
    key: 'Choix unique',
    text: 'Choix unique',
    value: 'Choix unique',
    icon: 'list ul',
  },
  {
    key: 'Choix multiple',
    text: 'Choix multiple',
    value: 'Choix multiple',
    icon: 'check square outline',
  },
  {
    key: 'Évaluation par points',
    text: 'Évaluation par points',
    value: 'Évaluation par points',
    icon: 'star outline',
  }
];

/**
 * Différents types de question représentés dans une liste déroulante :
 * Question ouverte
 * Choix unique
 * Choix multiple
 * Évaluation par points
 */

export default class ListTypeQuestion extends React.Component {
  static propTypes = {
    onChangeTypeQuestion: PropTypes.func,
    fluid: PropTypes.bool,
    width: PropTypes.number,
    isModifying: PropTypes.bool,
    typeOfQuestion: PropTypes.string,
    isUnique: PropTypes.bool,
    disabled: PropTypes.bool
  }

  render() {
    let type = '';

    if (this.props.typeOfQuestion === 'QuestionChoix') {
      if (this.props.isUnique) {
        type = 'Choix unique';
      } else {
        type = 'Choix multiple';
      }
    } else if (this.props.typeOfQuestion === 'QuestionOuverte') {
      type = 'Question ouverte';
    } else if (this.props.typeOfQuestion === 'GroupeQuestion') {
      type = 'Groupe de questions';
    } else if (this.props.typeOfQuestion === 'QuestionPoint') {
      type = 'Évaluation par points';
    }

    return (
      <React.Fragment>
        <Form.Select
          disabled={this.props.disabled}
          placeholder={this.props.isModifying?type:'Question ouverte'}
          fluid={this.props.fluid}
          width={this.props.width}
          selection
          options={typeOfQuestion}
          onChange={(e, data) => {
            if (this.props.onChangeTypeQuestion) {
              this.props.onChangeTypeQuestion(data.value);
            }
          }}
        />
      </React.Fragment>
    );
  }
}