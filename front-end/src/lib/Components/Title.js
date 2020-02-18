import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import {
  Header
} from 'semantic-ui-react'

export default class Title extends React.Component {
  static propTypes = {
    as: PropTypes.string,
    content: PropTypes.string,
    color: PropTypes.string,
    icon: PropTypes.bool
  }

  render() {
    return (
      <React.Fragment>
        <Header 
          as={this.props.as}
          color={_.isNull(this.props.color)?'black':this.props.color}
          icon={_.isNull(this.props.icon)?false:this.props.icon}
        >
          {this.props.content}
        </Header>
      </React.Fragment>
    );
  }
}