import React from 'react'
import PropTypes from 'prop-types'

import { 
  Menu, 
  Sidebar 
} from 'semantic-ui-react'

export default class SideBarHome extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    onItemClick: PropTypes.func
  }

  handleItemClick = name => {
    if (this.props.onItemClick) {
      this.props.onItemClick(name);
    }
  }

  render() {
    return (
      <React.Fragment>
        <Sidebar
          as={Menu}
          secondary
          icon='labeled'
          visible
          direction='top'
          inverted
          color='teal'
        >
          <Menu.Item
            icon='bars'
            onClick={() => {
              this.handleItemClick('home')
            }} 
          />
          <Menu.Menu position='right'>
            <Menu.Item
              icon='user circle'
              onClick={() => {
                this.handleItemClick('user')
              }} 
            />
          </Menu.Menu>
        </Sidebar>
      </React.Fragment>
    );
  }
}