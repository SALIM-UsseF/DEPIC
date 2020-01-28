import React from 'react'
import PropTypes from 'prop-types'

import { 
  Form 
} from 'semantic-ui-react';

export default class QuestionChoixUnique extends React.Component {
  static propTypes = {
    choix: PropTypes.string,
    isModifying: PropTypes.bool
  }

  render() {
    return (
      <React.Fragment>
        <Form.Group inline>
          <Form.Radio readOnly />
          <Form.Input
            value={this.props.isModifying?this.props.choix:''}
            placeholder='Saisissez un choix de réponse'
            width='12'
          />
          <Form.Button
            circular 
            icon='plus' 
          />
          <Form.Button
            circular 
            icon='minus' 
          />
        </Form.Group>
      </React.Fragment>
    );
  }
}