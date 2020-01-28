import React from 'react'
import PropTypes from 'prop-types'
// import _ from 'lodash'

// import { dictionnary } from '../../Langs/langs'

export default class Settings extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string
  }

  static defaultProps = {
    lang: 'fr'
  }

  render() {
    return (
      <React.Fragment>

      </React.Fragment>
    );
  }
}