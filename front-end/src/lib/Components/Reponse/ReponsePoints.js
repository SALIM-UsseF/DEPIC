import React from 'react'
import PropTypes from 'prop-types'

import { 
  Rating
} from 'semantic-ui-react'

//import { dictionnary } from '../../Langs/langs'

export default class ReponsePoints extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    obligatoire: PropTypes.bool,
    idQuestion: PropTypes.number,
    intitule: PropTypes.string,
    maxPoints: PropTypes.number,
    minPoints: PropTypes.number,
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
      
        <Rating
          maxRating={this.props.maxPoints}
          defaultRating={0}
          icon='star' 
          size='massive'
          onRate={(e, result) => {
            console.log(result.rating)
          }} />
      </React.Fragment>
    );
  }
}