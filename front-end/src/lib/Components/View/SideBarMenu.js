import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import { 
  Icon,
  Menu, 
  Sidebar 
} from 'semantic-ui-react'

import Title from './Title'

import { dictionnary } from '../../Langs/langs'

export default class SideBarMenu extends React.Component {
  static propTypes = {
    lang: PropTypes.string,
    open: PropTypes.bool,
    onHide: PropTypes.func,
    onItemClick: PropTypes.func
  }

  static defaultProps = {
    lang: 'fr'
  }

  onHide = () => {
    if (this.props.onHide) {
      this.props.onHide();
    }
  }

  handleItemClick = (e, {name}) => {
    if (this.props.onItemClick) {
      this.props.onItemClick(name);
    }

    if (this.props.onHide) {
      this.props.onHide();
    }
  }

  render() {
    let lang = _.toUpper(this.props.lang);
    let dashboard = _.get(dictionnary, lang + '.dashboard');
    let settings = _.get(dictionnary, lang + '.settings');

    return (
      <React.Fragment>
        <Sidebar
          as={Menu}
          animation='overlay'
          vertical
          visible={this.props.open}
          icon='labeled'
          style={{ width:'250px' }}
          onHide={this.onHide}
          size='massive'
        >
          <Menu.Item 
            header 
            as='h1' 
          />
          <Menu.Item
            name='dashboard'
            onClick={this.handleItemClick}
          >
            <Icon name='dashboard' />
            <Title
              as='h3'
              content={_.upperFirst(dashboard)} />
          </Menu.Item>
          <Menu.Item
            name='settings'
            onClick={this.handleItemClick}
          >
            <Icon name='setting' />
            <Title
              as='h3'
              content={_.upperFirst(settings)} />
          </Menu.Item>
        </Sidebar>
      </React.Fragment>
    );
  }
}