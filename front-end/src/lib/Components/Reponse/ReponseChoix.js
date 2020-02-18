import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import { 
  Checkbox,
  Form, 
  Radio
} from 'semantic-ui-react'

//import { dictionnary } from '../../Langs/langs'

export default class ReponseChoixMultiple extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    obligatoire: PropTypes.bool,
    unique: PropTypes.bool,
    idQuestion: PropTypes.number,
    intitule: PropTypes.string,
    nbChoix: PropTypes.number,
    ordre: PropTypes.number,
    styles: PropTypes.object
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    choixPossible: []
  }

  componentDidMount() {
    this.props.client.Choix.readByQuestion(
      this.props.idQuestion,
      result => {
        this.setState({
          choixPossible: result.data
        });
      },
      error => {
        console.log(error)
      }
    )
  }

  handleChangeUnique = (e, {value}) => {
    this.setState({ value })
  }

  handleChangeMultiple = (e, value) => {
  }

  render() {
    const { value } = this.state;

    return (
      <React.Fragment>
        <p 
          style={this.props.styles.titleQuestion}
        >{this.props.ordre}. {this.props.intitule}{this.props.obligatoire?' * ':''}</p>
        
        {_.map(this.state.choixPossible, choix => {
          if (this.props.unique) {
            return (
              <Form key={choix.id_choix} size="massive">
                <Form.Group unstackable>
                  <Form.Field
                    control={Radio}
                    label={choix.intituleChoix}
                    value={choix.id_choix}
                    checked={value === choix.id_choix}
                    onChange={this.handleChangeUnique}
                  />
                </Form.Group>
              </Form>
            );
          } else {
            return (
              <Form key={choix.id_choix} size="massive">
                <Form.Group unstackable>
                  <Form.Field
                    control={Checkbox}
                    label={choix.intituleChoix}
                    value={choix.id_choix}
                    onChange={this.handleChangeMultiple}
                  />
                </Form.Group>
              </Form>
            );
          }
        })}
      </React.Fragment>
    );
  }
}