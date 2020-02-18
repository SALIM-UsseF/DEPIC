import React from 'react'
import PropTypes from 'prop-types'

import {
  Form
} from 'semantic-ui-react'

export default class QuestionOuverte extends React.Component {
  static propTypes = {
    nbCharactere: PropTypes.number,
    isModifying: PropTypes.bool,
    disabled: PropTypes.bool,
    onChangeNbCharactere: PropTypes.func
  }

  render() {
    return (
      <React.Fragment>
        <Form.Group>
          <Form.Input
            disabled={this.props.isModifying?this.props.disabled:false}
            value={this.props.nbCharactere}
            fluid
            label='Nombre de caractères autorisés'
            placeholder='100'
            onChange={(e, data) => {
              if (this.props.onChangeNbCharactere) {
                this.props.onChangeNbCharactere(data.value);
              }
            }}
          />
        </Form.Group>
      </React.Fragment>
    );
  }
}