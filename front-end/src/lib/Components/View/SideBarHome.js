import React from 'react'
import PropTypes from 'prop-types'

import { 
  Menu, 
  Dropdown
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
        <Menu
          secondary
          icon='labeled'
          visible="true"
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
            <Dropdown
              icon='user circle'
              item
            >
              <Dropdown.Menu>
                <Dropdown.Item
                  icon='user outline'
                  content='Mon compte'
                  onClick={() => {
                    this.handleItemClick('compte')
                  }}
                />
                <Dropdown.Item
                  icon='sign-out alternate'
                  content='Se déconnecter'
                  onClick={() => {
                    this.handleItemClick('déconnecter')
                  }}
                />
              </Dropdown.Menu>
            </Dropdown>
          </Menu.Menu>
        </Menu>
      </React.Fragment>
    );
  }
}