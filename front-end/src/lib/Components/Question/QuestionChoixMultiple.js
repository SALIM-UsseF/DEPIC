import React from 'react'
import PropTypes from 'prop-types'

import { 
  Form 
} from 'semantic-ui-react';

export default class QuestionChoixMultiple extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    choix: PropTypes.string,
    isModifying: PropTypes.bool,
    disabled: PropTypes.bool,
    id_choix: PropTypes.number,
    update: PropTypes.func,
    idQuestion: PropTypes.number
  }

  state = {
    choix: this.props.isModifying?this.props.choix:'',
    error: false
  }

  render() {
    return (
      <React.Fragment>
        <Form.Group inline>
          <Form.Checkbox readOnly />
          <Form.Input
            disabled={this.props.isModifying?this.props.disabled:false}
            value={this.state.choix}
            error={this.state.error}
            placeholder='Saisissez un choix de réponse'
            width='12'
            onChange={(e, data) => {
              if (data.value === '') {
                this.setState({
                  choix: '',
                  error: true
                });

                return;
              }

              this.setState({
                choix: data.value,
                error: false
              });

              let params = {
                intituleChoix: data.value,
                question_id: this.props.idQuestion
              }

              this.props.client.Choix.update(
                this.props.id_choix,
                params,
                result => {},
                error => {}
              )
            }}
          />
          <Form.Button
            disabled={this.props.isModifying?this.props.disabled:false}
            circular 
            icon='trash'
            onClick={() => {
              this.props.client.Choix.delete(
                this.props.id_choix,
                result => {
                  if (this.props.update) {
                    this.props.update();
                  }
                },
                error => {
                  console.log(error);
                }
              )
            }}
          />
        </Form.Group>
      </React.Fragment>
    );
  }
}