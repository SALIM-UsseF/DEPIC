import React from 'react'
import PropTypes from 'prop-types'

import { Form } from 'semantic-ui-react'

//import { dictionnary } from '../../Langs/langs'

export default class ReponseOuverte extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    obligatoire: PropTypes.bool,
    idQuestion: PropTypes.number,
    intitule: PropTypes.string,
    nombreDeCaractere: PropTypes.number,
    ordre: PropTypes.number,
    styles: PropTypes.object
  }

  static defaultProps = {
    lang: 'fr'
  }

  render() {
    return (
      <React.Fragment>
        <p 
          style={this.props.styles.titleQuestion}
        >{this.props.ordre}. {this.props.intitule}{this.props.obligatoire?' * ':''}</p>
        
        <Form>
          <Form.TextArea
            onChange={(e, result) => {
              if (result.value.length > this.props.nombreDeCaractere) {
              }
            }}
          />
        </Form>
      </React.Fragment>
    );
  }
}