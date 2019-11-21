import React from 'react'

import { 
  Form 
} from 'semantic-ui-react';

export default class QuestionChoixUnique extends React.Component {
  render() {
    return (
      <React.Fragment>
        <Form.Group inline>
          <Form.Radio readOnly />
          <Form.Input
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