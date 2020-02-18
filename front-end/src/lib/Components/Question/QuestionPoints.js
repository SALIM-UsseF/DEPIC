import React from 'react'
import PropTypes from 'prop-types'

import { 
  Form
} from 'semantic-ui-react'

export default class QuestionPoints extends React.Component {
  static propTypes = {
    maxPoints: PropTypes.number,
    isModifying: PropTypes.bool,
    disabled: PropTypes.bool,
    onChangeMaxPoints: PropTypes.func
  }

  render() {
    return (
      <React.Fragment>
        <Form.Group>
          <Form.Input
            disabled={this.props.isModifying?this.props.disabled:false}
            value={this.props.maxPoints}
            fluid
            label='Nombre de points maximal'
            placeholder='5'
            onChange={(e, data) => {
              if (this.props.onChangeMaxPoints) {
                this.props.onChangeMaxPoints(data.value);
              }
            }}
          />
        </Form.Group>
      </React.Fragment>
    );
  }
}