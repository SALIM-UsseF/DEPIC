import React from 'react'

import {
  Form
} from 'semantic-ui-react'

export default class QuestionOuverte extends React.Component {
  render() {
    return (
      <React.Fragment>
        <Form.Group>
          <Form.Input
            fluid
            label='Nombre de caractère autorisé'
            placeholder='100'
          />
        </Form.Group>
      </React.Fragment>
    );
  }
}