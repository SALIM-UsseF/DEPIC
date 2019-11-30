import React from 'react'
import PropTypes from 'prop-types'

import Title from './Title'
import Dashboard from './Dashboard'

export default class Frame extends React.Component {
  static propTypes = {
    lang: PropTypes.string,
    title: PropTypes.string
  }

  static defaultProps = {
    lang: 'fr'
  }

  render() {
    return (
      <React.Fragment>
        <Title
          as='h1'
          content={this.props.title}
          color='teal' />
        
        <Dashboard
          lang={this.props.lang} />  
      </React.Fragment>
    );
  }
}