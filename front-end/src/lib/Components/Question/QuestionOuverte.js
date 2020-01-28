import React from 'react'
import PropTypes from 'prop-types'

import {
  Form
} from 'semantic-ui-react'

export default class QuestionOuverte extends React.Component {
  static propTypes = {
    nbCharactere: PropTypes.number,
    isModifying: PropTypes.bool
  }

  render() {
    return (
      <React.Fragment>
        <Form.Group>
          <Form.Input
            value={this.props.isModifying?this.props.nbCharactere:''}
            fluid
            label='Nombre de caractères autorisés'
            placeholder='100'
          />
        </Form.Group>
      </React.Fragment>
    );
  }
}