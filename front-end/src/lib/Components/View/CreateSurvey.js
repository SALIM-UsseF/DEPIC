import React from 'react'
import PropTypes from 'prop-types'
// import _ from 'lodash'

import { MainQuestion } from './../Question'

// import { dictionnary } from '../../Langs/langs'

export default class CreateSurvey extends React.Component {
  static propTypes = {
    lang: PropTypes.string
  }

  static defaultProps = {
    lang: 'fr'
  }

  render() {
    return (
      <React.Fragment>
        <MainQuestion />
      </React.Fragment>
    );
  }
}