import React from 'react'

import { 
  Dropdown,
  Flag,
  Icon, 
  Menu
} from 'semantic-ui-react'

export default class SettingsView extends React.Component {
  handleItemClick = () => {
    console.log('home');
  }

  render() {
    return (
      <React.Fragment>
        <Menu size='large'>
          <Menu.Item
            name='home'
            // active={activeItem === 'home'}
            onClick={this.handleItemClick}
          />

          <Menu.Menu position='right'>
            <Dropdown item text='Language'>
              <Dropdown.Menu>
                <Dropdown.Item>
                  <Flag name='fr' />
                </Dropdown.Item>
                <Dropdown.Item>
                  <Flag name='uk' />
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>

            <Menu.Item>
              <Icon name='user circle' />
            </Menu.Item>
          </Menu.Menu>
        </Menu>
      </React.Fragment>
    );
  }
}