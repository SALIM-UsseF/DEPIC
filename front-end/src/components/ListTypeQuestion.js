import React from 'react'

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
  render() {
    return (
      <React.Fragment>
        <Form.Select
          placeholder='Question ouverte'
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