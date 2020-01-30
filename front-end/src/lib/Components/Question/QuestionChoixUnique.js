import React from 'react'
import PropTypes from 'prop-types'

import { 
  Form 
} from 'semantic-ui-react';

export default class QuestionChoixUnique extends React.Component {
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
    choix: this.props.isModifying?this.props.choix:''
  }

  render() {
    return (
      <React.Fragment>
        <Form.Group inline>
          <Form.Radio readOnly />
          <Form.Input
            disabled={this.props.disabled}
            value={this.state.choix}
            placeholder='Saisissez un choix de réponse'
            width='12'
            onChange={(e, data) => {
              this.setState({
                choix: data.value
              });

              // let params = {
              //   intituleChoix: data.value,
              //   question_id: this.props.idQuestion
              // }

              // this.props.client.Choix.update(
              //   this.props.id_choix,
              //   params,
              //   result => {
              //     console.log(result);
              //   },
              //   error => {
              //     console.log(error);
              //   }
              // )
            }}
          />
          <Form.Button
            disabled={this.props.disabled}
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