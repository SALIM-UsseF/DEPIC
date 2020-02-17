import React from 'react'
import PropTypes from 'prop-types'
// import _ from 'lodash'

// import { dictionnary } from '../../Langs/langs'

export default class Resultats extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    idSondage: PropTypes.number
  }

  static defaultProps = {
    lang: 'fr'
  }

  componentDidMount() {
    this.props.client.Resultat.read(
      this.props.idSondage,
      result => {
        console.log(result);
      },
      error => {
        console.log(error);
      }
    )
  }

  render() {
    return (
      <React.Fragment>

      </React.Fragment>
    );
  }
}