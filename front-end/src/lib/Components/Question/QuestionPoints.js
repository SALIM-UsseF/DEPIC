import React from 'react'
import PropTypes from 'prop-types'

import { 
  Form
} from 'semantic-ui-react'

export default class QuestionPoints extends React.Component {
  static propTypes = {
    maxPoints: PropTypes.number,
    isModifying: PropTypes.bool
  }

  render() {
    return (
      <React.Fragment>
        <Form.Group>
          <Form.Input
            value={this.props.isModifying?this.props.maxPoints:''}
            fluid
            label='Nombre de points maximal'
            placeholder='5'
          />
        </Form.Group>
      </React.Fragment>
    );
  }
}